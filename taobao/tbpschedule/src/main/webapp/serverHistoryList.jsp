
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
调动服务器历史信息
</title>
</head>
<body bgcolor="#ffffff">

<%
String baseTaskType =  request.getParameter("baseTaskType");
String ownSign =  request.getParameter("ownSign");
String ip =  request.getParameter("ip");
String orderStr =  request.getParameter("orderStr");
%>

   <table border="1" style=";border-COLLAPSE: collapse;display:block;">
   <tr>
   <th nowrap>序号</th>
   <th>任务类型</th>
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
   List<ScheduleServer> serverList = MonitorBean.getScheduleConfigCenterClient()
        .selectHistoryScheduleServer(baseTaskType,ownSign,ip,orderStr);
   for(int j =0;j<serverList.size();j++){
   %>
	   <tr>
	   <td><%=(j+1) %></td>
	   <td><%=serverList.get(j).getBaseTaskType()%></td>	  
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
</body>
</html>
