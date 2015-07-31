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
    <script>
        $(document).ready(function(){
           	addValidateWithLeft($('#form1'),310);
	    	$("#troySubmit").click(function(){
	    			if(!checkValidate($('#form1'))){
			            	return;
			        }
					var formData=$("#form1").serialize();
	                $.ajax({
				        type: "POST",
				        url: "/biz/linkMessage/create?from=search",
				        data: formData,
				        success: function (msg) {
	            			var obj = jQuery.parseJSON(msg);
	            			if(obj.resultCode == "200"){
	            				troyAlert("消息发送成功，敬请期待回复！");
	            				$("#resetButton").click();
	            			} else {
	            				troyAlert(obj.resultMsg);
	            			}
				        }
				    });
	            });
	        });
    </script>
    <style>
    	.redSpan {
    		color:red;
    	}
    	
    	.titlePic{
    		width:100px;
    		height:38px;
    		position:absolute;
    		margin-top:-2px;
    	}
    </style>
</head>
<body>
<div id="templatemo_main_wrapper">
    <div id="templatemo_main">
        <div><img src="${contextPath}/webresources/images/memberSearch.png" class="titlePic"/><img src="${contextPath}/webresources/images/templatemo_menu.png" class="content1"/></div>
        <div class="left contentBottom">
            <p class="formTitle"><img src="${contextPath}/webresources/images/memberSearchTitle.png" /></p>
            <p class="textStyle">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如您有实盘资金需要寻找高手操盘或有意在我们现有高手中单独哪位大师为您操盘，我们将会安排给您面对面交流的机会！</p>
            <p class="textStyle">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请您先留下您的联系方式或拨打我公司热线4006075698.</p>

            <form class="troyFrom" id="form1">
                <table class="registTable">
                    <tr><td>带<span class="redSpan">*</span>号的为必填</td></tr>
                    <tr>
                    	<td style="width:100px"><div><span class="redSpan">*</span>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名</div></td>
			            <td style="width:180px;"><input type="text" value="" name="name" class="form-control large" datatype="s1-38" nullmsg="请输入您的姓名！" errormsg="姓名至少1个字符,最多38个字符！"></td>
			            <td>
			            <div class="info"><span class="Validform_checktip">姓名至少1个字符,最多38个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
			            </td>
                    </tr>
                    <tr>
                        <td style="width:100px">
                            <div><span class="redSpan">*</span>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别</div>
                        </td>
                        <td  style="width:180px;" width="28%">
                            <select class="troySelect" name="sex" datatype="*" nullmsg="请选择性别！" errormsg="请选择所在性别！">
                                <option value="">请选择</option>
                                <option value="1">&nbsp;&nbsp;男&nbsp;&nbsp;</option>
                                <option value="0">&nbsp;&nbsp;女&nbsp;&nbsp;</option>
                            </select>
                        </td>
                        <td>
			            	<div class="info"><span class="Validform_checktip">您的年龄至少1个字符,最多3个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
			            </td>
                    </tr>
                    <tr>
                    	<td style="width:100px"><div><span class="redSpan">*</span>年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄</div></td>
			            <td style="width:180px;"><input type="text" value="" name="age" class="form-control large" datatype="s1-3" nullmsg="请输入您的年龄！" errormsg="您的年龄至少1个字符,最多3个字符！"></td>
			            <td>
			            <div class="info"><span class="Validform_checktip">您的年龄至少1个字符,最多3个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
			            </td>
                    </tr>
                    <tr>
                    	<td style="width:100px"><div><span class="redSpan">*</span>联系电话</div></td>
			            <td  style="width:180px;"><input type="text" value="" name="phoneNumber" class="form-control large" datatype="s1-38" nullmsg="请输入您的联系电话！" errormsg="联系电话至少1个字符,最多38个字符！"></td>
			            <td>
			            <div class="info"><span class="Validform_checktip">联系电话至少1个字符,最多38个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
			            </td>
                    </tr>
                    <tr>
                    	<td style="width:100px"><div><span class="redSpan">*</span>所在地址</div></td>
			            <td style="width:180px;"><input type="text" value="" name="address" class="form-control large" datatype="s1-200" nullmsg="请输入您的所在地址！" errormsg="所在地址至少1个字符,最多200个字符！"></td>
			            <td>
			            <div class="info"><span class="Validform_checktip">所在地址至少1个字符,最多200个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
			            </td>
                    </tr>
                     <tr>
                    	<td style="width:100px"><div>邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱</div></td>
			            <td style="width:180px;"><input type="text" value="" name="email" class="form-control large" ></td>
			            <td>
			            </td>
                    </tr>
                    <tr>
                    	<td style="width:100px"><div>Q&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Q</div></td>
			            <td style="width:180px;"><input type="text" value="" name="qq" class="form-control large" ></td>
			            <td>
			           </td>
                    </tr>
                    <tr>
                        <td style="width:100px">
                            <div><span class="redSpan">*</span>投资方向</div>
                        </td>
                        <td  style="width:180px;" style="text-align: left;">
                            <input type="checkbox" name="investDirection" value="qh" id="checkbox1" checked/><label for="checkbox1">期货</label>
                            <input type="checkbox" name="investDirection" value="gp" id="checkbox2"/><label for="checkbox2">股票</label>
                            <input type="checkbox" name="investDirection" value="wh" id="checkbox3"/><label for="checkbox3">外汇</label>
                            <input type="checkbox" name="investDirection" value="hj" id="checkbox4"/><label for="checkbox4">贵金属</label>
                            <input type="checkbox" name="investDirection" value="by" id="checkbox5"/><label for="checkbox5">模拟区</label>
                        </td>
                    </tr>
                    <tr>
                    	<td style="width:100px"><div><span class="redSpan">*</span>最高交易账户</div></td>
			            <td style="width:180px;"><input type="text" value="" name="highestTransAccountValue" class="form-control large" datatype="s1-200" nullmsg="请输入您的最高交易账户！" errormsg="最高交易账户至少1个字符,最多200个字符！"></td>
			            <td>
			            <div class="info"><span class="Validform_checktip">最高交易账户至少1个字符,最多200个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
			            </td>
                    </tr>
                    <tr>
                    	<td style="width:100px"><div><span class="redSpan">*</span>意向交易账户</div></td>
			            <td style="width:180px;"><input type="text" value="" name="intentTransAccountValue" class="form-control large" datatype="s1-200" nullmsg="请输入您的意向交易账户！" errormsg="意向交易账户至少1个字符,最多200个字符！"></td>
			            <td>
			            <div class="info"><span class="Validform_checktip">意向交易账户至少1个字符,最多200个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
			            </td>
                    </tr>
                </table>
                <div class="buttonDiv"><input type="button" value="提    交" class="btn btn-block btn-lg btn-danger" id="troySubmit"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="reset" value="重    置" class="btn btn-block btn-lg btn-regist" id="resetButton"/></div>
            </form>
        </div>
    </div>
    <!-- END of main -->
</div>
<!-- END of main wrapper -->
</body>
</html>