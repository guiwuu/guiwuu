
<%@page import="com.taobao.pamirs.schedule.MonitorBean"%>
<%@page import="com.taobao.pamirs.schedule.TaskQueueInfo"%>
<%@page import="com.taobao.pamirs.schedule.ScheduleServer"%>
<%@page import="com.taobao.pamirs.schedule.ScheduleTaskTypeRunningInfo"%>
<%@page import="com.taobao.pamirs.schedule.ScheduleTaskType"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=GB2312" %>
<html>
<head>
<title>
调度任务详细信息
</title>
</head>
<body bgcolor="#ffffff">

<%
String baseTaskType =  request.getParameter("baseTaskType");
List<ScheduleTaskTypeRunningInfo> taskTypeRunningInfoList = MonitorBean.getScheduleConfigCenterClient().getAllTaskTypeRunningInfo(baseTaskType);
if(taskTypeRunningInfoList.size() ==0){
%>
任务 <%=baseTaskType%>：还没有运行期信息
<%	
}else{
%>
<table border="1" style="border-COLLAPSE: collapse;display:block;">
<%
for(int i=0;i<taskTypeRunningInfoList.size();i++){
%>
<tr style="background-color:#F3F5F8;color:#013299;">
<td>
	<%=taskTypeRunningInfoList.get(i).getTaskType()%> -- <%=taskTypeRunningInfoList.get(i).getOwnSign()%>   
</td>
</tr>
<tr>
<td>
   <table border="1" style="background-color:#FFF475;border-COLLAPSE: collapse;display:block;">
   <tr>
   <th nowrap>序号</th>
   <th>服务编号</th>
   <th>域</th>
   <th>IP地址</th>
   <th>主机名称</th>
   <th nowrap>线程</th>
   <th>注册时间</th>
   <th>心跳时间</th>
   <th nowrap>版本</th>
   <th nowrap>下次开始</th>
   <th nowrap>下次结束</th>
   <th>JMX</th>
   <th>处理详情</th>
   
   </tr>
   <%
   List<ScheduleServer> serverList = MonitorBean.getScheduleConfigCenterClient().selectAllValidScheduleServer(taskTypeRunningInfoList.get(i).getTaskType());
   for(int j =0;j<serverList.size();j++){
   %>
	   <tr>
	   <td><%=(j+1) %></td>
	   <td nowrap><%=serverList.get(j).getUuid()%></td>
	   <td><%=serverList.get(j).getOwnSign()%></td>	  
	   <td nowrap><%=serverList.get(j).getIp()%></td>	  
	   <td nowrap><%=serverList.get(j).getHostName()%></td>	
	   <td><%=serverList.get(j).getThreadNum()%></td>	
	   <td nowrap><%=serverList.get(j).getRegisterTime()%></td>	
	   <td nowrap><%=serverList.get(j).getHeartBeatTime()%></td>	
	   <td><%=serverList.get(j).getVersion()%></td>	
	   <td nowrap><%=serverList.get(j).getNextRunStartTime() == null?"--":serverList.get(j).getNextRunStartTime()%></td>	
	   <td nowrap><%=serverList.get(j).getNextRunEndTime()==null?"--":serverList.get(j).getNextRunEndTime()%></td>
	   <td nowrap><%=serverList.get(j).getJmxUrl()%></td>	
	   <td nowrap><%=serverList.get(j).getDealInfoDesc()%></td>	
	   </tr>      
   <%
   }
   %>
   </table> 
</td>
</tr>
<!-- 队列信息 -->
<tr>
<td>
   <table border="1" style="border-COLLAPSE: collapse;display:block;">
   <tr>
   <th>队列</th>
   <th>当前服务器</th>
   <th>请求服务器</th>
   
   </tr>
   <%
	List<TaskQueueInfo> queueList =MonitorBean.getScheduleConfigCenterClient().loadAllQueue(taskTypeRunningInfoList.get(i).getTaskType());
   for(int j =0;j<queueList.size();j++){
   %>
	   <tr>
	   <td><%=queueList.get(j).getTaskQueueId()%></td>
	   <td><%=queueList.get(j).getCurrentScheduleServer()%></td>
	   <td><%=queueList.get(j).getRequestScheduleServer()==null?"--":queueList.get(j).getRequestScheduleServer()%></td>	   
	   </tr>      
   <%
   }
   %>
   </table> 
</td>
</tr>
<%
}
%>
</table>
<%
}
%>
</body>
</html>
