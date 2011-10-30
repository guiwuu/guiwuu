/**
 * @(#) WebServiceClient.java Created on 2010-12-27 下午06:28:23
 * Copyright (c) 2010 by Taobao.com.
 */

import java.lang.reflect.Field;
import java.util.List;

import model.ScheduleClient;
import model.ScheduleStat;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.unitils.spring.annotation.SpringBean;
import org.unitils.spring.annotation.SpringBeanByName;

import utils.MonitorUtils;

import com.taobao.pamirs.schedule.ScheduleServer;

import control.ScheduleMonitorManager;
import control.WebServiceClient;
import control.dao.IScheduleMonitorDAO;

/**
 * 
 * 类名称：WebServiceClient
 * 类描述：测试
 * 创建人：jishao
 * 创建时间：2010-12-27 下午06:28:23
 * 
 */
@ContextConfiguration(locations = { "classpath:db.xml" })
public class WebServiceClientTest extends AbstractJUnit4SpringContextTests {

	@SpringBean(value = "scheduleMonitorDAO")
	private IScheduleMonitorDAO scheduleMonitorDAO;

	@SpringBeanByName
	public void setScheduleMonitorDAO(IScheduleMonitorDAO scheduleMonitorDAO) {
		this.scheduleMonitorDAO = scheduleMonitorDAO;
	}

	@Test
	public void test_manager() throws Exception {
		int threadNum = 3;
		for (int i = 0; i < threadNum; i++) {
			ScheduleMonitorManager manager = new ScheduleMonitorManager();
			List<ScheduleStat> ss = manager.getMonitorStats();
			if (CollectionUtils.isEmpty(ss)) {
				System.out.println("no results!");
				System.exit(0);
			}
			for (ScheduleStat s : ss) {
				System.out.println("---------------------");
				System.out.println(s.getDealDataSucess());
				System.out.println(s.getFetchDataCount());
				System.out.println("---------------------");
			}
			Thread.sleep(5000);
		}
	}

	@Test
	public void test_client() throws Exception {
		ScheduleClient a = WebServiceClient.getScheduleClientsByWebService()
				.get(0);
		System.out.println(a.getClient().queryQueueCount(
				a.getTaskTypeList().get(0)));
		System.out.println(a.getClient().loadTaskTypeBaseInfo(
				a.getTaskTypeList().get(0)));
		ScheduleServer sm = a.getClient().selectAllValidScheduleServer(
				a.getTaskTypeList().get(0)).get(0);
		String desc = sm.getDealInfoDesc();
		desc = desc.substring(desc.lastIndexOf("FetchDataCount"));
		System.out.println(desc);
		String[] statStrings = desc.split(",");
		String[] tars = new String[] { "fetchDataNum", "fetchDataCount",
				"dealDataSucess", "dealDataFail", "dealSpendTime",
				"otherCompareCount" };
		ScheduleStat stat = new ScheduleStat();

		for (String s : statStrings) {
			System.out.println(s);
			String[] ss = s.split("=");
			for (String t : tars) {
				if (ss[0].equalsIgnoreCase(t)) {
					new MonitorUtils().setComponentsValue(stat, t, ss[1]);
				}
			}
		}
		stat.setIp(sm.getIp());
		stat.setNextRunStartTime(sm.getNextRunStartTime());
		stat.setNextRunEndTime(sm.getNextRunEndTime());
		stat.setTaskType(sm.getTaskType());
		stat.setOwnSign(sm.getOwnSign());
		System.out.println(stat);
	}

	public void setComponentsValue(Object o, String n, Object v) {
		Field[] fields = o.getClass().getDeclaredFields();
		for (int i = 0, len = fields.length; i < len; i++) {
			String varName = fields[i].getName();
			try {
				// 获取原来的访问控制权限 
				boolean accessFlag = fields[i].isAccessible();
				// 获取在对象f中属性fields[i]对应的对象中的变量 
				if (n.equals(varName)) {
					// 修改访问控制权限 
					fields[i].setAccessible(true);
					fields[i].setLong(o, Long.parseLong((String) v));
					System.out
							.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);
				}
				// 恢复访问控制权限 
				fields[i].setAccessible(accessFlag);
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void test() throws Exception {
		List<ScheduleServer> ss = scheduleMonitorDAO.getScheduleServer();
		System.out.println(ss.get(0).getIp());
	}
}
