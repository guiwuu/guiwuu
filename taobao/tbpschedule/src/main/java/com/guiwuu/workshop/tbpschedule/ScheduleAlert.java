package com.guiwuu.workshop.tbpschedule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.pamirs.schedule.IScheduleAlert;

public class ScheduleAlert implements IScheduleAlert {

	private Log log = LogFactory.getLog(ScheduleAlert.class);

	public void noReloadTaskQueue(String arg0, String arg1, String arg2) {
		log.warn("noTaskQueue.............");
	}

	public void noTaskQueue(String arg0, String arg1, String arg2) {
		log.warn("noReloadTaskQueue.............");
	}

}
