package com.guiwuu.workshop.tbpschedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.taobao.pamirs.schedule.IScheduleTaskDealSingle;
import com.taobao.pamirs.schedule.TBScheduleManagerFactory;

public class SimpleTask implements IScheduleTaskDealSingle<Long> {

	private final static transient Log log = LogFactory
			.getLog(SimpleTask.class);

	@Autowired
	private DataSource dataSource;

	@Autowired
	private TBScheduleManagerFactory tbScheduleManagerFactory;

	private String taskType;

	private String ownSign;

	public boolean execute(Long task, String ownSign) throws Exception {
		Connection conn = null;
		Long id = (Long) task;
		try {
			conn = dataSource.getConnection();
			String sql = "update LOTTERY_SCHEDULE_TASK SET PARAMETER ='Y' ,DEAL_COUNT = DEAL_COUNT + 1, GMT_MODIFIED=now() WHERE ID = ? and PARAMETER ='N' ";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1, id);
			statement.executeUpdate();
			statement.close();
			conn.commit();
			log.info("处理任务：" + id + " 成功！");
			return true;
		} catch (Exception e) {
			log.error("执行任务：" + task + "失败：" + e.getMessage(), e);
			if (conn != null) {
				conn.rollback();
			}
			return false;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	public Comparator<Long> getComparator() {
		return new Comparator<Long>() {
			public int compare(Long o1, Long o2) {
				return o1.compareTo(o2);
			}
		};
	}

	public List<Long> selectTasks(String ownSign, int queueNum,
			List<String> queryCondition, int fetchNum) throws Exception {
		List<Long> result = new ArrayList<Long>();
		if (queryCondition.size() == 0) {
			return result;
		}
		StringBuffer condition = new StringBuffer();
		for (int i = 0; i < queryCondition.size(); i++) {
			if (i > 0) {
				condition.append(",");
			}
			condition.append(queryCondition.get(i));
		}
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			String sql = null;
			sql = "select ID from LOTTERY_SCHEDULE_TEST where OWN_SIGN = '" + ownSign
					+ "'  and mod(id," + queueNum + ") in ("
					+ condition.toString() + ") and PARAMETER ='N' LIMIT " + fetchNum;
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			while (set.next()) {
				result.add(set.getLong("ID"));
			}
			set.close();
			statement.close();
			return result;
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public void init() throws Exception {
		tbScheduleManagerFactory.createTBScheduleManager(taskType, ownSign);
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public void setOwnSign(String ownSign) {
		this.ownSign = ownSign;
	}

}
