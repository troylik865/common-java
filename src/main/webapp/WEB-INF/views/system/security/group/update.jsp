<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<h2 class="contentTitle">修改组织</h2>
<a id="refreshJbsxBox2GroupTree" rel="jbsxBox2GroupTree" target="ajax" href="${contextPath}/system/security/group/tree_list" style="display:none;"></a>
<form method="post" action="${contextPath}/system/security/group/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadRel2Group);">
	<input type="hidden" name="id" value="${group.id }"/>
	<div class="pageFormContent" layoutH="97">
		<dl>
			<dt>名称：</dt>
			<dd>
				<input type="text" name="name" class="required" size="32" maxlength="32" value="${group.name }" alt="请输入组织名称"/>
			</dd>
		</dl>
		<dl>
			<dt>城区：</dt>
			<dd> 
			    <dwz:sysarea type="areaCode" target="dialog" areaLevel="1"   value="${group.areaCode }"
			    onchange="getSelectJson('streetCode','${contextPath}/system/config/area/getjson/','dialog',this.value,'${group.streetCode }') ;"/>
			</dd>
		</dl>
		<dl>
			<dt>街道：</dt>
			<dd>
				<dwz:select type="streetCode" target="dialog" parentValue="${group.areaCode }" defultValue="${group.streetCode }"></dwz:select>
			</dd>
		</dl>	
		<dl>
			<dt>父级：</dt>
			<dd>
			    <input type="hidden" name="parent.id" value="${group.parent.id }"/>
			    <input class="required" name="parent.name" type="text" readonly="readonly" value="${group.parent.name }"/>
			    <a class="btnLook" href="${contextPath}/system/security/group/look_group" lookupGroup="parent" title="父级机构" width="400">查找带回</a>	
			</dd>
		</dl>	
		<dl>
			<dt>描述：</dt>
			<dd>
				<textarea name="description" cols="32" rows="3" maxlength="255">${group.description }</textarea>
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