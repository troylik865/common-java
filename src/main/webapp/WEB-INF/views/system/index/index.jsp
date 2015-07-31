<%@ page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include/var.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>系统</title>
<link href="${contextPath}/webresources/management/themes/azure/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${contextPath}/webresources/management/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${contextPath}/webresources/management/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<link href="${contextPath}/webresources/management/themes/css/custom.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${contextPath}/webresources/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>

<!--[if IE]>
<link href="${contextPath}/webresources/management/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->
<!-- <style type="text/css">
	#header{height:85px}
	#leftside, #container, #splitBar, #splitBarProxy{top:90px}
</style> -->
<script src="${contextPath}/webresources/management/js/speedup.js" type="text/javascript"></script>
<script src="${contextPath}/webresources/management/js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${contextPath}/webresources/management/js/jquery.cookie.js" type="text/javascript"></script>
<script src="${contextPath}/webresources/management/js/jquery.validate.js" type="text/javascript"></script>
<script src="${contextPath}/webresources/management/js/jquery.bgiframe.js" type="text/javascript"></script>

<!-- svg图表  supports Firefox 3.0+, Safari 3.0+, Chrome 5.0+, Opera 9.5+ and Internet Explorer 6.0+ -->
<script type="text/javascript" src="${contextPath}/webresources/chart/raphael.js"></script>
<script type="text/javascript" src="${contextPath}/webresources/chart/g.raphael.js"></script>
<script type="text/javascript" src="${contextPath}/webresources/chart/g.bar.js"></script>
<script type="text/javascript" src="${contextPath}/webresources/chart/g.line.js"></script>
<script type="text/javascript" src="${contextPath}/webresources/chart/g.pie.js"></script>
<script type="text/javascript" src="${contextPath}/webresources/chart/g.dot.js"></script>

<!-- highcharts 图表-->
<script src="${contextPath}/webresources/highcharts/highcharts.js"></script>
<script src="${contextPath}/webresources/highcharts/modules/exporting.js"></script>

<%--
<script src="${contextPath}/webresources/xheditor/xheditor-1.1.14-zh-cn.min.js" type="text/javascript"></script>
<script src="${contextPath}/webresources/uploadify/scripts/swfobject.js" type="text/javascript"></script>
<script src="${contextPath}/webresources/uploadify/scripts/jquery.uploadify.v2.1.0.js" type="text/javascript"></script>
--%>
<script src="${contextPath}/webresources/management/bin/dwz.min.gzjs" type="text/javascript"></script>

<script src="${contextPath}/webresources/management/js/dwz.regional.zh.js" type="text/javascript"></script>

<!-- 自定义JS -->
<script src="${contextPath}/webresources/management/js/customer.js" type="text/javascript"></script>

<!-- jqueryform -->
<script src="${contextPath}/webresources/jqueryform/2.8/jquery.form.js" type="text/javascript" charset="utf-8"></script>

<script type="text/javascript">
var dwzLoadComplete = false;
$(function(){	
	//$.ajaxSettings.global=false; //全局设置关闭loading 数据加载...
	DWZ.init("${contextPath}/webresources/management/dwz.frag.xml", {
		loginUrl:"${contextPath}/login/timeout", loginTitle:"登录",	// 弹出登录对话框
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"${contextPath}/webresources/management/themes"});
			dwzLoadComplete = true;
		}
	});
});
</script>
</head>
<body scroll="no">

<div id="layout">
    <%@ include file="/WEB-INF/views/include/header.jsp"%>
    <%@ include file="/WEB-INF/views/include/left.jsp"%>
    <%@ include file="/WEB-INF/views/include/center.jsp"%>
</div>

<%@ include file="/WEB-INF/views/include/footer.jsp"%>

</body>
</html>