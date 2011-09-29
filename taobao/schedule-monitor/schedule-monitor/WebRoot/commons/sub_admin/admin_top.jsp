<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <script type="text/javascript" src="${ctx}/js/jquery-1.2.6.js"></script>
   <script type="text/javascript" src="${ctx}/js/jMessager.js" ></script>
   <link href="${ctx}/css/adminTop.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function ms(s){
	jQuery.messager.lays(200,45);
	jQuery.messager.show('&nbsp;',s,2000);
}
function cc(n)
{
	jQuery("#TopMain_NavCenter a").removeClass('on');
	jQuery("#TopMain_NavCenter a:eq("+n+")").addClass('on');
	jQuery("#TopMain_NavCenter a:eq("+n+")").blur();
}
</script>
</head>
<body>
	<div id="TopIndex">
		<div id="TopIndex_Index">
			<ul>
				<li><a href="#">游览店面</a></li>
				<li class="TopIndex_IndexDiv"></li>
				<li><a href="#">账号设置</a></li>
				<li class="TopIndex_IndexDiv"></li>
				<li><a href="#">桌面</a></li>
				<li class="TopIndex_IndexDiv"></li>
				<li><a href="#">关于</a></li>
				<li class="TopIndex_IndexDiv"></li>
				<li><a href="#">退出</a></li>
			</ul>
		</div>
		<div id="TopIndex_WelcomeCurrentUser">
			admin 欢迎登陆
		</div>
	</div>
	<div id="TopMain">
		<div id="TopMain_Logo">
			<img src="${ctx}/images/adminTop_Logo.gif" width="192" height="62"/>				<!--LOGO-->
		</div>
		<div id="TopMain_Body">
			<div id="TopMain_BodyLeft">
				<div id="TopMain_NavLeft">
				</div>
				<div id="TopMain_NavRight">
				</div>
				<div id="TopMain_NavCenter">
					<ul>
						<li id="TopMain_NavCenter_One"><a class="on" href="javascript:window.parent.got('goods')">商品</a></li>
						<li class="TopMain_NavCenterDiv"></li>
						<li id="TopMain_NavCenter_Two"><a href="javascript:window.parent.got('orders')">订单</a></li>
						<li class="TopMain_NavCenterDiv"></li>
						<li id="TopMain_NavCenter_Three"><a href="javascript:window.parent.got('members')">会员</a></li>
						<li class="TopMain_NavCenterDiv"></li>
						<li id="TopMain_NavCenter_Four"><a href="#">营销推广</a></li>
						<!--  <li class="TopMain_NavCenterDiv"></li>
						<li id="TopMain_NavCenter_Five"><a href="#">统计报表</a></li> -->
						<li class="TopMain_NavCenterDiv"></li>
						<li id="TopMain_NavCenter_Five"><a href="javascript:window.parent.got('config')">商店配置</a></li>
					</ul>
				</div>
			</div>
			<div id="TopMain_BodyRight">
				<img src="${ctx}/images/adminTop_BodyRightBackGroundLeft.gif" width="33" height="62"/>
				<!--导航栏右侧自适应圆角头-->
				<div id="m"></div>
			</div>
		</div>
	</div>
</body>
</html>

