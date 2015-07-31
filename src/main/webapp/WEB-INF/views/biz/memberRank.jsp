<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>jQuery UI Tabs - Sortable</title>
	<link rel="stylesheet" href="${contextPath}/webresources/css/jquery.ui.all.css">
	<script src="${contextPath}/webresources/js/jquery-1.8.0.min.js"></script>
	<script src="${contextPath}/webresources/js/jquery.ui.core.js"></script>
	<script src="${contextPath}/webresources/js/jquery.ui.widget.js"></script>
	<script src="${contextPath}/webresources/js/jquery.ui.mouse.js"></script>
	<script src="${contextPath}/webresources/js/jquery.ui.sortable.js"></script>
	<script src="${contextPath}/webresources/js/jquery.ui.tabs.js"></script>
	<link rel="stylesheet" href="${contextPath}/webresources/css/demos1.css">
    <script type="text/javascript" src="${contextPath}/webresources/js/troy.js"></script>
    <link href="${contextPath}/webresources/css/troy.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/webresources/templatemo_style.css" rel="stylesheet" type="text/css"/>
	<script>
	$(function() {
		var tabs = $( "#tabs" ).tabs();
		tabs.tabs( "refresh" );
		tabs.find( ".ui-tabs-nav" ).sortable({
			axis: "x",
			stop: function() {
				tabs.tabs( "refresh" );
			}
		});
		loadRankInfo("tab-1","qh");
		loadRankInfo("tab-2","gp");
		loadRankInfo("tab-3","wh");
		loadRankInfo("tab-4","hj");
		loadRankInfo("tab-5","by");
		$('#beginTitle').addClass('beginTitle');
	});
	
	function loadRankInfo(showDivId,type){
    		$('#'+showDivId).html('')
    		var dataInfo =
		    {
		        divId: showDivId,
		        pageSize: 8,
		        url: "/biz/memberRank/memberRank?investType="+type,
		        data: [
		            {
		                param: "no",
		                width:"11%"
		            },
		            {
		                type:"method",
		                method:"name",
		                width:"14%"
		            },
		            {
		                param: "origion",
		                width:"12%"
		            },
		            {
		                param: "out",
		                width:"12%"
		            },
		            {
		                param: "in",
		                width:"14%"
		            },
		            {
		                param: "last",
		                width:"14%"
		            },
		            {
		                param: "total",
		                width:"12%"
		            },
		            {
		                param: "begin",
		                width:"9%"
		            }
		        ]
		    };
    		loadData(dataInfo);
    	}
    	
    	function name(dataObj,index){
    		var name = dataObj.name;
    		var isValidated = dataObj.isValidated;
    		var temp = name;
    		if(isValidated == '1'){
    			name += "<span style=\"color:red\" title=\""+temp+"\"><img src=\"${contextPath}/webresources/images/icon_5.png\" class=\"coinPic\"/></span>";
    		}
    		return "<a href=\"javascript:void(0)\" style=\"color:blue;\" title=\""+temp+"\" onclick=\"toMember('"+dataObj.no+"',"+index+",'"+isValidated+"')\">"+name+"</a>";
    	}
    	
    	function toMember(no,index,isValidated){
    			top.$("#lookformember").click();
            	top.$("#mainFrame").addClass("heigher");
                top.window.mainFrame.location =  "/homeWithChartSelf?memberNo="+no+"&index="+index+"&isValidated="+isValidated;
    	}
	</script>
	<style>
		
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
			margin-left:0px;
			font-size:12px;
			height:33px;
		}
		
		.beginTitle{
			font-weight:bold;
			font-size:15px;
			line-height:27px;
			width:80px;
			text-align:center;
		}
		
		 .coinPic{
        	width:20px;
        	height:20px;
        }
        
        .divContent .pageDiv{
			margin-top:0px;
			margin-left:50px;
		}
		
		.divContent #ulDiv{
			height:200px;
		}
	</style>
	
	
</head>
<body>
<div id="tabs" class="contentDiv" >
	<ul>
		<li id="beginTitle" class="memberRank" style="height: 40px;width: 115px; margin-top: -10px;margin-right:1px">&nbsp;</li>
		<li><a href="#tabs-1">期货</a></li>
		<li><a href="#tabs-2">股票</a></li>
		<li><a href="#tabs-3">外汇</a></li>
		<li><a href="#tabs-4">贵金属</a></li>
		<li><a href="#tabs-5">模拟区</a></li>
	</ul>
	<div id="tabs-1" class="contentDiv">
		<div id="titleDiv">
       		<ul>
             <li style="width:5%;">编号</li>
             <li style="width:15%">投资者</li>
             <li style="width:12%">初期资金</li>
             <li style="width:13%">累计出金</li>
             <li style="width:13%">累计入金</li>
             <li style="width:13%">最新权益</li>
             <li style="width:15%">累计盈亏</li>
             <li style="width:14%">起始时间</li>
            </ul>
        </div>
        <div id="tab-1"></div>             	
	</div>
	<div id="tabs-2" class="contentDiv">
		<div id="titleDiv">
	       		<ul>
	             <li style="width:5%;">编号</li>
	             <li style="width:15%">投资者</li>
	             <li style="width:12%">初期资金</li>
	             <li style="width:13%">累计出金</li>
	             <li style="width:13%">累计入金</li>
	             <li style="width:13%">最新权益</li>
	             <li style="width:15%">累计盈亏</li>
	             <li style="width:14%">起始时间</li>
	            </ul>
	        </div>
	        <div id="tab-2"></div>  
	</div>
	<div id="tabs-3" class="contentDiv">
		<div id="titleDiv">
       		<ul>
             <li style="width:5%;">编号</li>
             <li style="width:15%">投资者</li>
             <li style="width:12%">初期资金</li>
             <li style="width:13%">累计出金</li>
             <li style="width:13%">累计入金</li>
             <li style="width:13%">最新权益</li>
             <li style="width:15%">累计盈亏</li>
             <li style="width:14%">起始时间</li>
            </ul>
        </div>
        <div id="tab-3"></div>  
	</div>
	<div id="tabs-4" class="contentDiv">
		<div id="titleDiv">
       		<ul>
             <li style="width:5%;">编号</li>
             <li style="width:15%">投资者</li>
             <li style="width:12%">初期资金</li>
             <li style="width:13%">累计出金</li>
             <li style="width:13%">累计入金</li>
             <li style="width:13%">最新权益</li>
             <li style="width:15%">累计盈亏</li>
             <li style="width:14%">起始时间</li>
            </ul>
        </div>
        <div id="tab-4"></div>  
	</div>
	<div id="tabs-5" class="contentDiv">
		<div id="titleDiv">
       		<ul>
             <li style="width:5%;">编号</li>
             <li style="width:15%">投资者</li>
             <li style="width:12%">初期资金</li>
             <li style="width:13%">累计出金</li>
             <li style="width:13%">累计入金</li>
             <li style="width:13%">最新权益</li>
             <li style="width:15%">累计盈亏</li>
             <li style="width:14%">起始时间</li>
            </ul>
        </div>
        <div id="tab-5"></div>  
	</div>
</div>
</body>
</html>
