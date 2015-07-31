<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@include file="/WEB-INF/views/biz/validate.jsp" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Home</title>
    <meta name="keywords" content="free template,  ecommerce, online shopping, store"/>
    <meta name="description"
          content="Floral Shop is free website template for ecommerce or online shopping websites. Products, Shopping Cart, FAQs and Checkout pages are included."/>
    <link href="${contextPath}/webresources/templatemo_style.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/webresources/css/troy.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${contextPath}/webresources/css/ddsmoothmenu.css"/>
    <style>
        .memberRegist{
            margin-top:30px;
            margin-left:30px;
            border-bottom: 1px #A7A4A4 solid;
        }
        .memberRegist p {
            font-size:30px;
            font-weight: bold;
            color:red;
            width:200px;
        }
        .secondTitle{
            margin-left:30px;
            margin-top:30px;
            margin-bottom: 20px;
        }
        .memberContentDiv{
            margin-left:30px;
            height:500px;
            border-radius: 6px;
        }
        .memberRegist .spanClass{
            float:right;
            margin-right:20px;
            margin-top:-25px;
            font-size:12px;
        }
        .memberRegist .spanClass1 {
            font-weight: bold;
            font-size:14px;
            color:red;
        }
    	.redSpan {
    		color:red;
    	}
    </style>
    <script>
        $(document).ready(function(){
           	addValidateResize($('#form1'),30);
           	$(window).resize(function(){
     			var width = $(this).width();
     			$(".info").each(function(){
     				var windowLength = $(window).width();
     				var left;
     				if(windowLength > 985){
						left=windowLength/2-30;
     				} else {
     					left = 470;
     				}
     				$(this).css({
						left:left,
					})
     			});
			});
			
			$('#form1').Validform().addRule([{
				ele:"#username",
				ajaxurl:"/biz/member/check?type=check"
			}]);
			
			$('#commit').click(function (){
				$.ajax({
			        type: "POST",
			        url: "/biz/member/reSec",
			        data: $("#form1").serialize(),
			        success: function (msg) {
            			var obj = jQuery.parseJSON(msg);
            			if(obj.resultCode == "200"){
	            			window.location.href = "/forgetSecSuc";
            			} else {
            				troyAlert(obj.resultMsg);
            			}
			        }
			    });
			});
			
			$("#sendMsg").click(function(){
				$.ajax({
			        type: "POST",
			        url: "/biz/mobile/create?type=resec",
			        data: $("#form1").serialize(),
			        success: function (msg) {
            			var obj = jQuery.parseJSON(msg);
            			if(obj.resultCode == "200"){
	            			troyAlert(obj.resultMsg);
	            			$('#sendMsg').removeClass("btn-danger");
	            			  var count = 60;
	            			  $("#sendMsg").attr("disabled", true);
	            			  var interValObj = window.setInterval(function (){
	            			  	count--;
	            			  	if(count == 0){
	            			  		$("#sendMsg").attr("disabled", false);
	            			  		$('#sendMsg').val("立即获取");
	            					$('#sendMsg').addClass("btn-danger");
                					window.clearInterval(interValObj);//停止计时器
	            			  	} else {
	            			  		$('#sendMsg').val(count+"秒");
	            			  	}
	            			  }, 1000);
            			} else {
            				troyAlert(obj.resultMsg);
            			}
			        }
			    });				
			});
			
        });
    </script>
</head>
<body>
<div id="templatemo_main_wrapper">
    <div id="templatemo_main">
        <div class="">
            <div class="memberContentDiv">
            <div class="memberRegist"><p>密码重置</p><span class="spanClass"><span class="spanClass1">①重置密码</span> -->②密码重置成功</span></div>
                <form class="troyFrom" id="form1" action="biz/member/create" method="post">
                    <table class="registTable">
                        <tr><td colspan="4"></td></tr>
	                    <tr>
	                    	<td style="width:100px"><div><span class="redSpan">*</span>用户名</div></td>
				            <td  colspan="3"><input type="text" value="" name="userName" class="form-control large" id="username" datatype="s1-200"  nullmsg="请输入您的用户名！" errormsg="用户名至少1个字符,最多200个字符！"></td>
				            <td>
				            <div class="info"><span class="Validform_checktip">用户名至少1个字符,最多200个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
				            </td>
	                    </tr>
	                    <tr>
		                    <td style="width:100px;"><div><span class="redSpan">*</span>密码：</div></td>
		                    <td  colspan="3">
		                        <input type="password" value="" name="password" class="form-control large" plugin="passwordStrength" datatype="*6-18" nullmsg="请输入密码！" errormsg="密码至少6个字符,最多18个字符！">
		                    </td>
		                    <td>
		                        <div class="passwordStrength" style="display:none;"><b>密码强度</b> <span>弱</span><span>中</span><span class="last">强</span></div>
		                        <div class="info"><span class="Validform_checktip">密码至少6个字符,最多18个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
		                    </td>
		                </tr>
		                <tr>
		                    <td><div><span class="redSpan">*</span>确认密码</div></td>
		                    <td  colspan="3"><input type="password" value="" name="repassword" class="form-control large" recheck="password" datatype="*6-18" nullmsg="请确认密码！" errormsg="两次输入的密码不一致！"></td>
		                    <td>
		                    	<div class="info"><span class="Validform_checktip">请确认您的密码</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
		                    </td>
		                </tr>
		                <tr>
		                    <td><div><span class="redSpan">*</span>验证码</div></td>
		                    <td><input type="text" value="" name="validateCode" class="form-control large"  datatype="*1-18" nullmsg="请确认验证码！" errormsg="验证码至少1个字符,最多18个字符！"></td>
		                    <td style="padding-left:20px">
		                    	<div class="info"><span class="Validform_checktip">请确认您的验证码</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
		                    </td>
		                    <td>
		                    	<input type="button" value="立即获取" style="margin-left:6px;height:27px;line-height: 22px;" class="btn btn-block btn-lg btn-danger" id="sendMsg"/>
		                    </td>
		                </tr>
                    </table>
                    <div class="buttonDiv"><input type="button" value="确认" class="btn btn-block btn-lg btn-danger" id="commit"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="reset" value="重    置" class="btn btn-block btn-lg btn-regist"/></div>
                </form>
            </div>
        </div>
    </div>
    <!-- END of main -->
</div>
<!-- END of main wrapper -->
</body>
</html>