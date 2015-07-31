<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Home</title>
    <meta name="keywords" content="free template,  ecommerce, online shopping, store"/>
    <meta name="description"
          content="Floral Shop is free website template for ecommerce or online shopping websites. Products, Shopping Cart, FAQs and Checkout pages are included."/>
    <link href="${contextPath}/webresources/templatemo_style.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/webresources/css/troy.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${contextPath}/webresources/css/ddsmoothmenu.css"/>
    <script type="text/javascript" src="${contextPath}/webresources/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${contextPath}/webresources/js/troy.js"></script>
    <style>
        .personalTitle{
            font-size: 16px;
            color:red;
            margin-bottom:10px;
            margin-top:23px;
        }
        .contentDiv{
            margin-top:20px;
        }
        
		.pageDiv{
		    font-size: 12px;
		    margin-top:0px;
		    float:right;
		    margin-right:20px;
		}
		
		.divContent ul{
			margin-left:0px;
		}
		
		.divContent ul li {
			display: inline;
			float: left;
			margin-left: 0px;
			font-size: 12px;
			height: 35px;
			line-height:35px;
		}
		
		#titleDiv{
        	height:25px;
    		border-top: 1px #A7A4A4 solid;
    		border-left: 1px #A7A4A4 solid;
    		border-right: 1px #A7A4A4 solid;
    		border-radius: 4px;
    		line-height:22px;
    		font-size:13px;
    		font-weight:bold;
		}
		
		#ulDiv{
    		height:700px;
    		border: 1px #A7A4A4 solid;
    		margin-top:-3px;
		}
		
		#ulDiv ul{
			margin-top:0px;
		}
		
		#titleDiv ul{
			margin-top:1px;
		}
		
		#titleDiv ul li {
			display: inline;
			float: left;
			margin-left:0px;
			text-align:center;
		}
		
		.bottomLine{
		}
    	</style>
		
		<script>
			$(function(){
				loadDetail('showDetail');
			});
		
			function loadDetail(showDivId){
		    	var dataInfo =
				    {
				        divId: showDivId,
				        pageSize: 20,
				        url: "/biz/financeTransDetail/list",
				        data: [
				            {
				                param: "date",
				                width:"18%"
				            },
				            {
				                param: "time",
				                width:"15%"
				            },
				            {
				                param: "summary",
				                width:"19%"
				            },
				            {
				                param: "in",
				                width:"15%"
				            },
				            {
				                param: "out",
				                width:"13%"
				            },
				            {
				                param: "left",
				                width:"20%"
				            }
				        ]
				    };
		    		loadData(dataInfo);
		    	}
		</script>
</head>
<body>
<div id="templatemo_main_wrapper">
    <div id="templatemo_main">
        <div><img src="${contextPath}/webresources/images/templatemo_menu.png" class="content1"/></div>
        <div class="left contentBottom">
                <div  style="margin:0 auto;width: 95%">
                    <div class="personalTitle bottomLine" >资金交易明细</div>
                    <div id="titleDiv">
                    	<ul>
                    		<li style="width:20%;margin-left:-30px;">记账日</li>
                    		<li style="width:15%">交易时间</li>
                    		<li style="width:20%">交易摘要</li>
                    		<li style="width:15%">金币收入</li>
                    		<li style="width:15%">金币支出</li>
                    		<li style="width:15%">用户余额</li>
                    	</ul>
                    </div>
                    <div id="showDetail" style="height:154px;padding-top:1px;"></div>
                </div>
        </div>
        <!-- END of main -->
    </div>
    <!-- END of main wrapper -->
</body>
</html>