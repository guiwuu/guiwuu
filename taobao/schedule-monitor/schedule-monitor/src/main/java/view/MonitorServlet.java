/**
 * @(#) MonitorServlet.java Created on 2010-12-30 下午05:13:32
 * Copyright (c) 2010 by Taobao.com.
 */
package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Page;
import model.ScheduleStat;
import net.sf.json.JSONObject;
import control.ScheduleMonitorManager;

/**
 * 
 * 类名称：MonitorServlet
 * 类描述：监控servlet
 * 创建人：jishao
 * 创建时间：2010-12-30 下午05:13:32
 * 
 */
@SuppressWarnings("serial")
public class MonitorServlet extends HttpServlet {

	private static List<ScheduleStat> statList = new ArrayList<ScheduleStat>();
	private Page page;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		page = new Page();
		PrintWriter out = response.getWriter();
		ScheduleMonitorManager manager = new ScheduleMonitorManager();
		statList = manager.getMonitorStats();
		JSONObject jsonObject = new JSONObject();
		page.setData(statList);
		jsonObject.put("page", page);
		out.print(jsonObject.toString());
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
