<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page import="java.util.Date" %>
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
			initParam();        	
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
			
			$('#regist').click(function (){
				$.ajax({
			        type: "POST",
			        url: "/biz/member/update",
			        data: $("#form1").serialize(),
			        success: function (msg) {
            			var obj = jQuery.parseJSON(msg);
            			if(obj.resultCode == "200"){
							window.location.href = "/biz/member/modSuc";
            			} else {
            				troyAlert(obj.resultMsg);
            			}
            			
			        }
			    });
			});
        });
        
        function initParam(){
        	$('#name').val("${name}");
        	$('#sex').val("${sex}");
        	$('#phoneNumber').val("${phoneNumber}");
        	$('#age').val("${age}");
        	$('#address').val("${address}");
        	$('#qq').val("${qq}");
        	$('#memberId').val("${memberId}");
        	$('#email').val("${email}");
        	var invest = "${invest}";
        	var invests = invest.split(";");
        	$(":checkbox").each(function(){
        		for(var i = 0;i<invests.length;i++){
        			var inv = invests[i];
        			if(inv == $(this).val()){
        				$(this).attr("checked","checked");
        			}
        		}
        	});
        }
    </script>
</head>
<body>
<div id="templatemo_main_wrapper">
    <div id="templatemo_main">
        <div class="">
            <div class="memberContentDiv">
                <div class="memberRegist"><p>会员资料修改</p><span class="spanClass"><span class="spanClass1">①修改资料</span> --> ②修改成功</span></div>
                <form class="troyFrom" id="form1" >
                	<input type="hidden" name="id" id="memberId" />
                    <table class="registTable">
                        <tr>
                    	<td style="width:100px"><div><span class="redSpan">*</span>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</div></td>
			            <td colspan="3"><input type="text" value=""  name="name" id="name" class="form-control large" datatype="s1-38" nullmsg="请输入您的姓名！" errormsg="姓名至少1个字符,最多38个字符！"></td>
			            <td>
			            <div class="info"><span class="Validform_checktip">姓名至少1个字符,最多38个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
			            </td>
                    </tr>
                    <tr>
                        <td style="width:100px">
                            <div><span class="redSpan">*</span>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别</div>
                        </td>
                        <td width="28%" colspan="3">
                            <select class="troySelect" id="sex" name="sex" datatype="*" nullmsg="请选择性别！" errormsg="请选择所在性别！">
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
			            <td colspan="3"><input type="text" value="" id="age" name="age" class="form-control large" datatype="s1-3" nullmsg="请输入您的年龄！" errormsg="您的年龄至少1个字符,最多3个字符！"></td>
			            <td >
			            <div class="info"><span class="Validform_checktip">您的年龄至少1个字符,最多3个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
			            </td>
                    </tr>
                    <tr>
                    	<td style="width:100px"><div><span class="redSpan">*</span>联系电话</div></td>
			            <td colspan="3"><input type="text" value="" id="phoneNumber" name="phoneNumber" class="form-control large" datatype="s1-38" nullmsg="请输入您的联系电话！" errormsg="联系电话至少1个字符,最多38个字符！"></td>
			            <td >
			            <div class="info"><span class="Validform_checktip">联系电话至少1个字符,最多38个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
			            </td>
                    </tr>
                    <tr>
                    	<td style="width:100px"><div><span class="redSpan">*</span>所在地址</div></td>
			            <td colspan="3"><input type="text" value="" id="address" name="address" class="form-control large" datatype="s1-200" nullmsg="请输入您的所在地址！" errormsg="所在地址至少1个字符,最多200个字符！"></td>
			            <td>
			            <div class="info"><span class="Validform_checktip">所在地址至少1个字符,最多200个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
			            </td>
                    </tr>
                     <tr>
                    	<td style="width:100px"><div>邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱</div></td>
			            <td colspan="3"><input type="text" id="email" value="" name="email" class="form-control large" ></td>
			            <td>
			            </td>
                    </tr>
                    <tr>
                    	<td style="width:100px"><div>Q&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Q</div></td>
			            <td colspan="3"><input type="text" id="qq" value="" name="qq" class="form-control large" ></td>
			            <td>
			           </td>
                    </tr>
                    <tr>
                        <td style="width:30%">
                            <div><span class="redSpan">*</span>投资方向</div>
                        </td>
                        <td  colspan="3" style="text-align: left;width:45%">
                            <input type="checkbox" name="investDirection" value="qh" id="checkbox1" checked/><label for="checkbox1">期货</label>
                            <input type="checkbox" name="investDirection" value="gp" id="checkbox2"/><label for="checkbox2">股票</label>
                            <input type="checkbox" name="investDirection" value="wh" id="checkbox3"/><label for="checkbox3">外汇</label>
                            <input type="checkbox" name="investDirection" value="hj" id="checkbox4"/><label for="checkbox4">贵金属</label>
                            <input type="checkbox" name="investDirection" value="by" id="checkbox5"/><label for="checkbox5">模拟区</label>
                        </td>
                    </tr>
                    </table>
                    <div>
                    <div class="buttonDiv"><input type="button" value="提交" class="btn btn-block btn-lg btn-danger" id="regist"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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