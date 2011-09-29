/**
 * @(#) ScheduleMonitorManager.java Created on 2010-12-30 下午02:58:53
 * Copyright (c) 2010 by Taobao.com.
 */
package control;

import java.util.ArrayList;
import java.util.List;

import model.ScheduleClient;
import model.ScheduleStat;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import utils.MonitorUtils;

import com.taobao.pamirs.schedule.ScheduleServer;

/**
 * 
 * 类名称：ScheduleMonitorManager
 * 类描述：监控调用处理类
 * 创建人：jishao
 * 创建时间：2010-12-30 下午02:58:53
 * 
 */
public class ScheduleMonitorManager {

	private static transient Log logger = LogFactory
			.getLog(ScheduleMonitorManager.class);

	/**
	 * selectMonitorStats(获取所有有效监控数据)
	 * @return list
	 * @author jishao
	 * @since 2010-12-30 下午04:11:25
	 */
	public List<ScheduleStat> getMonitorStats() {
		List<ScheduleClient> clientList = WebServiceClient.getClientsInstance();
		List<ScheduleStat> statList = new ArrayList<ScheduleStat>();
		try {
			synchronized (clientList) {
				if (CollectionUtils.isEmpty(clientList) && clientList.isEmpty()) {
					clientList = WebServiceClient
							.getScheduleClientsByWebService();
					if (CollectionUtils.isEmpty(clientList)
							&& clientList.isEmpty()) {
						throw new Exception("未能获取有效的web service!");
					}
					statList = selectMonitorStats(clientList, statList);
				} else {
					statList = selectMonitorStats(clientList, statList);
				}
			}
		} catch (Exception e) {
			logger.error("获取web service异常", e);
		}
		return statList;
	}

	public List<ScheduleStat> selectMonitorStats(
			List<ScheduleClient> clientList, List<ScheduleStat> statList)
			throws Exception {
		for (ScheduleClient client : clientList) {
			if ((CollectionUtils.isEmpty(client.getTaskTypeList()) && client
					.getTaskTypeList().isEmpty())) {
				throw new Exception("服务器" + client.getIp() + "上未能获取相关任务!");
			}
			for (String taskType : client.getTaskTypeList()) {
				try {
					List<ScheduleServer> sList = client.getClient()
							.selectAllValidScheduleServer(taskType);
					ScheduleStat stat = new ScheduleStat();
					for (ScheduleServer sServer : sList) {
						if (sServer.getIp().equals(client.getIp())) {
							stat = getStatByServer(sServer);
						}
					}
					if (stat != null) {
						statList.add(stat);
					}
				} catch (RuntimeException e) {
					clientList.remove(client);
					throw new RuntimeException("服务器" + client.getIp()
							+ "调用web服务失败!检查该机器是否运行schedule和提供web服务!");
				}
			}
		}
		return statList;
	}

	public ScheduleStat getStatByServer(ScheduleServer server) {
		ScheduleStat stat = new ScheduleStat();
		String desc = server.getDealInfoDesc();
		desc = desc.substring(desc.lastIndexOf("FetchDataCount"));
		String[] statStrings = desc.split(",");
		for (String ss : statStrings) {
			String[] s = ss.split("=");
			for (String t : MonitorUtils.StatProps) {
				if (s[0].equalsIgnoreCase(t)) {
					new MonitorUtils().setComponentsValue(stat, t, s[1]);
				}
			}
		}
		stat.setIp(server.getIp());
		stat.setNextRunStartTime(server.getNextRunStartTime());
		stat.setNextRunEndTime(server.getNextRunEndTime());
		stat.setTaskType(server.getTaskType());
		stat.setOwnSign(server.getOwnSign());
		return stat;
	}
}
