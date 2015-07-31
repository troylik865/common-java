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
				ajaxurl:"/biz/member/check"
			}]);
			
			$("#sendMsg").click(function(){
				$.ajax({
			        type: "POST",
			        url: "/biz/mobile/create?type=regist",
			        data: $("#form1").serialize(),
			        success: function (msg) {
            			var obj = jQuery.parseJSON(msg);
            			if(obj.resultCode == "200"){
	            			troyAlert(obj.data);
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
			
			$('#regist').click(function (){
				$.ajax({
			        type: "POST",
			        url: "/biz/member/create",
			        data: $("#form1").serialize(),
			        success: function (msg) {
            			var obj = jQuery.parseJSON(msg);
            			if(obj.resultCode == "200"){
							window.location.href = "/biz/member/three";
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
                <div class="memberRegist">
                	<img src="${contextPath}/webresources/images/member_left.png" />
					<div style="float:right;margin-top:50px;">
					<img src="${contextPath}/webresources/images/1.png" class="img"/>
					<img src="${contextPath}/webresources/images/read_normal.png" class="img"/>
					<img src="${contextPath}/webresources/images/arrow.png" class="img"/>
					<img src="${contextPath}/webresources/images/2.png" class="img"/>
					<img src="${contextPath}/webresources/images/fill in_press.png" class="img"/>
					<img src="${contextPath}/webresources/images/arrow.png" class="img"/>
					<img src="${contextPath}/webresources/images/3.png" class="img"/>
					<img src="${contextPath}/webresources/images/register_normal.png" class="img"/>
					</div>
                </div>
                <form class="troyFrom" id="form1" >
                	<input type="hidden" name="memberId" id="memberId" />
                    <table class="registTable">
                        <tr><td colspan="4">注册后的信息将不能更改，请如实编写</td></tr>
                        <tr>
                    	<td style="width:100px"><div><span class="redSpan">*</span>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</div></td>
			            <td colspan="3"><input type="text" value="" name="name" class="form-control large" datatype="s1-38" nullmsg="请输入您的姓名！" errormsg="姓名至少1个字符,最多38个字符！"></td>
			            <td>
			            <div class="info"><span class="Validform_checktip">姓名至少1个字符,最多38个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
			            </td>
                    </tr>
                    <tr>
                        <td style="width:100px">
                            <div><span class="redSpan">*</span>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别</div>
                        </td>
                        <td width="28%" colspan="3">
                            <select class="troySelect" name="sex" datatype="*" nullmsg="请选择性别！" errormsg="请选择所在性别！">
                                <option value="">请选择</option>
                                <option value="1">&nbsp;&nbsp;男&nbsp;&nbsp;</option>
                                <option value="0">&nbsp;&nbsp;女&nbsp;&nbsp;</option>
                            </select>
                        </td>
                        <td >
			            	<div class="info"><span class="Validform_checktip">您的年龄至少1个字符,最多3个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
			            </td>
                    </tr>
                    <tr>
                    	<td style="width:100px"><div><span class="redSpan">*</span>年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄</div></td>
			            <td colspan="3"><input type="text" value="" name="age" class="form-control large" datatype="n1-3" nullmsg="请输入您的年龄！" errormsg="您的年龄至少1个字符,最多3个数字！"></td>
			            <td >
			            <div class="info"><span class="Validform_checktip">您的年龄至少1个字符,最多3个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
			            </td>
                    </tr>
                    <tr>
                    	<td style="width:100px"><div><span class="redSpan">*</span>联系电话</div></td>
			            <td colspan="3"><input type="text" value="" name="phoneNumber" class="form-control large" datatype="s1-38" nullmsg="请输入您的联系电话！" errormsg="联系电话至少1个字符,最多38个字符！"></td>
			            <td >
			            <div class="info"><span class="Validform_checktip">联系电话至少1个字符,最多38个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
			            </td>
                    </tr>
                    <tr>
                    	<td style="width:100px"><div><span class="redSpan">*</span>所在地址</div></td>
			            <td colspan="3"><input type="text" value="" name="address" class="form-control large" datatype="s1-200" nullmsg="请输入您的所在地址！" errormsg="所在地址至少1个字符,最多200个字符！"></td>
			            <td>
			            <div class="info"><span class="Validform_checktip">所在地址至少1个字符,最多200个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
			            </td>
                    </tr>
                     <tr>
                    	<td style="width:100px"><div>邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱</div></td>
			            <td colspan="3"><input type="text" value="" name="email" class="form-control large" ></td>
			            <td>
			            </td>
                    </tr>
                    <tr>
                    	<td style="width:100px"><div>Q&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Q</div></td>
			            <td colspan="3"><input type="text" value="" name="qq" class="form-control large" ></td>
			            <td>
			           </td>
                    </tr>
                    <tr>
                        <td style="width:30%">
                            <div><span class="redSpan">*</span>投资方向</div>
                        </td>
                        <td  colspan="3" style="text-align: left;">
                            <input type="checkbox" name="investDirection" value="qh" id="checkbox1" checked/><label for="checkbox1">期货</label>
                            <input type="checkbox" name="investDirection" value="gp" id="checkbox2"/><label for="checkbox2">股票</label>
                            <input type="checkbox" name="investDirection" value="wh" id="checkbox3"/><label for="checkbox3">外汇</label>
                            <input type="checkbox" name="investDirection" value="hj" id="checkbox4"/><label for="checkbox4">贵金属</label>
                            <input type="checkbox" name="investDirection" value="by" id="checkbox5"/><label for="checkbox5">模拟区</label>
                        </td>
                    </tr>
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
                    <div>
                    <div class="buttonDiv"><input type="button" value="注册" class="btn btn-block btn-lg btn-danger" id="regist"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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