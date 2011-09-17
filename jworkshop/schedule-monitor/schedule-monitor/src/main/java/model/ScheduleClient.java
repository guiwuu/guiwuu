/**
 * @(#) ScheduleClient.java Created on 2010-12-30 上午10:59:14
 * Copyright (c) 2010 by Taobao.com.
 */
package model;

import java.util.List;

import com.taobao.pamirs.schedule.IScheduleClient;

/**
 * 
 * 类名称：ScheduleClient
 * 类描述：schedule web服务client组装类
 * 创建人：jishao
 * 创建时间：2010-12-30 上午10:59:14
 * 
 */
public class ScheduleClient {
	
	private String ip;
	private List<String> taskTypeList;
	private IScheduleClient client;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public List<String> getTaskTypeList() {
		return taskTypeList;
	}

	public void setTaskTypeList(List<String> taskTypeList) {
		this.taskTypeList = taskTypeList;
	}

	public IScheduleClient getClient() {
		return client;
	}

	public void setClient(IScheduleClient client) {
		this.client = client;
	}
}
