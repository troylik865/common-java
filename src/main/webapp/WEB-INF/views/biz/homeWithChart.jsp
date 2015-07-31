<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Home</title>
    <meta name="keywords" content="free template,  ecommerce, online shopping, store" />
    <meta name="description" content="Floral Shop is free website template for ecommerce or online shopping websites. Products, Shopping Cart, FAQs and Checkout pages are included." />
    <script type="text/javascript" src="${contextPath}/webresources/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${contextPath}/webresources/js/highcharts.js"></script>
    <script type="text/javascript" src="${contextPath}/webresources/js/exporting.js"></script>
    <script type="text/javascript" src="${contextPath}/webresources/js/troy.js"></script>
    <script type="text/javascript" src="${contextPath}/webresources/js/chartInit.js"></script>
    <script type="text/javascript" src="${contextPath}/webresources/js/bootstrap-datepicker.js"></script>
	<%@include file="/WEB-INF/views/biz/showDiv.jsp" %>    
    <link href="${contextPath}/webresources/templatemo_style.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/webresources/css/troy.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/webresources/css/bootstrap.css" rel="stylesheet">
    <link href="${contextPath}/webresources/css/datepicker.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${contextPath}/webresources/css/ddsmoothmenu.css"/>
	<script type="text/javascript">
	var transRecordIds = new Array();
    //时间格式化
    var timeFormat = '%Y%m%d';
    //会员号
    var memberNo = "";
    //投资方向,默认期货
    var investType="qh";
    var startDate="";
    var endDate="";
    $(document).ready(function (){
    	var collect = "${collect}";
    	if(collect != '1'){
    	 	$('#collect').hide();
    	}
    
    	$('#startDate').datepicker({
            format: 'yyyy-mm-dd'
        });
         $('#endDate').datepicker({
            format: 'yyyy-mm-dd'
        });
    	memberNo=$("#memberNo").val();
    	loadTransRecordWeekData('trasnRecordWeek');
    	loadChart();
    	loadMemberDesc();
    		var isValidated = '${isValidated}';
    		if(isValidated == '1'){
    			$('#sai').html("<img src=\"${contextPath}/webresources/images/icon_5.png\" class=\"coinPic\"/>");
    		}
    });
    
    function loadChart(){
    	var chartData = getAllData(memberNo,$("#investType").val(),$("#startDate").val(),$("#endDate").val());
    	var values = getValue(chartData);
    	var xValue = getXValue(chartData);
    	Highcharts.setOptions({
            colors:['red']
        });
        $('#container').highcharts({
            chart: {
                type: 'spline'
            },
            title: {
                text: '最新权益'
            },
            xAxis: {
                categories: values
            },
            yAxis: {
                title: {
                    text: ''
                },
                labels: {
                    formatter: function() {
                        return this.value +'元'
                    }
                }
            },
            tooltip: {
                crosshairs: true,
                shared: true
            },
            plotOptions: {
                spline: {
                    marker: {
                        radius: 1,
                        lineColor: 'red',
                        fillColor: 'red',
                        lineWidth: 1
                    }
                }
            },
            series: [{
                name: '最新权益',
                marker: {
                    symbol: 'circle'
                },
                data: xValue
           }]
        });
        
    	var chartData = getTotalData(memberNo,$("#investType").val(),$("#startDate").val(),$("#endDate").val());
    	var values = getValue(chartData);
    	var xValue = getXValue(chartData);
        $('#totalContainer').highcharts({
            chart: {
                type: 'spline'
            },
            title: {
                text: '累计盈亏'
            },
            xAxis: {
                categories: values
            },
            yAxis: {
                title: {
                    text: ''
                },
                labels: {
                    formatter: function() {
                        return this.value +'%'
                    }
                }
            },
            tooltip: {
                crosshairs: true,
                shared: true
            },
            plotOptions: {
                spline: {
                    marker: {
                        radius: 1,
                        lineColor: 'red',
                        fillColor: 'red',
                        lineWidth: 1
                    }
                }
            },
            series: [{
                name: '累计盈亏',
                marker: {
                    symbol: 'circle'
                },
                data: xValue
           }]
        });
    }

    //加载图片信息
    function loadImageData(showDivId,type){
        var memberNo=$("#memberNo").val();
    	$('#'+showDivId).html('');
		var dataInfo =
	    {
	        divId: showDivId,
	        pageSize: 1,
	        url: "/biz/attach/list?transRecordIds="+transRecordIds+"&type="+type,
	        data: [
	            	{
		                type:"method",
		                method:"opertor",
		                width:"100%"
		            }
	        ]
	    };
		loadData(dataInfo);
	}

    //加载一周内的交易明细
    function loadTransRecordWeekData(showDivId){
        var memberNo=$("#memberNo").val();
    	$('#'+showDivId).html('');
    	transRecordIds = new Array();
		var dataInfo =
	    {
	        divId: showDivId,
	        pageSize: 5,
	        url: "/biz/transRecord/findTransRrcordByMemberAndTypeForWeek?memberNo="+memberNo,
	        dealBack:"dealBack",
	        data: [
	            {
	                type:"method",
	                method:"investion",
	                width:"20%"
	            },
	            {
	                param: "origionValueWYuan",
	                width:"23%"
	            },
	             {
	                param: "currValueWYuan",
	                width:"23%"
	            },
	            {
	                param: "importDate",
	                width:"20%"
	            }
	        ]
	    };
		loadData(dataInfo);
	}
	
	function dealBack(){
    	loadImageData('img1',"biz_trans_record1");
    	loadImageData('img2',"biz_trans_record2");
	}
	
	function investion(rowData,index){
		transRecordIds.push(rowData.id);
		return rowData.investTypeStr;
	}
	
	function loadMemberDesc(){
		var showDivId = 'memberDesc';
		$('#'+showDivId).html('');
		var dataInfo =
	    {
	        divId: showDivId,
	        pageSize: 5,
	        url: "/biz/attentionRecord/findByMember?memberNo="+$('#memberNo').val(),
	        data: [
	            {
	            	type:"method",
	            	method:"getContent",
	                width:"100%"
	            }
	        ]
	    };
		loadData(dataInfo);
	}
	
	function getContent(rowData,index){
		return rowData.content;
	}

    function opertor(rowData,index){
        return "<img src='/biz/attach/download?fileId="+rowData.fileId+"'  class=\"showPic\" onclick=\"showBigPic(this)\"/>";
	}

    //刷新图标数据
    
	function refresh(){
		var investType=$("#investType").val();
		var startDate=$("#startDate").val();
		var endDate=$("#endDate").val();
		loadChart();
	}

    //对图标设置新的数据
	function setDataForChar(newData){
		chart.series[0].setData(newData);
	}
	
	function reportShow(){
			showDiv("dialog-modal2",300,350);
    }
    
    function showBigPic(img){
    		$('#showBig').attr("src",img.src);
			showDiv("dialog-modal3",700,500);
    }
    
    function showDiv(id,width,height){
    		$( "#"+id ).dialog({
					height: height,
		            width:width,
					modal: true
			});
			$( "#"+id ).parent().css({top:250});
    	}

	function report(){
    		troyConfirm('确定要举报该大师吗?',function(){
	                $.ajax({
				        type: "POST",
				        url: "/biz/siteMessage/create?type=report",
				        data: $("#form3").serialize(),
				        success: function (msg) {
	            			var obj = jQuery.parseJSON(msg);
	            			if(obj.resultCode == "200"){
	            				troyAlert(obj.resultMsg);
	            				$(".ui-icon-closethick").click();
	            			} else {
	            				troyAlert(obj.resultMsg);
	            			}
				        }
				    });
            	});
    	}
    	
    	$(function(){
    		var $div_li =$("div.tab_menu ul li");
		    $div_li.click(function(){
				$(this).addClass("selected") //当前<li>元素高亮
					   .siblings().removeClass("selected");  
	            var index =  $div_li.index(this);  
				$("div.tab_box > div") 
						.eq(index).show()   
						.siblings().hide();
			}).hover(function(){
				$(this).addClass("hover");
			},function(){
				$(this).removeClass("hover");
			})
    	});
    	
    	function toMemberRoom(){
    		var temp = "${memberLink}";
    		if('' == temp || null == temp){
    			return;
    		}
    		window.open(temp,'大师工作室','width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-30)+ ',top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
    	}
    	
    	function toAttent(){
    			top.$("#personalPage").click();
            	top.$("#mainFrame").removeClass("heigher");
                top.window.mainFrame.location =  "/biz/member/attention?memberNo=${memberNo}";
    	}
    	
    	function collect(){
    		troyConfirm('确定要收藏该大师吗?',function(){
	                $.ajax({
				        type: "POST",
				        url: "/biz/memberCollect/create?memberNo=${memberNo}",
				        data: $("#form3").serialize(),
				        success: function (msg) {
	            			var obj = jQuery.parseJSON(msg);
	            			if(obj.resultCode == "200"){
	            				troyAlert(obj.resultMsg);
	            				$('#collect').hide();
	            				top.window.leftGuideFrame.location.reload();
	            			} else {
	            				troyAlert(obj.resultMsg);
	            			}
				        }
				    });
            	});
    	}
    	
    	function closeWindow() {
    		$(".ui-icon-closethick").each(function(){
	            	$(this).click();
	       });
    	}
    	
    </script>
    <style>
		
		#ulDiv {
			height:120px;
			margin-left:20px;
    		border: 1px #A7A4A4 solid;
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
			font-size:12px;
		}
		
		
		.contentD{
    		height:150px;
    	}
    	
    	.pageDiv{
		    font-size: 12px;
		    margin-top:0px;
		    float:right;
		    margin-right:20px;
		}
		
		.divContent ul li {
			margin-left:10px;
			height:20px;
			font-size:12px;
			text-align:center;
			
		}
		
		.titlePic{
    		width:100px;
    		height:38px;
    		position:absolute;
    		margin-top:-2px;
    	}
    	
    	.queryTitle{
    		margin-left:20px;
    		color:rgb(206, 1, 1);
    		font-weight:bold;
    		width:105px;
    	}
    	
    	.inputStyle{
    		width:100px;
   			font-size:12px; 	
    	}
    	
    	#trasnRecordWeek #ulDiv {
    		height:300px;
    		width:450px;
    	}
    	
    	#templatemo_main_wrapper table {
    		font-size:12px;
    	}
    	
    	.queryButton{
    		background: url(../../webresources/images/query_normal.png) no-repeat;
    		width:55px;
    		line-height:16px;
    	}
    	.submitButton{
    		background: url(../../webresources/images/submit_normal.png) no-repeat;
    		width:70px;
    		line-height:16px;
    		color:white;
    	}
    	
    	.title{
    		text-align:left;
    		padding-left:20px;
    		color:rgb(162, 0, 0);
    		margin-top:50px;
    		font-size:14px;
    	}
    	
    	.contentDesc #ulDiv {
    		height:230px;
    	}
    	
    	.contentDesc #ulDiv li {
    		text-align:left;
    		margin-left:40px;
    		height:25px;
    	}
    	
    	.bigTable2{
            width:990px;
            height: 550px;;
            margin-left: auto;
            margin-right: auto;
        }
        
        .tab { width:98%;margin-left:10px;}
		 .tab_menu { clear:both;}
		 .tab_menu li { float:left; text-align:center; cursor:pointer; list-style:none; padding:1px 6px; margin-right:4px; background:#F1F1F1; border:1px solid #898989; border-bottom:none;}
		 .tab_menu li.hover { background:#DFDFDF;}
		 .tab_menu li.selected { color:#FFF; background:#6D84B4;}
		 .tab_box { clear:both; border:1px solid #898989; height:780px;}
		 .tab_hide{display:none}
        
        
        .infoTable{
        	width:100%;
        	margin-top:80px;
        }
        
        .infoTable tr {
        	height:23px;
        }
        
        
        .content{
        	text-align:left;
        }
        
        .pic{
        	width:200px;
        	height:200px;
        }
        
        .showPic{
        	width:230px;
        	height:120px;
        }
    </style>
</head>
<body>
<div id="templatemo_main_wrapper" >
        <div>
        <div><img src="${contextPath}/webresources/images/pic (3).png" class="titlePic"/>
        <img src="${contextPath}/webresources/images/templatemo_menu.png" class="content1"/></div>
        <br>
            <p class="title">实盘交易信息</p>
            <div >
                <div style="float:left;width:200px;margin-left:20px;font-size:14px;">投资大师：${name} &nbsp;&nbsp;<a id="collect" href="javascript:void(0)" onclick="collect()" style="color:red">(收藏该大师)</a><span id="sai"></span></div><input type="hidden" id="memberNo" value='${memberNo}'/> 
               <div style="float:left;width:200px;font-size:14px;"> 综合排名：${rank} </div> <img src="${contextPath}/webresources/images/icon (8).png" title="点击跳转到该大师的工作室！" onclick="toMemberRoom()" style="cursor:pointer" /><img src="${contextPath}/webresources/images/watch_noraml.png" style="cursor:pointer" onclick="toAttent()"/>
            </div>
            <br>
            <table>
            <td><div class="queryTitle">按日期查询：</div></td>
            <td>
            	类型：
            </td>
            <td  style="width:13%">
            	<select id="investType" class="troySelect" name="investType" >
                      <option value="qh" >&nbsp;&nbsp;期货&nbsp;&nbsp;</option>
                      <option value="gp">&nbsp;&nbsp;股票&nbsp;&nbsp;</option>
                      <option value="wh">&nbsp;&nbsp;外汇&nbsp;&nbsp;</option>
                      <option value="hj">&nbsp;&nbsp;贵金属&nbsp;&nbsp;</option>
                      <option value="by">&nbsp;&nbsp;模拟区&nbsp;&nbsp;</option>
                </select>
            </td>
            <td>
                           起始时间：<input type="text" name="startDate" id="startDate" class="inputStyle" style="height:26px;" readonly="readonly"/>
					 到：<input type="text" name="endDate" id="endDate" class="inputStyle" style="height:26px;" readonly="readonly"/>
					<input type="button" value="" onclick="refresh()" class="btn  queryButton"/>
            </td>
          </table>
          <div class="tab">
			<div class="tab_menu">
				<ul>
					<li class="selected" style="font-size:14px">最新权益</li>
					<li style="font-size:14px">累计盈亏</li>
				</ul>
			</div>
			<div class="tab_box">
				 <div id="tab2">
					<div id="container" style="min-width:700px;height:400px">
		          
					</div>
				</div>
				 <div class="tab_hide" id="tab3">
					<div id="totalContainer" style="min-width:700px;height:400px">
					
					</div>
				</div>
			</div>
			<table style="width:97%;margin-top:-370px;">
		              <tr>
		              <td width="60%">
		                 <div>
		                    <!-- <input type="button" style="float: right;margin-right:10px;" class="btn btn-block btn-lg btn-regist" value="下载详情"/> -->
		                    <span class="chartTitle">最近交易明细</span>
		                    <div id="titleDiv" style="width:450px;">
		                    	<ul>
		                    		<li style="width:23%;font-size:14px;">投资方向</li>
		                    		<li style="width:20%;margin-left:10px;font-size:14px;">初期资金</li>
		                    		<li style="width:20%;margin-left:10px;font-size:14px;">最新权益</li>
		                    		<li style="width:20%;margin-left:10px;font-size:14px;">导入时间</li>
		                    	</ul>
		                    </div>
		                    <div id="trasnRecordWeek"/>
		                 </div>
		              </td>
		              <td width="40%">
		                   <div>
		                      <p class="chartTitle">资金权益及持仓</p>
		                      <div id="img1" class="contentD" style="margin-top:-1px;"></div>
		                   </div>
		                   <div>
		                       <p class="chartTitle">资金权益及持仓</p>
		                       <div id="img2" class="contentD" style="margin-top:-1px;">
		                       </div>
		                   </div>
		              </td>
		              </tr>
		          </table>
		</div>
    <div id="titleDiv" style="width:100%;">
        <ul style="margin-top:5px;">
           <li style="width:70%;font-size:16px;font-weight:bold;color:rgb(224, 0, 0)">大师交易评价</li>
          <li style="width:20%"><img src="${contextPath}/webresources/images/report_normal.png" style="cursor:pointer;margin-top:2px;float:right;margin-right:30px;" onclick="reportShow()"/></li>
        <ul>
     </div>
     <div class="contentDesc" style="margin-top:-1px;width:97%" id="memberDesc">
     </div>
    <!-- END of main -->
</div>
<div id="dialog-modal2" title="举报大师" style="display:none;" >
        <form id="form3">
        <input type="hidden" name="memberId" id="memberId" value="${memberId}"/>
		<table style="margin-top:30px;">
            <tr>
            	<td>举报内容</td>
            	<td></td><td><textarea rows=10 cols=30 id="contentArea" name="content" style="margin-top:10px;"></textarea></td>
            </tr>
        </table>
        <div style="margin-left:100px;"><input type="button" value="提  交" class="btn  submitButton" style="margin-top:10px;" id="add3" onclick="report()"/>  <input type="button" value="关闭" class="btn  submitButton" style="margin-top:10px;" id="closeButton" onclick="closeWindow()"/></div>
        </form>
	</div>
	<div id="dialog-modal3" title="图片预览" style="display:none;" >
		<img id="showBig" style="width:660px;height:440px"/>
	</div>
	<div class="bigTable2" id="showRankPic">
    	<iframe src="recommend2" class="bigTable2" id="recommend"  scrolling="no"  frameborder="0"></iframe>
    </div>
</body>
</html>