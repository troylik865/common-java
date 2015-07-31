<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="/WEB-INF/views/biz/validate.jsp" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>附件下载</title>
    <meta name="keywords" content="free template,  ecommerce, online shopping, store"/>
    <meta name="description"
          content="Floral Shop is free website template for ecommerce or online shopping websites. Products, Shopping Cart, FAQs and Checkout pages are included."/>
    <link href="${contextPath}/webresources/templatemo_style.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${contextPath}/webresources/css/ddsmoothmenu.css"/>
    <link rel="stylesheet" type="text/css" href="${contextPath}/webresources/css/troy.css"/>
	<script>
		$(function(){
			$('#download').click(function (){
				var id = '${id}';
				if(null == id || '' == id){
					return;
				} 
				window.location.href = "/biz/attach/download?fileId=${attachId}";
			});
		});
	</script>

</head>
<body>    
	<div style="margin-top:50px;margin-left:50px;">
	附件名称：${name}
	<br>
	附件描述：${desc}
	<br>
	<input style="margin-left:100px;margin-top:30px;" type="button" id="download" value="下载" class="btn btn-lg btn-danger search">
	</div>
</body>
</html>