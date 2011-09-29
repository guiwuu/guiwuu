/**
 * @(#) ScheduleMonitorDAO.java Created on 2010-12-29 下午02:14:10
 * Copyright (c) 2010 by Taobao.com.
 */
package control.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.taobao.pamirs.schedule.ScheduleServer;
import com.taobao.pamirs.transaction.TBConnection;
import com.taobao.pamirs.transaction.TBTransactionHint;

/**
 * 
 * 类名称：ScheduleMonitorDAO
 * 类描述：DAO实现类
 * 创建人：jishao
 * 创建时间：2010-12-29 下午02:14:10
 * 
 */
public class ScheduleMonitorDAO implements IScheduleMonitorDAO, TBTransactionHint {

	Map<String, String> tableMap;
	/**
	 * 配置中心数据库的数据源
	 */
	DataSource dataSource;

	/**
	 * 获取所有服务器信息
	 * @param taskInfo
	 * @throws Exception
	 */
	public List<ScheduleServer> getScheduleServer()
			throws Exception {
		List<ScheduleServer> result = new ArrayList<ScheduleServer>();
		Connection conn = null;
		try {
			conn = getConnection();
			String sql = " SELECT UUID,TASK_TYPE,BASE_TASK_TYPE,OWN_SIGN,IP,HOST_NAME,MANAGER_PORT,THREAD_NUM,REGISTER_TIME,HEARTBEAT_TIME,VERSION,DEALINFO_DESC,JMX_URL, "
					+ this.getDataBaseSysdateString(conn)
					+ " as CenterServerTime   FROM  "
					+ transferTableName(conn, "PAMIRS_SCHEDULE_SERVER")
					+ " ORDER BY REGISTER_TIME";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet resultSet = stmt.executeQuery();
			while (resultSet.next()) {
				ScheduleServer server = new ScheduleServer();
				server.setUuid(resultSet.getString("UUID"));
				server.setTaskType(resultSet.getString("TASK_TYPE"));
				server.setBaseTaskType(resultSet.getString("BASE_TASK_TYPE"));
				server.setOwnSign(resultSet.getString("OWN_SIGN"));
				server.setIp(resultSet.getString("IP"));
				server.setHostName(resultSet.getString("HOST_NAME"));
				server.setManagerPort(resultSet.getInt("MANAGER_PORT"));
				server.setThreadNum(resultSet.getInt("THREAD_NUM"));
				server.setRegisterTime(resultSet.getTimestamp("REGISTER_TIME"));
				server.setHeartBeatTime(resultSet
						.getTimestamp("HEARTBEAT_TIME"));
				server.setVersion(resultSet.getInt("VERSION"));
				server.setCenterServerTime(resultSet
						.getTimestamp("CenterServerTime"));
				server.setDealInfoDesc(resultSet.getString("DEALINFO_DESC"));
				server.setJmxUrl(resultSet.getString("JMX_URL"));
				result.add(server);
			}
			resultSet.close();
			stmt.close();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return result;
	}

	String dataBaseType = null;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Connection getConnection() throws Exception {
		return dataSource.getConnection();
	}

	/**
	 * 配置中心的数据库类型
	 */
	public String getDataBaseType(Connection conn) throws SQLException {
		if (dataBaseType == null) {
			if (conn instanceof TBConnection) {
				dataBaseType = ((TBConnection) conn).getDBType();
			} else {
				dataBaseType = conn.getMetaData().getDatabaseProductName();
				if ("oracle".equalsIgnoreCase(dataBaseType) == false
						&& "mysql".equalsIgnoreCase(dataBaseType) == false) {
					throw new SQLException("不支持的数据库类型：" + dataBaseType);
				}
			}
		}
		return dataBaseType;
	}

	/**
	 * 获取不同数据库获取系统时间的方法字符串
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public String getDataBaseSysdateString(Connection conn) throws Exception {
		String type = this.getDataBaseType(conn);
		if ("oracle".equalsIgnoreCase(type)) {
			return "sysdate";
		} else if ("mysql".equalsIgnoreCase(type)) {
			return "now()";
		} else {
			throw new Exception("不支持的数据库类型：" + type);
		}
	}

	public void setTableMap(Map<String, String> aTableMap) {
		this.tableMap = new HashMap<String, String>();
		for (Object e : aTableMap.keySet()) {
			String key = ((String) e).toUpperCase();
			if (aTableMap.get(e) != null
					&& aTableMap.get(e).toString().trim().length() > 0) {
				this.tableMap.put(key.trim(), aTableMap.get(e).toString()
						.trim());
			}
		}
	}

	public String transferTableName(Connection conn, String name) {
		if (this.tableMap == null) {
			return name;
		} else if (this.tableMap.containsKey(name)) {
			return tableMap.get(name);
		} else {
			return name;
		}
	}
}
