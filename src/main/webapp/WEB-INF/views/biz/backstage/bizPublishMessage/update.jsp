<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<script>
$(function (){
	$('#refuse').click(function(){
		$('#form1').attr("action","${contextPath}/biz/backstage/bizPublishMessage/update?messageId=${messageId}&status=F");
		$('#form1').submit();
	});
	
	$('#pass').click(function(){
		$('#form1').attr("action","${contextPath}/biz/backstage/bizPublishMessage/update?messageId=${messageId}&status=S");
		$('#form1').submit();
	});

});
</script>
<div class="pageContent">
<form id="form1" method="post" action="${ contextPath }/biz/backstage/bizPublishMessage/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" id="pass">审批通过</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close" id="refuse">驳回</button></div></div></li>
		</ul>
	</div>
</form>
</div>