<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/views/biz/validate.jsp" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>投资大师</title>
    <meta name="keywords" content="free template,  ecommerce, online shopping, store"/>
    <meta name="description"
          content="Floral Shop is free website template for ecommerce or online shopping websites. Products, Shopping Cart, FAQs and Checkout pages are included."/>
    <link href="${contextPath}/webresources/templatemo_style.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${contextPath}/webresources/css/ddsmoothmenu.css"/>
    <link rel="stylesheet" type="text/css" href="${contextPath}/webresources/css/troy.css"/>

    <style type="text/css">
        .validate {
            width: 65px;
            height: 26px;
            line-height: 25px;
            bottom: 39px;
            left: 160px;
            float:right;
        }

        .itemList {
            height: 200px;
        }

        .loginContent td {
            height: 40px;
        }
        .newUl{
        	margin-top:-20px;
        }

        .newUl li{
            height:27px;
            line-height:27px;
            margin-left:10px;
        }

        .newUl li a{
            color:  #A7A4A4;
        }

        .bigTable{
            width:990px;
            height: 400px;
            margin-left: auto;
            margin-right: auto;
        }
        .bigTable2{
            width:990px;
            height: 550px;;
            margin-left: auto;
            margin-right: auto;
        }
        
        #itemContent {
		    width: 490px  ;
		    float: left;
		}
		
		.newCoin{
			margin-right:10px;
		}
		
		#ulDiv{
			margin-top:-10px;
		}
		
    </style>

    <script>
        $(document).ready(function(){
        	//加载新闻列表
        	loadNews('news');
        	
            $("#regist").click(function(){
                window.location.href = "memberRegistFirst";
            });
            
            $("#forgetSec").click(function(){
                window.location.href = "forgetSec";
            });
            
            $("#validateCode").click(function(){
                $("#validateCode").attr("src","biz/validate?date="+new Date());
            });
            $("#logout").click(function(){
                $.ajax({
			        type: "POST",
			        url: "biz/member/logout",
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
            $("#modify").click(function(){
                window.location.href = "/biz/member/modify";
            });
            $("#login").click(function(){
	            	loginFunc();
			});
            addValidate($('#form1'));
			var name = "${name}";
			if(null != name && "" != name) {
				  $("#showInfo").show();
			}
			loadColumn("itemContent");
        });
        
        function loadColumn(showDivId){
        	$.ajax({
		        type: "POST",
		        url: "/biz/columnList/listAllWithC",
		        data: "",
		        success: function (msg) {
		            var obj = jQuery.parseJSON(msg);
		            var divObj = $("#"+showDivId);
		            for(var a in obj){
    					var ulObj = $("<ul></ul>");
    					divObj.append(ulObj);
    					ulObj.append("<li class=\"columnTitle\"><span style=\"color:white\">"+a+"</span></li>");
    					var eachObj = obj[a];
    					var flag = false;
    					for(var i = 0;i<5;i++){
    						var each = eachObj[i];
    						if(eachObj[i] != null){
	    						var itemContentType = $(each).attr("itemContentType");
	    						if(itemContentType == "HTML_A" ){
	    							ulObj.append("<li><a href=\""+$(each).attr("itemContent")+"\" target=\"_blank\">"+$(each).attr("itemName")+"</a></li>");
	    						} else if(itemContentType =="HTML_FILE"){
	    							ulObj.append("<li><a href=\"javascript:void(0)\" onclick=\"downloadAttach('"+$(each).attr("id")+"')\">"+$(each).attr("itemName")+"</a></li>");
 	    						}else {
	    							ulObj.append("<li>"+$(each).attr("itemName")+"</li>");
	    						}
    						}else{
    							if(!flag) flag = true;
    							ulObj.append("<li></li>");
    						}
    					}
    					if(!flag){
    						ulObj.append("<li><a style=\"color:red;font-weight:bold;font-size:13px;\" href=\"javascript:void(0)\" onclick=\"tiaozhuan()\">更多>></a></li>");
    					}else {
    						ulObj.append("<li></li>");
    					}
		            }
		        }
		    });
    	}
    	
    	function loadNews(showDivId){
    		$('#'+showDivId).html('')
    		var dataInfo =
		    {
		        divId: showDivId,
		        pageSize: 7,
		        url: "/biz/news/list",
		        hidePageDiv:"true",
		        data: [
		            {
		            	type:"method",
		            	method:"showTitle",
		                width:"90%"
		            }
		        ]
		    };
    		loadData(dataInfo);
    	}
    	
    	function showTitle(rowData,index){
    		var showTitle = rowData.title;
    		if(showTitle.length > 12) {
    			showTitle = showTitle.substring(0,12)+"...";
    		}
    		return "<img class=\"newCoin\" src=\"${contextPath}/webresources/images/icon_new.png\"/><a href=\"javascript:void(0)\" title=\""+rowData.title+"\" onclick=\"to('"+rowData.id+"')\">"+showTitle+"</a>";
    	}
    	
    	function to(id){
    		window.open("/biz/news/show/"+id,'查看新闻','width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-30)+ ',top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
    	}
    	
    	function downloadAttach(id){
    	var openUrl = "/biz/attach/show/"+id;//弹出窗口的url
		var iWidth=400; //弹出窗口的宽度;
		var iHeight=300; //弹出窗口的高度;
		var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
		var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
		 	window.open(openUrl,"","height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft); 
    	}
    	
    	function tiaozhuan(){
    		top.$("#softCenter").click();
    	}
    	
    	$(document).keydown(function(e)  
	    {  
		    if(e.which == 13){
		    	loginFunc(); 
		    	return false;
		    }
	    });
	    
	    function loginFunc(){
        		if(!checkValidate($('#form1'))){
	            	return;
	            }
				var formData=$("#form1").serialize();
                $.ajax({
			        type: "POST",
			        url: "biz/member/login",
			        data: formData,
			        success: function (msg) {
            			var obj = jQuery.parseJSON(msg);
            			if(obj.resultCode == "200"){
            				top.isLogin = 1;
	            			$("#name").html(obj.data.name);
	            			$("#lastLogin").html(obj.data.lastLoginTime);
				        	$("#loginDiv").hide();
				        	$("#showInfo").show();
	            			top.window.leftGuideFrame.location.reload();
            			} else {
            				troyAlert(obj.resultMsg);
                			$("#validateCode").attr("src","biz/validate?date="+new Date());
            			}
			        }
			        });
        }
        
        function loadRankInfo(showDivId){
    		$('#'+showDivId).html('')
    		var dataInfo =
		    {
		        divId: showDivId,
		        pageSize: 6,
		        url: "/biz/memberRank/listTop",
		        data: [
		        	{
		                type:"method",
		                method:"showNo",
		                width:"10%"
		            },
		            {
		                param: "name",
		                width:"90%"
		            }
		        ]
		    };
    		loadData(dataInfo);
    	}
    	
    	function showNo(pageInfo,index){
    		return index +". ";
    	}
    	
    	function turnTo(num){
    		if(num == 1){
    			top.$("#personalPage").click();
    		} else if(num == 2){
    			top.$("#personalPage").click();
            	top.$("#mainFrame").addClass("heigher");
                top.window.mainFrame.location =  "/homeWithChartSelf";
    		} else if(num == 3){
    			window.location.href = "/biz/member/modify";
    		} else if(num == 4){
    			top.$("#personalPage").click();
                top.window.mainFrame.location =  "/biz/member/attention";
    		} else if(num == 5){
    			top.$("#personalPage").click();
            	var obj = window.parent.$('#mainFrame');
            	obj.addClass("contentFrame2");
            	obj.removeClass("contentFrame");
                top.window.mainFrame.location =  "/biz/member/publish";
    		} else if(num == 6){
    			top.$("#personalPage").click();
                top.window.mainFrame.location = "/biz/member/detail";
    		}
    	}
    </script>
    <style>
    	.memberInfo{
    		font-size:14px;
    		text-align:center;
    		margin-top:50px;
    	}
    	
    	.divContent1 ul{
    		margin-left:10px;
    	}
    	
    	.divContent1 ul li {
    		text-align:center;
	    	position: relative;
		    display: inline;
		    float: left;
		    font-size:12px;
		    height:28px;
		    line-height:28px;
		    width:63px;
		    margin-bottom:2px;
    	}
    	
    	#news ul li {
    		height:25px;
    		line-height:25px;
    		position:relative;
    		float:inherit;
    		display:block;
    		margin-left:40px;
    	}
    </style>
    
    

</head>
<div id="templatemo_main_wrapper" style="margin-top:3px;">
    <div id="templatemo_main">
        <div id="sidebar" class="left">
        	<c:if test="${null == name}">
            <div class="sidebar_box" id="loginDiv">
                <h3>·</h3>
                <div class="loginContent">
                    <form id="form1">
                        <table>
                            <tr>
			                    <td style="width:60px;">用户名：</td>
			                    <td><input type="text" value="" name="userName" class="form-control large" datatype="s1-38" nullmsg="请输入您的昵称！" errormsg="昵称至少1个字符,最多38个字符！"></td>
			                    <td>
			                        <div class="info"><span class="Validform_checktip">昵称至少1个字符,最多38个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
			                    </td>
                            </tr>
                            <tr>
			                    <td>密&nbsp;&nbsp;码：</td>
			                    <td><input type="password" value="" name="password" class="form-control large" datatype="*1-18" nullmsg="请确认密码！"></td>
			                    <td>
			                    	<div class="info"><span class="Validform_checktip">请确认您的密码</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
			                    </td>
                            </tr>
                            <tr>
                                <td>验证码：</td>
                                <td><input type="text" name="validate" class="form-control small" datatype="*1-18" nullmsg="请确认验证码！" /> <img
                                        src="biz/validate" class="validate" id="validateCode" /></td>
                                <td>
			                    	<div class="info"><span class="Validform_checktip">请确认验证码</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
			                    </td>
                            </tr>
                        </table>
                        <input type="button" value="" class="btn btn-block btn-lg login" id="login"/>
                        <input type="button" value="注    册" class="btn btn-block btn-lg btn-regist regist" id="regist"/>
                        <a href="#" style="color: #706F6F" id="forgetSec">忘记密码</a>
                    </form>
                </div>
            </div>
            </c:if>
            <div class="sidebar_box" id="showInfo"   style="display:none">
                <h3 class="quick"><a type="button" class="" id="logout" style="float:right;cursor:pointer;font-size:10px">注销</a></h3>
                <ul class="quickUl">
                	<li onclick="turnTo(1)"><img src="${contextPath}/webresources/images/icon (1).png" class="img"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;大师工作室</li>
                	<li onclick="turnTo(2)"><img src="${contextPath}/webresources/images/icon (2).png" class="img"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实盘交易展示</li>
                	<li onclick="turnTo(3)"><img src="${contextPath}/webresources/images/icon (3).png" class="img"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;个人信息编辑</li>
                	<li onclick="turnTo(4)"><img src="${contextPath}/webresources/images/icon (4).png" class="img"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实盘信息查看</li>
                	<li onclick="turnTo(5)"><img src="${contextPath}/webresources/images/icon (5).png" class="img"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实盘信息发布</li>
                	<li onclick="turnTo(6)"><img src="${contextPath}/webresources/images/icon (6).png" class="img"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;金币使用明细</li>
                </ul>
                <!--<p class="memberInfo">会员：<span id="name" style="color:red">${name}</span> 欢迎回来</p>	
                <p class="memberInfo">上次登陆时间：<span id="lastLogin">${lastLoginTime}</span></p>	
                <div style="text-align:center"><input type="button" value="注  销" class="btn btn-block btn-lg btn-danger" id="logout"/>&nbsp;<input type="button" value="修改" class="btn btn-block btn-lg btn-danger" id="modify"/></div>-->
            </div>
        </div>

        <div id="itemContent" class="itemList divContent1" style="margin-top:12px;">
        </div>
        <div id="sidebar1" class="right">
            <div class="sidebar_box">
                <h3 class="notify"></h3>
                <div id="news">
                </div>
            </div>
        </div>
    </div>
    <!--<div class="bigTable">显示大师排行的地方</div>-->

    <div class="bigTable">
    	<iframe src="memberRank" class="bigTable2" id="memberRank"  scrolling="no"  frameborder="0"  style="margin-top:-20px;"></iframe>
	</div>
    <div class="bigTable2" id="showRankPic">
    	<iframe src="recommend1" class="bigTable2" id="recommend"  scrolling="no"  frameborder="0"></iframe>
    </div>
    <!-- END of main -->
</body>
</html>