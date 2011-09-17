<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/commons/sub_admin/_head.jsp" %>
<link href="${ctx}/css/AdminGrid.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/ajaxutils.js"></script>
<script type="text/javascript" src="${ctx}/js/stat.js"></script>
</head>
<body>

<%@ include file="/commons/sub_admin/_topShadow.jsp" %>

<pin:afterScript>
	var isStop = false;
	jQuery('#schedule').gridAddCtrlTab(0);
	var refresh = {
		   tIndex: 0,
		   bIndex: 0,
			title: '刷新缓存信息',
		className: 'edit_edit_tag',
	    fn: function ()
		{
			isStop = true;
			var url = "ScheduleInfoRefresh"; 
			jQuery.ajax({
				type:"POST",
				url: url,
				async:false,
			    success:function(){jQuery().gridRefresh();isStop=false;},
				error:function(){alert('请求失败了');return false;}
			});
		},
			event: 'onclick'
	};
	jQuery('#schedule').gridAddCtrlButton(refresh);
	setInterval(getInfo,2000);
	function getInfo() {
		if(isStop==false) {
			ajaxUtil("POST","ScheduleMonitor","json",
			function()
			{
				jQuery().gridRefresh();
			},
			function(){
				alert('请求失败了');
				return false;
			});
		}
	}
</pin:afterScript>

<pin:newCellScript param="info">
var j = jQuery().getJsonData();
if(info.colIndex==7)
{
	return MillisecondToDate(j.data[info.rowIndex].dealSpendTime);
}
if(info.colIndex==9)
{
	var s = '';
	if(j.data[info.rowIndex].nextRunStartTime == "")
		s = '执行中';
	else
		s = j.data[info.rowIndex].nextRunStartTime;
	return s;
}
if(info.colIndex==10)
{
	var s = '';
	if(j.data[info.rowIndex].nextRunEndTime == "")
		s = '执行中';
	else
		s = j.data[info.rowIndex].nextRunEndTime;
	return s;
}
</pin:newCellScript>


<pin:pageScript 
    column="[
	{title:'服务器',name:'ip',width:'100px',sortable:true,field:'ip'},
	{title:'任务类型',name:'taskType',width:'150px',sortable:true,field:'taskType'},
	{title:'所属域',name:'ownSign',width:'100px',sortable:true,field:'ownSign'},
	{title:'读取的数据量',name:'fetchDataNum',width:'100px',sortable:true,field:'fetchDataNum'},
	{title:'读取次数',name:'fetchDataCount',width:'100px',sortable:true,field:'fetchDataCount'},
	{title:'处理成功的数据量',name:'dealDataSucess',width:'150px',sortable:true,field:'dealDataSucess'},
	{title:'处理失败的数据量',name:'dealDataFail',width:'150px',sortable:true,field:'dealDataFail'},
	{type:'other',title:'处理总耗时',name:'dealSpendTime',width:'100px',sortable:true,field:'dealSpendTime'},
	{title:'比较次数',name:'otherCompareCount',width:'100px',sortable:true,field:'otherCompareCount'},
	{type:'other',title:'下次执行开始时间',name:'nextRunStartTime',width:'150px',sortable:true,field:'nextRunStartTime'},
	{type:'other',title:'下次执行结束时间',name:'nextRunEndTime',width:'150px',sortable:true,field:'nextRunEndTime'}]" 
	id="schedule" pageUrl="${ctx}/ScheduleMonitor" 
	title="schedule动态监控列表" pkey="ip"
	previewStyle="advance">
</pin:pageScript>

<%@ include file="/commons/sub_admin/_bottomShadow.jsp" %>
</body>
</html>











