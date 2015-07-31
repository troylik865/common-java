<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<h2 class="contentTitle">添加参数</h2>
<form method="post" action="${contextPath}/system/config/para/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="97">
		<dl>
			<dt>参数名称：</dt>
			<dd>
				<input type="text" name="paraName" class="required" size="32" maxlength="255" alt="请输入参数名称"/>
			</dd>
		</dl>
		<dl>
			<dt>参数描述：</dt>
			<dd>
				<input type="text" name="paraDesc" class="required" size="32" maxlength="128" alt="请输入参数描述"/>
			</dd>
		</dl>
		<dl>
			<dt>状      态：</dt>
			<dd>
			    <dwz:syscode type="paraState" codeType="STATE" target="dialog" style="required"></dwz:syscode>
			</dd>
		</dl>
		<dl>
			<dt>关 键 字：</dt>
			<dd>
				<input type="text" name="paraKey" class="required" size="32" maxlength="32" alt="请输入关键字"/>
			</dd>
		</dl>
		<dl>
			<dt>参 数 值：</dt>
			<dd>
			    <textarea name="paraValue" cols="32" rows="3" class="required"  maxlength="1024"></textarea>
			</dd>
		</dl>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>