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
    		height:300px;
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
		
		#ulDiv a {
			color:blue;
		}
		
		.bottomLine{
		}
    	</style>
		
		<script>
			$(function(){
				var flag = '<%=request.getParameter("flag")%>';
				if(flag == "1"){
					troyAlert("交易修改成功！");
				}else if(flag == "0") {
					troyAlert("交易修改失败！");
				}
				loadDetail('showDetail');
				loadPublishDetail('showDetail1');
			});
		
			function loadDetail(showDivId){
				$('#showDetail').html('');
		    	var dataInfo =
				    {
				        divId: showDivId,
				        pageSize: 8,
				        url: "/biz/transRecord/myTransRecords",
				        data: [
				            {
				                param: "investTypeStr",
				                width:"5%"
				            },
				            {
				                param: "importDate",
				                width:"15%"
				            },
				            {
				                param: "currIncomeWYuan",
				                width:"15%"
				            },
				            {
				                param: "currOutcomeWYuan",
				                width:"12%"
				            },
				            {
				                param: "currValueWYuan",
				                width:"13%"
				            },
				            {
				                param: "lastDayValueWYuan",
				                width:"14.5%"
				            },
				             {
				             	type:"method",
				             	method:"statusName",
				                width:"9%"
				            },
				            {
				                type:"method",
				                method:"operator",
				                width:"16%"
				            }
				        ]
				    };
		    		loadData(dataInfo);
		    	}
		    	
		    	function statusName(rowData,index){
		    		if('审核退回' == rowData.statusName){
		    			return "<span title=\""+rowData.failReason+"\">"+rowData.statusName+"</span>";
		    		}
		    		return rowData.statusName;
		    	}
			    	
				function loadPublishDetail(showDivId){
					$('#'+showDivId).html('');
			    	var dataInfo =
					    {
					        divId: showDivId,
					        pageSize: 8,
					        url: "/biz/publishMessage/listByMemberId",
					        data: [
					            {
					            	type:"method",
					            	method:"gmtCreateDate",
					                width:"25%",
					            },
					             {
					            	type:"method",
					            	method:"content",
					                width:"40%",
					            },
					            {
					                param: "statusName",
					                width:"15%"
					            },
					            {
					            	type:"method",
					            	method:"status",
					                width:"15%",
					            }
					        ]
					    };
			    		loadData(dataInfo);
			    	}
		    	
		    	
		    	
		    	function gmtCreateDate(rowData,index){
		    		return rowData.gmtCreateDateTime;
		    	}
		    	
		    	function content(rowData,index){
		    		return rowData.content;
		    	}
		    	
		    	function status(rowData,index){
		    		var status = rowData.status;
		    		if(status != 'S'){
		    			return "<a href=\"#\" onclick=\"editMessage('"+rowData.id+"')\">编辑</a> | <a href=\"#\" onclick=\"delMessage('"+rowData.id+"')\">删除</a>";
		    		}
		    		return "<span style=\"color:red\">已审核</span>";
		    	}
		    	
		    	
		    	function operator(rowData,index){
		    		var status = rowData.status;
		    		if(status != 'S'){
		    			return "<a href=\"#\" onclick=\"edit('"+rowData.id+"')\">编辑</a> | <a href=\"#\" onclick=\"del('"+rowData.id+"')\">删除</a>";
		    		}
		    		return "<span style=\"color:red\">已审核</span>";
		    	}
		    	
		    	
		    	function del(id) {
    				troyConfirm('确定要删除该交易吗?',function(){
    					$.ajax({
				        type: "POST",
				        url: "/biz/transRecord/delete/"+id,
				        data: "",
				        success: function (msg) {
		        			var obj = jQuery.parseJSON(msg);
		        			if(obj.resultCode == "200"){
		        				troyAlert(obj.resultMsg);
								loadDetail('showDetail');
		        			} else {
		        				troyAlert(obj.resultMsg);
		        			}
				        }
				    });
		    		});
		    	}
		    	
		    	function delMessage(id) {
    				troyConfirm('确定要删除该发布信息吗?',function(){
    					$.ajax({
				        type: "POST",
				        url: "/biz/publishMessage/delete/"+id,
				        data: "",
				        success: function (msg) {
		        			var obj = jQuery.parseJSON(msg);
		        			if(obj.resultCode == "200"){
		        				troyAlert(obj.resultMsg);
								loadPublishDetail('showDetail1');
		        			} else {
		        				troyAlert(obj.resultMsg);
		        			}
				        }
				    });
		    		});
		    	}
		    	
		    	function edit(id){
		    		window.location.href = "/biz/transRecord/update/"+id;
		    	}
		    	function editMessage(id){
		    		window.location.href = "/biz/publishMessage/update/"+id;
		    	}
		</script>
</head>

<body>
<div id="templatemo_main_wrapper">
    <div id="templatemo_main">
        <div><img src="${contextPath}/webresources/images/templatemo_menu.png" class="content1"/></div>
        <div class="left contentBottom">
                <div  style="margin:0 auto;width: 95%">
                   <div style="height:380px;">
	                   	 <div class="personalTitle bottomLine" >交易导入明细</div>
	                    <div id="titleDiv">
	                    	<ul>
	                    		<li style="width:10%;margin-left:-30px;">投资方向</li>
	                    		<li style="width:14%">导入时间</li>
	                    		<li style="width:14%">当日入金(元)</li>
	                    		<li style="width:14%">当日出金(元)</li>
	                    		<li style="width:14%">当日权益(元)</li>
	                    		<li style="width:14%">上日权益(元)</li>
	                    		<li style="width:14%">状态</li>
	                    		<li style="width:10%">操作</li>
	                    	</ul>
	                    </div>
	                    <div id="showDetail" style="height:154px;padding-top:1px;"></div>
                    </div>
                    <div>
	                    <div class="personalTitle bottomLine" >实盘消息发布</div>
	                    <div id="titleDiv">
	                    	<ul>
	                    		<li style="width:15%">发布时间</li>
	                    		<li style="width:50%">发布信息</li>
	                    		<li style="width:15%">状态</li>
	                    		<li style="width:15%">操作</li>
	                    	</ul>
	                    </div>
	                    <div id="showDetail1" style="height:154px;padding-top:1px;"></div>
                    </div>
                </div>
        </div>
        <!-- END of main -->
    </div>
    <!-- END of main wrapper -->
</body>
</html>