<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<style>
	.showPic{
		width:100%;
		height:400px;
	}
</style>
<div class="pageContent" style="height:100%">
<h2 class="contentTitle">审核确认</h2>
<form method="post" action="${contextPath}/biz/transRecord/reviewTransRecord" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" style="height:60px;">
		<p>
		    <input type="radio" name="result" value="S" checked="checked" />通过   <input type="radio" name="result" value="F" />退回
			<label>审核意见:</label>
			<textarea rows="3" cols="20" name="comment" value="${transRecordForm.comment}"  maxlength="32"/>
			<input type="hidden" name="transRecordId" value="${transRecordForm.transRecordId}"/>
		</p>
	</div>
			
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
<div>
	<c:forEach var="item" items="${attachs}">
		<img src="/biz/attach/download?fileId=${item.id}" class="showPic"/>
	</c:forEach>
</div>
</div>