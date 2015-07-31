<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>UploadiFive Test</title>
<script src="${contextPath}/webresources/js/jquery-1.8.0.min.js" type="text/javascript"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<script src="${contextPath}/webresources/uploadify/scripts/jquery.uploadify.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${contextPath}/webresources/uploadify/css/uploadify.css">
<style type="text/css">
body {
	font: 13px Arial, Helvetica, Sans-serif;
}
</style>
</head>

<body>

<form>
	<div id="fileQueue"></div>
		<h4>提货券类型</h4>
				<input type="radio" id="typeCode" name="typeCode" value="108" checked="">108</input>
				<input type="radio" id="typeCode" name="typeCode" value="138">138</input>
		</p>		
        <input type="file" name="myfiles1" id="file_upload" />
        <p>
        <a href="javascript:$('#file_upload').uploadify('upload','*')">开始上传</a>
        <script>
        	$(function() {
	        	var typeCode =""; 
	        	
		    	$("#file_upload").uploadify({
		    		'buttonText'    :	'选择文件',
		    		'multi'    		: 	true,
		            'swf'      		: 	'${contextPath}/webresources/uploadify/scripts/uploadify.swf',
		            'uploader' 		: 	'/upload',
		            'auto' 			: 	false,
		            'queueSizeLimit' :'5',
		            'formData'     : {
					'timestamp' : new Date()+''
					},
		            'onUploadStart' : 	function(file) {
			        	 
			        	//校验   
		                
	                    $(":input[name='typeCode']").each(function(){	                    
	                     		if ( $(this).attr("checked"))
	                     			{
	                     				typeCode = ($(this).val());
	                     			}
	                     });
			        	$("#file_upload").uploadify("settings", "formData", {'typeCode':typeCode});
			        }
		    	});
			});	
        </script>
        
        </p>
        <!-- <a href="javascript:jQuery('#uploadify').uploadifyClearQueue()">取消所有上传</a> -->
        </p>
</form> 	
</body>
</body>
</html>