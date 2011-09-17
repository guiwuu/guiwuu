/**
 * @(#) ScheduleMonitorDAO.java Created on 2010-12-29 下午02:14:10
 * Copyright (c) 2010 by Taobao.com.
 */
package control.dao;

import java.util.List;

import com.taobao.pamirs.schedule.ScheduleServer;

/**
 * 
 * 类名称：ScheduleMonitorDAO
 * 类描述：数据库DAO接口
 * 创建人：jishao
 * 创建时间：2010-12-29 下午02:14:10
 * 
 */
public interface IScheduleMonitorDAO {

	/**
	 * 获取所有服务器信息
	 * @param taskInfo
	 * @throws Exception
	 */
	public List<ScheduleServer> getScheduleServer() throws Exception;

}
