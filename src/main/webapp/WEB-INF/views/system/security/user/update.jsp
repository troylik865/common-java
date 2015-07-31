<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<h2 class="contentTitle">修改用户</h2>
<form method="post" action="${contextPath}/system/security/user/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${user.id}"/>
	<div class="pageFormContent" layoutH="97">
		<p>
			<label>登录名称:</label>
			<input type="text" name="username" class="required" size="20" maxlength="32" readonly="readonly" value="${user.username }"/>
		</p>
		<p>
			<label>真实名字:</label>
			<input type="text" name="realname" class="required" size="20" maxlength="32" readonly="readonly" value="${user.realname }"/>
		</p>		
		<p>
			<label>电话:</label>
			<input type="text" name="phone" class="phone" size="20" maxlength="32" value="${user.phone }"/>
		</p>
		<p>
			<label>用户邮箱:</label>
			<input type="text" name="email" class="email" size="20" maxlength="128" value="${user.email }"/>
		</p>		
		<p>
			<label>用户状态:</label>
			<dwz:syscode type="status" codeType="STATUS" target="dialog" value="${user.status}" style="required"></dwz:syscode>
		</p>
		<p>
			<label>关联组织:</label>
			<input name="group.id" value="${user.group.id }" type="hidden"/>
			<input class="required" name="group.name" type="text" readonly="readonly" value="${user.group.name }"/>
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