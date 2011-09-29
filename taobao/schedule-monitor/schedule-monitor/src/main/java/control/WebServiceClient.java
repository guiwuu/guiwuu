/**
 * @(#) WebServiceClient.java Created on 2010-12-27 下午06:28:23
 * Copyright (c) 2010 by Taobao.com.
 */
package control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.ScheduleClient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.taobao.pamirs.schedule.IScheduleClient;
import com.taobao.pamirs.schedule.ScheduleServer;

import control.dao.IScheduleMonitorDAO;

/**
 * 
 * 类名称：WebServiceClient
 * 类描述：采集schedule web服务接口
 * 创建人：jishao
 * 创建时间：2010-12-27 下午06:28:23
 * 
 */

public class WebServiceClient {

	private static transient Log logger = LogFactory
			.getLog(WebServiceClient.class);
	private static IScheduleMonitorDAO scheduleMonitorDAO;

	private static List<ScheduleClient> clientList;

	public static List<ScheduleClient> getClientsInstance() {
		if (clientList == null) {
			clientList = Collections.synchronizedList(new ArrayList<ScheduleClient>());
		}
		return clientList;
	}

	public static Map servTasks = new HashMap<String, List<String>>();
	
	public static List<ScheduleClient> getScheduleClientsByWebService() {
		getClientsInstance().clear();
		List<ScheduleServer> ssList = null;
		try {
			ssList = scheduleMonitorDAO.getScheduleServer();
		} catch (Exception e) {
			logger.error("查询schedule service信息出错!", e);
		}
		if (CollectionUtils.isEmpty(ssList) || ssList.isEmpty()) {
			logger.warn("任务调度数据中未获得有效server数据!");
		} else {
			ssList = filterServerList(ssList);
			for (ScheduleServer ss : ssList) {
				ScheduleClient client = new ScheduleClient();
				IScheduleClient webClient = instanceClient(ss.getIp());
				client.setIp(ss.getIp());
				client.setClient(webClient);
				client
						.setTaskTypeList((List<String>) servTasks.get(ss
								.getIp()));
				getClientsInstance().add(client);
			}
		}
		return clientList;
	}

	/**
	 * filterServerList(过滤服务器信息，去除重复的机器)
	 * @param ssList
	 * @return List
	 * @author jishao
	 * @since 2010-12-30 上午11:13:37
	 */
	public static List<ScheduleServer> filterServerList(
			List<ScheduleServer> ssList) {
		List<ScheduleServer> fserverList = new ArrayList<ScheduleServer>();
		for (ScheduleServer ss : ssList) {
			boolean isMuti = false;
			List<String> taskTypes = new ArrayList<String>();
			for (ScheduleServer fs : fserverList) {
				if (fs.getIp().equals(ss.getIp())) {
					isMuti = true;
					taskTypes.add(ss.getTaskType());
					continue;
				}
			}
			if (!isMuti || fserverList.isEmpty()) {
				fserverList.add(ss);
				taskTypes.add(ss.getTaskType());
				servTasks.put(ss.getIp(), taskTypes);
			}
			if (isMuti
					&& !((List<String>) servTasks.get(ss.getIp())).contains(ss
							.getTaskType())) {
				taskTypes.add(ss.getTaskType());
				servTasks.put(ss.getIp(), taskTypes);
			}
		}
		return fserverList;
	}

	/**
	 * instanceClient(实例化有效机器的WEB服务客户端)
	 * @param ip
	 * @return IScheduleClient
	 * @author jishao
	 * @since 2010-12-30 上午11:15:16
	 */
	public static IScheduleClient instanceClient(String ip) {
		JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
		factoryBean
				.setServiceClass(com.taobao.pamirs.schedule.IScheduleClient.class);
		factoryBean.setAddress("http://" + ip + "/ScheduleWebService");
		IScheduleClient client = (IScheduleClient) factoryBean.create();
		return client;
	}

	public void setScheduleMonitorDAO(IScheduleMonitorDAO scheduleMonitorDAO) {
		WebServiceClient.scheduleMonitorDAO = scheduleMonitorDAO;
	}

}
