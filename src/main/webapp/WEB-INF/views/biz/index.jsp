<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@include file="/WEB-INF/views/biz/troyAlert.jsp" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>投资大师</title>
    <meta name="keywords" content="投资"/>
    <link href="${contextPath}/webresources/templatemo_style.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="${contextPath}/webresources/css/orman.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="${contextPath}/webresources/css/nivo-slider.css" type="text/css" media="screen"/>
    <link rel="stylesheet" type="text/css" href="${contextPath}/webresources/css/ddsmoothmenu.css"/>
    <link rel="stylesheet" type="text/css" href="${contextPath}/webresources/css/troy.css"/>
    <script type="text/javascript" src="${contextPath}/webresources/js/ddsmoothmenu.js"></script>
    <script type="text/javascript">

        ddsmoothmenu.init({
            mainmenuid: "templatemo_menu", //menu DIV id
            orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
            classname: 'ddsmoothmenu', //class added to menu's outer DIV
            //customtheme: ["#1c5a80", "#18374a"],
            contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
        });
        function clearText(field) {
            if (field.defaultValue == field.value) field.value = '';
            else if (field.value == '') field.value = field.defaultValue;
        }
    </script>
    <link rel="stylesheet" href="${contextPath}/webresources/css/slimbox2.css" type="text/css" media="screen"/>
    <script type="text/JavaScript" src="${contextPath}/webresources/js/slimbox2.js"></script>
    <style type="text/css">
        .validate {
            width: 75px;
            height: 26px;
            line-height: 25px;
            position: absolute;
            bottom: 39px;
            left: 160px;
        }
        .first{
        	margin-top:-10px;
        }
        .heightest {
        	height:2550px;
        }
        
        .heigher {
        	height:1450px;
        }
        
        .img{
        	width:240px;
        	height:150px;
        	margin-left:2px;
        }
        
        .normal{
        	height:1050px;
        }
        
        .boldTitle {
        	position: absolute;
			left: 200px;
			top: 50px;
			font-size: 14px;
			font-weight: bold;
			font-family: 微软雅黑;
			color:black;
        }
        
        .bottomFont{
        	position: relative;
			top: 20px;
			left:10px;
			font-size: 14px;
			font-weight: bold;
			font-family: 微软雅黑;
			color:black;
        }
        
        .bottomFont1{
        	position: relative;
			top: 20px;
			left:10px;
        	margin-top:10px;
			font-size: 12px;
			font-family: 微软雅黑;
			color:black;
        }
    </style>
	<script>
		var isLogin = '${isLogin}';
        $(document).ready(function (){
            $("#lookformember").click(function(){
            	$("#first").hide();
            	reClass();
                window.mainFrame.location = "memberSearch";
            	$("#notFirst").show();
            });
            $("#companyDesc").click(function(){
            	$("#first").hide();
            	reClass();
                window.mainFrame.location = "companyDesc";
            	$("#notFirst").show();
            });
            $("#contactUs").click(function(){
            	$("#first").hide();
            	reClass();
                window.mainFrame.location = "contactUs";
            	$("#notFirst").show();
            });
            $("#firstPage").click(function(){
                window.location.reload();
            });
            $("#recommend").click(function(){
            	$("#first").hide();
            	reClass();
                //window.mainFrame.location = "homeWithChart";
                window.mainFrame.location = "recommend";
            	$("#notFirst").show();
            });
            $("#personalPage").click(function(){
            	if(isLogin != 1){
            		jAlert("请您先首页登录！","温馨提示");
            		return;
            	}
            	$("#first").hide();
            	reClass();
            	$("#notFirst").show();
                window.mainFrame.location = "personalPage";
            	$("#notFirst").show();
            });
            $("#softCenter").click(function(){
            	$("#first").hide();
            	$("#mainFrame").addClass("contentFrame");
            	$("#mainFrame").addClass("heightest");
                window.mainFrame.location = "softCenter";
            	$("#notFirst").show();
            });
            $("#newMember").click(function(){
            	$("#first").hide();
            	$("#mainFrame").addClass("contentFrame");
            	$("#mainFrame").addClass("heightest");
                window.mainFrame.location = "newMember";
            	$("#notFirst").show();
            });
        });
        
        function reClass() {
	        if($("#mainFrame").hasClass("heightest")){
            	$("#mainFrame").removeClass("heightest");
	        }
	        if(!$("#mainFrame").hasClass("contentFrame")){
            	$("#mainFrame").addClass("contentFrame");
	        }
        }
    </script>
</head>

<body>
<div id="templatemo_wrapper_h" style="background-color:rgb(241, 241, 241)">
    <div id="templatemo_header_wh">
        <div id="templatemo_header" >
        <div id="site_title"><a href="#" rel="nofollow"><span style="left:200px;position:absolute;top:30px;font-family: 微软雅黑;">固定电话：${headPhone1}</span><br><span class="boldTitle">投资服务热线：${headPhone2}</span></a></div>
            <div id="templatemo_menu" class="ddsmoothmenu">
                <ul>
                    <li><a href="javascript:void(0)" id="firstPage">首页</a></li>
                    <li><a href="javascript:void(0)" id="companyDesc">参赛规则</a></li>
                    <li><a href="javascript:void(0)" id="recommend">大师推荐</a></li>
                    <li><a href="javascript:void(0)" id="lookformember">寻找大师</a></li>
                    <li><a href="javascript:void(0)" id="softCenter">实用软件下载</a></li>
                    <li><a href="javascript:void(0)" id="personalPage">会员专区</a></li>
                    <li><a href="javascript:void(0)" id="newMember">新手入门</a></li>
                    <li><a href="javascript:void(0)" id="contactUs">联系我们</a></li>
                </ul>
            </div>
        </div>
    </div> 
</div>
	<div style="width:100%;" id="headFrame">
    <iframe src="head" class="headFrame"  scrolling="no" frameborder="0"></iframe>
	</div>
	<!--<div style="width:100%;">
	    <iframe src="search" name="searchFrame" id="searchFrame" class="searchFrame"  scrolling="no" ></iframe>
	</div>
	-->
<div style="width:100%;" id="first">
	<iframe src="first" name="firstFrame" class="first" id="first"  scrolling="no"  frameborder="0"></iframe>
</div>	
<div id="notFirst" style="display:none">
	<div id="templatemo_main_wrapper">
	    <div id="templatemo_main">
	        <div id="sidebar" class="left">
	            <iframe src="leftGuide" class="leftGuideFrame" id="leftGuideFrame" name="leftGuideFrame"  scrolling="no"  frameborder="0" ></iframe>
	        </div>
	
	        <div id="content">
	            <iframe src="main" name="mainFrame" id="mainFrame"  scrolling="no"  frameborder="0"></iframe>
	        </div>
	        <div class="cleaner"></div>
	    </div>
	</div>
</div>
<div style="text-align:center">
	<img src="${contextPath}/webresources/images/pic_6.png" class="img"/>
	<img src="${contextPath}/webresources/images/pic_7.png" class="img"/>
	<img src="${contextPath}/webresources/images/pic_8.png" class="img"/>
	<img src="${contextPath}/webresources/images/pic_1.png" class="img"/>
</div>
	<div id="templatemo_footer_wrapper">
	    <div id="templatemo_footer" style="margin-top:-30px;">
	        <div class="footer_left" style="width: 170px;height: 83px;">
	        	<span class="bottomFont1">
	       			全国客服热线
	       		</span>
	       			<br>
	       		<span class="bottomFont">
	       			${footPhone}
	       		</span>
	        </div>
	        <div class="footer_right">
	            <p style="padding-top:30px;padding-left:50px">COPYRIGHT &copy; 2014.投资大师网 ALL RIGHTS RESERVED</p>
	        </div>
	        <div class="cleaner"></div>
	    </div>
	</div>
</body>
</html>