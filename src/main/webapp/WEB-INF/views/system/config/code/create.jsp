<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<h2 class="contentTitle">添加代码</h2>
<form method="post" action="${contextPath}/system/config/code/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="97">
		<dl>
			<dt>代码类别：</dt>
			<dd>
				<input type="text" name="codeType" class="required" size="32" maxlength="32" alt="请输入代码类别"/>
			</dd>
		</dl>
		<dl>
			<dt>类别名称：</dt>
			<dd>
				<input type="text" name="typeName" class="required" size="32" maxlength="255" alt="请输入类别名称"/>
			</dd>
		</dl>
		<dl>
			<dt>代码名称：</dt>
			<dd>
				<input type="text" name="codeName" class="required" size="32" maxlength="32" alt="请输入代码名称"/>
			</dd>
		</dl>
		<dl>
			<dt>代码值：</dt>
			<dd>
				<input type="text" name="codeValue" class="required" size="32" maxlength="32" alt="请输入代码值"/>
			</dd>
		</dl>
		
		<dl>
			<dt>序  号：</dt>
			<dd>
				<input type="text" name="ordNo" class="required digits" size="3" value="99" maxlength="3"/>
				<span class="info">&nbsp;&nbsp;默认:99</span>
			</dd>
		</dl>	
		<dl>
			<dt>状  态：</dt>
			<dd>
			    <dwz:syscode  type="codeState" codeType="STATE" target="dialog" style="required"></dwz:syscode>
			</dd>
		</dl>
		<dl>
			<dt>描  述：</dt>
			<dd>
				<textarea name="codeDesc" cols="32" rows="3" maxlength="255"></textarea>
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