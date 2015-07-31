<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>新手入门</title>
    <link href="${contextPath}/webresources/templatemo_style.css" rel="stylesheet" type="text/css"/>
	<script src="${contextPath}/webresources/js/jquery-1.8.0.min.js"></script>
</head>
<style>
		.showPic{
			width:170px;
			height:150px;
		}
		
		#titleDiv{
        	height:35px;
    		border-radius: 4px;
    		line-height:22px;
    		font-size:15px;
    		font-weight:bold;
    		margin-top:-10px;
		}
		#titleDiv ul li {
			display: inline;
			float: left;
			margin-left:0px;
			text-align:center;
			height:30px;
			line-height:30px;
		}
		
		.divContent ul{
			margin-top:-10px;
		}
		
		.divContent ul li{
			position:relative;
			display:inline;
			float:left;
			margin-left:10px;
			font-size:12px;
			height:200px;
			line-height:48px;
		}
		
		.beginTitle{
			font-weight:bold;
			font-size:15px;
			line-height:27px;
			width:80px;
			text-align:center;
		}
		.titlePic{
    		width:100px;
    		height:38px;
    		position:absolute;
    		margin-top:-2px;
    	}
    	
    	#tabs{
    		margin-top:20px;
    	}
    	
    	.title {
		    font-size: 20px;
			text-align: center;
			margin-top: 50px;
			margin-bottom: 20px;
    	}
	</style>
	
	<script>
		var index = "${index}";
		$(function (){
			if(null == index || '' == index) {
				$('.content1').each(function (){
					$(this).show();
				});
				return;			
			}
			$('#pic'+index).show();
		});
		
	</script>
<body>
<div style="margin-left:5px;">
<img src="${contextPath}/webresources/images/templatemo_menu.png" class="content1" /></div>
<br>
<div style="margin-left:10px;margin-top:30px;">
<div class="title">${desc}</div>
<img src="${contextPath}/webresources/images/1.jpg" class="content1" id="pic1"  style="display:none"/>
<img src="${contextPath}/webresources/images/2.jpg" class="content1" id="pic2"    style="display:none"/>
<img src="${contextPath}/webresources/images/3.jpg" class="content1" id="pic3"    style="display:none"/>
<img src="${contextPath}/webresources/images/4.jpg" class="content1" id="pic4"    style="display:none"/>
<img src="${contextPath}/webresources/images/5.jpg" class="content1"  id="pic5"  style="display:none" />
<img src="${contextPath}/webresources/images/6.jpg" class="content1"  id="pic6"   style="display:none"/>
<img src="${contextPath}/webresources/images/7.jpg" class="content1"  id="pic7"   style="display:none"/>
<img src="${contextPath}/webresources/images/8.jpg" class="content1"  id="pic8"   style="display:none" />
</div>
</body>
</html>
