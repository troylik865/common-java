<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<h2 class="contentTitle">添加密钥对</h2>
<div class="pageContent">
<form method="post" action="${contextPath}/system/security/secretkey/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="97">
		<p>
			<label>第三方支付渠道:</label>
            <select name="source_id">
                <option value="">请选择</option>
                <c:forEach items="${items}" var="item">
                    <option value="${item.id}">${item.name}</option>
                </c:forEach>
            </select>
		</p>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>