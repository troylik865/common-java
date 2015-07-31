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
           	addValidateWithLeft($('#form1'),260);
	    	$("#troySubmit").click(function(){
			    	if(!checkValidate($('#form1'))){
			            	return;
			        }
					var formData=$("#form1").serialize();
	                $.ajax({
				        type: "POST",
				        url: "/biz/linkMessage/create",
				        data: formData,
				        success: function (msg) {
	            			var obj = jQuery.parseJSON(msg);
	            			if(obj.resultCode == "200"){
	            				troyAlert("留言成功");
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
        <div><img src="${contextPath}/webresources/images/contractus.png" class="titlePic"/><img src="${contextPath}/webresources/images/templatemo_menu.png" class="content1"/></div>
        <div class="left contentBottomS">
            <br>
            <br>
            <div><img src="${contextPath}/webresources/images/pic5.png" /></div>
            <div style="float: right; width: 380px;margin-top: -100px;"><p class="textStyle">
			&nbsp;&nbsp;&nbsp;&nbsp;诚邀代理加盟商！如您对本网站有兴趣请直接拨打公司服务热线或留下您的联系方式，我们将会尽快联系您。另高回报寻找操盘手，居间人。详情电联或面谈。
			随时欢迎广大会员对本网站提出好的想法和建议，我们将更努力的完善对您的服务！
			<br>
			服务热线 : 400-607-5698
			</p>
			</div>
            <form class="troyFrom" id="form1">
                <table class="registTableL">
                    <tr>
                    	<td style="width:100px"><div>姓&nbsp;&nbsp;&nbsp;名</div></td>
			            <td><input type="text" value="" name="name" class="form-control large" datatype="s1-38" nullmsg="请输入您的昵称！" errormsg="昵称至少1个字符,最多38个字符！"></td>
			            <td colspan="3">
			            <div class="info"><span class="Validform_checktip">昵称至少1个字符,最多38个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
			            </td>
                    </tr>
                    <tr>
                        <td style="width:100px">
                            <div>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别</div>
                        </td>
                        <td width="28%">
                            <select class="troySelect" name="sex" datatype="*" nullmsg="请选择所在性别！" errormsg="请选择所在性别！">
                                <option value="">请选择</option>
                                <option value="1">&nbsp;&nbsp;男&nbsp;&nbsp;</option>
                                <option value="0">&nbsp;&nbsp;女&nbsp;&nbsp;</option>
                            </select>
                        </td>
                        <td colspan="3">
			            	<div class="info"><span class="Validform_checktip">您的年龄至少1个字符,最多3个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
			            </td>
                    </tr>
                    <tr>
                    	<td style="width:100px"><div>年&nbsp;&nbsp;&nbsp;龄</div></td>
			            <td><input type="text" value="" name="age" class="form-control large" datatype="n1-3" nullmsg="请输入您的年龄！" errormsg="您的年龄至少1个字符,最多3个数字！"></td>
			            <td colspan="3">
			            <div class="info"><span class="Validform_checktip">您的年龄至少1个字符,最多3个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
			            </td>
                    </tr>
                    <tr>
                    	<td style="width:100px"><div>联系电话</div></td>
			            <td><input type="text" value="" name="phoneNumber" class="form-control large" datatype="s1-38" nullmsg="请输入您的联系电话！" errormsg="请输入正确的联系电话"></td>
			            <td colspan="3">
			            <div class="info"><span class="Validform_checktip">联系电话至少1个字符,最多38个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
			            </td>
                    </tr>
                    <tr>
                    	<td style="width:100px"><div>所在地址</div></td>
			            <td><input type="text" value="" name="address" class="form-control large" datatype="s1-200" nullmsg="请输入您的所在地址！" errormsg="所在地址至少1个字符,最多200个字符！"></td>
			            <td colspan="3">
			            <div class="info"><span class="Validform_checktip">所在地址至少1个字符,最多200个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
			            </td>
                    </tr>
                    <tr>
                    	<td style="width:100px"><div>您的留言</div></td>
			            <td><textarea class="troyArea" name="message" cols="43" onfocus="clearText(this)" datatype="s1-400" nullmsg="请输入您的您的留言！" errormsg="您的留言至少1个字符,最多400个字符！">
                        </textarea></td>
			            <td colspan="3">
			            <div class="info"><span class="Validform_checktip">您的留言至少1个字符,最多400个字符</span><span class="dec"><s class="dec1">◆</s><s class="dec2">◆</s></span></div>
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