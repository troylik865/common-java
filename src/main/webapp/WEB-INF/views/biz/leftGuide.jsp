<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@include file="/WEB-INF/views/biz/troyAlert.jsp" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>投资大师</title>
    <meta name="keywords" content="free template,  ecommerce, online shopping, store" />
    <meta name="description" content="Floral Shop is free website template for ecommerce or online shopping websites. Products, Shopping Cart, FAQs and Checkout pages are included." />
	<link href="${contextPath}/webresources/css/troy.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/webresources/templatemo_style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${contextPath}/webresources/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="${contextPath}/webresources/js/troy.js"></script>
    
    <script type="text/javascript">
        function clearText(field)
        {
            if (field.defaultValue == field.value) field.value = '';
            else if (field.value == '') field.value = field.defaultValue;
        }
    	function turnTo(num){
    		if(!checkLogin()){
    			troyAlert("请您先首页登录!");
    			return;
    		}
    		if(num == 1){
            	top.$("#mainFrame").removeClass("heigher");
    			top.$("#personalPage").click();
    		} else if(num == 2){
    			top.$("#personalPage").click();
            	top.$("#mainFrame").addClass("heigher");
                top.window.mainFrame.location =  "/homeWithChartSelf";
    		} else if(num == 3){
    			top.$('#notFirst').hide();
    			top.$('#first').show();
            	top.$("#mainFrame").removeClass("heigher");
            	top.$("#mainFrame").addClass("normal");
    			top.window.firstFrame.location = "/biz/member/modify";
    		} else if(num == 4){
    			top.$("#personalPage").click();
            	top.$("#mainFrame").removeClass("heigher");
                top.window.mainFrame.location =  "/biz/member/attention";
    		} else if(num == 5){
    			top.$("#personalPage").click();
            	top.$("#mainFrame").removeClass("heigher");
                top.window.mainFrame.location =  "/biz/member/publish";
    		} else if(num == 6){
    			top.$("#personalPage").click();
            	top.$("#mainFrame").removeClass("heigher");
                top.window.mainFrame.location = "/biz/member/detail";
    		}
    	}
    	
    	function toNew(index,desc){
    			top.$("#newMember").click();
            	top.$("#mainFrame").addClass("heigher");
                top.window.mainFrame.location =  "/newMember?index="+index+"&desc="+encodeURIComponent(desc);
    	}
    	
    	function checkLogin(){
    		var flag = false;
    		$.ajax({
			        type: "POST",
			        url: "/biz/member/checkSession",
			        data: "",
			        async:false,
			        success: function (msg) {
            			var obj = jQuery.parseJSON(msg);
            			if(obj.resultCode == "200"){
            				if(obj.data == true){
            					flag = true;
            				}
            			} else {
            				troyAlert(obj.resultMsg);
            			}
			        }
			    });
			    return flag;
    	}
    	
    	$(function(){
    		$("#logout").click(function(){
                $.ajax({
			        type: "POST",
			        url: "/biz/member/logout",
			        data: "",
			        success: function (msg) {
            			var obj = jQuery.parseJSON(msg);
            			if(obj.resultCode == "200"){
	            			top.window.location.reload();
            			} else {
            				troyAlert(obj.resultMsg);
            			}
			        }
			    });
            });
            
            loadCollect('collectMember');
    	});
    	
    	function loadCollect(showDivId){
    		$('#'+showDivId).html('')
    		var dataInfo =
		    {
		        divId: showDivId,
		        pageSize: 5,
		        url: "/biz/memberCollect/list",
		        data: [
		            {
		            	type:"method",
		            	method:"name",
		                width:"50%"
		            },
		            {
		            	type:"method",
		            	method:"operator",
		                width:"50%"
		            }
		        ]
		    };
    		loadData(dataInfo);
    	}
    	
    	function name(rowData,index) {
    		return "<a href=\"javascript:void(0)\" style=\"color:blue;text-decoration:underline\" title=\""+rowData.name+"\" onclick=\"toMember("+rowData.memberNo+")\">"+rowData.name+"</a>";
    	}
    	
    	function operator(rowData,index){
    		return "<a href=\"javascript:void(0)\" style=\"color:red;font-size:14px;\" title=\""+rowData.name+"\" onclick=\"cancel("+rowData.id+")\">取消收藏</a>";
    	}
    	
    	function cancel(id) {
    		troyConfirm('确定要取消收藏该大师吗?',function(){
	                $.ajax({
				        type: "POST",
				        url: "/biz/memberCollect/delete?id="+id,
				        data: "",
				        success: function (msg) {
	            			var obj = jQuery.parseJSON(msg);
	            			if(obj.resultCode == "200"){
            					loadCollect('collectMember')
	            			} else {
	            				troyAlert(obj.resultMsg);
	            			}
				        }
				    });
            	});
    	}
    	
    	function toMember(no){
    			top.$("#lookformember").click();
            	top.$("#mainFrame").addClass("heigher");
                top.window.mainFrame.location =  "/homeWithChartSelf?memberNo="+no;
    	}
    	
    </script>
    <style>
    	.contentD{
    		height:170px;
    	}
    	
    	.pageDiv{
		    font-size: 12px;
		    margin-top:0px;
		    float:right;
		    margin-right:20px;
		}
		
		.divContent ul li {
			margin-left:0px;
			height:20px;
		}
		
		#ulDiv {
			height:120px;
			margin-left:20px;
		}
		
		.quickUl li {
			list-style-type:none;
			margin-left:-40px;
		}
		
		.quickUl li  span{
			margin-left:20px;
			position:absolute;
			margin-top:4px;		
		}
		
		.img {
			margin-top:2px;
		}
		
		.guideWord{
			padding-left:10px;
			margin-top:10px;
			position:absolute;
			margin-top:7px;
		}
		
		.qq {
			position: absolute;
			left: 70px;
			bottom: 90px;
			font-size: 14px;
			color:white;
		}
		
		.telephone {
			position: absolute;
			left: 75px;
			bottom: 25px;
			font-size: 14px;
			color:white;
		}
		
		#operatorGuide li {
			cursor:pointer;
		}
		
    </style>
</head>
<body>
<div id="sidebar" class="left">
    <div class="sidebar_box" style="margin-top:-12px" id="quickOperator">
    	<div class="sidebar_box" id="showInfo" >
                <h3 class="quick"><a type="button" class="" id="logout" style="float:right;cursor:pointer;font-size:10px;color:#f27f02">注销</a></h3>
                <ul class="quickUl">
                	<li onclick="turnTo(1)"><img src="${contextPath}/webresources/images/icon (1).png" class="img"/><span>大师工作室</span></li>
                	<li onclick="turnTo(2)"><img src="${contextPath}/webresources/images/icon (2).png" class="img"/><span>实盘交易展示</span></li>
                	<li onclick="turnTo(3)"><img src="${contextPath}/webresources/images/icon (3).png" class="img"/><span>个人信息编辑</span></li>
                	<li onclick="turnTo(4)"><img src="${contextPath}/webresources/images/icon (4).png" class="img"/><span>实盘信息查看</span></li>
                	<li onclick="turnTo(5)"><img src="${contextPath}/webresources/images/icon (5).png" class="img"/><span>实盘信息发布</span></li>
                	<li onclick="turnTo(6)"><img src="${contextPath}/webresources/images/icon (6).png" class="img"/><span>金币使用明细</span></li>
                </ul>
        </div>
        <div class="content">
            <p class="titleContent titlePic" style="text-align:left"><span class="guideWord">收藏的大师</span></p>
           	<div class="contentD" id="collectMember"></div>
        </div>
        <br>
        <div class="content">
            <p class="titleContent titlePic" style="text-align:left"><span class="guideWord">操作指南</span></p>
           	<div class="contentD" id="operatorGuide">
           		<ul style="margin-top:-10px;margin-left:-10px;" >
                	<li onclick="toNew(1,'第一张图片')"><span>第一张图片</span></li>
                	<li onclick="toNew(2,'第二张图片')"><span>第二张图片</span></li>
                	<li onclick="toNew(3,'第三张图片')"><span>第三张图片</span></li>
                	<li onclick="toNew(4,'第四张图片')"><span>第四张图片</span></li>
                	<li onclick="toNew(5,'第五张图片')"><span>第五张图片</span></li>
                	<li onclick="toNew(6,'第六张图片')"><span>第六张图片</span></li>
                	<li onclick="toNew(5,'第七张图片')"><span>第七张图片</span></li>
                	<li onclick="toNew(6,'第八张图片')"><span>第八张图片</span></li>
                </ul>
           	</div>
        </div>
    </div>
    <div class="sidebar_box">
        <div class="special">
            <p  style="text-align: left;font-size: 14px;">
                如果您在使用中遇到什么问题可以通过以下方式联系我们：
            </p>
            <p>
            <div>
                <img src="${contextPath}/webresources/images/portfolio/pic_4.png" alt="slider image 3" style="width:90%;height: 50px"><div class="qq">客服QQ：${qq}</div></img>
            </div>
            <div>
                <img src="${contextPath}/webresources/images/portfolio/pic_5.png" alt="slider image 3"  style="width:90%;height: 50px"><div class="telephone">TEL：${tel}</div></img>
            </div>
            </p>
        </div>
    </div>
</div>
</body>
</html>