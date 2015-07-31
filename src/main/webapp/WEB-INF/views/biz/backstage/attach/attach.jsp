<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
 <link rel="stylesheet" type="text/css" href="${contextPath}/webresources/uploadify/css/uploadify.css">

    <script type="text/javascript" src="${contextPath}/webresources/uploadify/scripts/jquery.uploadify.min.js"></script>
    <script type="text/javascript">
    $(function() {
    	 
       $('#file_upload').uploadify({
            'swf'      : '${contextPath}/webresources/uploadify/scripts/uploadify.swf',
            'uploader' : '${contextPath}/biz/attach/upload',
             auto : true,
             'fileSizeLimit' : '200MB',
             'fileTypeDesc' : 'Image Files',
             'fileTypeExts' : '*.*',
             buttonText:'请选择文件(一个或多个)',
             'onUploadSuccess' : function(file, data, response) {
            	 //每个文件上传成功后会调用，可能会两次
            	 //alert('文件[' + file.name + ']上传成功了,' + response + '返回值:' + data);
            	 $("#shownode").append("<p>已成功上传"+file.name+"文件</p>");
              },
		       'onQueueComplete' : function(queueData) {
		           //alert(queueData.uploadsSuccessful + ' 个文件上传成功！');
		    	   $("#shownode").append("<p>已成功上传"+queueData.uploadsSuccessful+"个文件</p>");
		       },
		       'onUploadStart' : 	function(file) {
		       		var desc = $('#desc').val();
					$("#file_upload").uploadify("settings", "formData", {'bizId':'${bizId}','desc':desc,'cost':'gold','type':'${type}'});
				}

        });
    });
    </script>

</head>

<body>

<input type="file" name="file_upload" id="file_upload" />
<br><br>
附件描述：<input type="text" name="desc" id="desc"/><span style="color:red">(如果该附件有描述信息，请先进行附件信息的填写！)</span>
<br><br><br><br>
<div id="shownode" ></div>
</body>
</html>