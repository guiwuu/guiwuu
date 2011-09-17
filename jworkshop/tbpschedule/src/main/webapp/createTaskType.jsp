<%@page import="com.taobao.pamirs.schedule.MonitorBean"%>
<%@page import="com.taobao.pamirs.schedule.TaskQueueInfo"%>
<%@page import="com.taobao.pamirs.schedule.ScheduleTaskType"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=GB2312" %>
<html>
<head>
<title>
创建调度任务
</title>
</head>
<body bgcolor="#ffffff">
<%
	String action = request.getParameter("aciton");
	String result = "";
	boolean isRefreshParent = false;
	String baseTaskType = request.getParameter("taskType");
	try {
		if (action.equalsIgnoreCase("createTaskType")) {
			ScheduleTaskType taskType = new ScheduleTaskType();
			taskType.setBaseTaskType(baseTaskType);
			taskType.setDealBeanName(request.getParameter("dealBean"));
			MonitorBean
					.getScheduleConfigCenterClient()
					.createBaseTaskType(taskType,
							request.getParameter("queueIds").split(","));
			result = "任务" + baseTaskType + "创建成功！！！！";
			isRefreshParent = true;
		} else if (action.equalsIgnoreCase("clearTaskType")) {
			MonitorBean.getScheduleConfigCenterClient().clearTaskType(
					baseTaskType);
			result = "任务" + baseTaskType + "运行期信息清理成功！！！！";
			isRefreshParent = false;
		} else if (action.equalsIgnoreCase("deleteTaskType")) {
			MonitorBean.getScheduleConfigCenterClient().deleteTaskType(
					baseTaskType);
			result = "任务" + baseTaskType + "删除成功！！！！";
			isRefreshParent = true;
		}
	} catch (Throwable e) {
		result ="ERROR:" + e.getMessage(); 
		isRefreshParent = false;
	}
%>
<%=result%>
</body>
</html>
<% if(isRefreshParent == true){ %>
<script>
 parent.location.reload();
</script>
<%}%>