<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<script>
</script>
<div class="pageContent">
<form id="form1" method="post" action="${ contextPath }/biz/backstage/bizPublishMessage/modify" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div style="heigth:200px;">
		<textarea id="content" cols=80 rows=8 name="content" >${content}</textarea>
		<input type="hidden" name="messageId" value="${messageId}"/>
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="submit" id="pass">修改</button></div></div></li>
		</ul>
	</div>
</form>
</div>