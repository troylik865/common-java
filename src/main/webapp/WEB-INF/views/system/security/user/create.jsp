<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<h2 class="contentTitle">添加用户</h2>
<form method="post" action="${contextPath}/system/security/user/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="97">
		<p>
			<label>登录名称:</label>
			<input type="text" name="username" class="required" size="20" maxlength="32"/>
		</p>
		<p>
			<label>真实名字:</label>
			<input type="text" name="realname" class="required" size="20" maxlength="32"/>
		</p>		
		<p>
			<label>登录密码:</label>
			<input type="text" name="plainPassword" class="required" size="20" minlength="6" maxlength="20" value="123456" alt="字母、数字、下划线 6-20位"/>
			<span class="info">&nbsp;&nbsp;默认:123456</span>
		</p>
		<p>
			<label>电话:</label>
			<input type="text" name="phone" class="phone" size="20" maxlength="32"/>
		</p>		
		<p>
			<label>用户邮箱:</label>
			<input type="text" name="email" class="email" size="20" maxlength="128"/>
		</p>		
		<p>
			<label>用户状态:</label>
			<dwz:syscode type="status" codeType="STATUS" target="dialog" value="enabled" style="required"></dwz:syscode>
		</p>
		<p>
			<label>关联组织:</label>	
			<input name="group.id" value="" type="hidden"/>
			<input class="required" name="group.name" type="text" readonly="readonly"/>
			<a class="btnLook" href="${contextPath}/system/security/group/look_group" lookupGroup="group" title="关联组织" width="400">查找带回</a>	
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