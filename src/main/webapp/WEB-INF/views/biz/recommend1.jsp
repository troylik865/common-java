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
		$('#rankApply').click(function(){
			if(!checkLogin()){
    			troyAlert("请您先首页登录!");
    			return;
    		}
			troyConfirm('确定申请出现在大师排行榜?',function(){
    			$.ajax({
			        type: "POST",
			        url: "/biz/siteMessage/create?type=rank",
			        data: "",
			        success: function (msg) {
            			var obj = jQuery.parseJSON(msg);
            			if(obj.resultCode == "200"){
            				troyAlert(obj.resultMsg);
            			} else {
            				troyAlert(obj.resultMsg);
            			}
			        }
			    }); 
    		});
		});
	});
	
	function loadRankInfo(showDivId,type){
    		$('#'+showDivId).html('')
    		var dataInfo =
		    {
		        divId: showDivId,
		        pageSize: 8,
		        url: "/biz/memberRank/memberReconmend?type="+type,
		        hidePageDiv:"true",
		        data: [
		            {
						type:"method",
						method:"add",
		                width:"23%"
		            }
		        ]
		    };
    		loadData(dataInfo);
    	}
    	
    	function add(dataObj,index){
    		var temp = dataObj.desc;
    		var length = 16;
    		if(temp.length > length ){
    			temp = temp.substring(0,length)+"...";
    		}
    		return "<img class=\"showPic\" src=\""+dataObj.src+"\" title=\""+dataObj.desc+"\"  onclick=\"toMember('"+dataObj.memberNo+"')\" style=\"cursor:pointer\">"+temp+"</img>"; 
    	}
    	
    	function toMember(memberNo){
    			top.$("#lookformember").click();
            	top.$("#mainFrame").addClass("heigher");
                top.window.mainFrame.location =  "/homeWithChartSelf?memberNo="+memberNo;
    	}
    	
	</script>
	<style>
		div{
			height:400px;
		}
		
		.showPic{
			width:230px;
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
			height:200px;
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
	</style>
	
	
</head>
<body>
<div id="tabs" class="contentDiv" >
	<ul>
		<li id="beginTitle" class="memberRecom" style="height: 40px;width: 115px; margin-top: -10px;margin-right:1px">&nbsp;</li>
		<li><a href="#tabs-1">期货</a></li>
		<li><a href="#tabs-2">股票</a></li>
		<li><a href="#tabs-3">外汇</a></li>
		<li><a href="#tabs-4">贵金属</a></li>
		<li><a href="#tabs-5">模拟区</a></li>
		<li style="float:right"><img src="${contextPath}/webresources/images/appear_normal.png" id="rankApply" style="cursor:pointer"/></li>
	</ul>
	<div id="tabs-1" class="contentDiv">
        <div id="tab-1"></div>             	
	</div>
	<div id="tabs-2" class="contentDiv">
        <div id="tab-2"></div>             	
	</div>
	<div id="tabs-3" class="contentDiv">
        <div id="tab-3"></div>             	
	</div>
	<div id="tabs-4" class="contentDiv">
        <div id="tab-4"></div>             	
	</div>
	<div id="tabs-5" class="contentDiv">
        <div id="tab-5"></div>             	
	</div>
</div>
</body>
</html>
