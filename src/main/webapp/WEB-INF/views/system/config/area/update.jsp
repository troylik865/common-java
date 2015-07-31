<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<h2 class="contentTitle">修改区域</h2>
<a id="refreshJbsxBox2AreaTree" rel="jbsxBox2AreaTree" target="ajax" href="${contextPath}/system/config/area/tree_list" style="display:none;"></a>
<form method="post" action="${contextPath}/system/config/area/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadRel2Area);">
	<input type="hidden" name="id" value="${area.id }"/>
	<div class="pageFormContent" layoutH="97">
	     <dl>
			<dt>区域编码：</dt>
			<dd>
				<input type="text" name="areaCode" class="required" size="32" maxlength="16" value="${area.areaCode }" alt="请输入区域编码"/>
			</dd>
		</dl>	
		<dl>
			<dt>区域名称：</dt>
			<dd>
				<input type="text" name="areaName" class="required" size="32" maxlength="50" value="${area.areaName }" alt="请输入区域名称"/>
			</dd>
		</dl>
		<dl>
			<dt>区域级别：</dt>
			<dd>
			    <dwz:syscode  type="areaLevel" codeType="AREA_LEVEL" target="dialog" value="${area.areaLevel }" style="required"></dwz:syscode>
			</dd>
		</dl>	
		<dl>
			<dt>父级：</dt>
			<dd>
			    <input type="hidden" name="parent.id" value="${area.parent.id }"/>
			    <input class="required" name="parent.name" type="text" readonly="readonly" value="${area.parent.areaName }"/>
			    <a class="btnLook" href="${contextPath}/system/config/area/look_area" lookupGroup="parent" title="父级区域" width="400">查找带回</a>	
			</dd>
		</dl>	
		<dl>
			<dt>备 注：</dt>
			<dd>
				<textarea name="remark" cols="32" rows="3" maxlength="255">${area.remark }</textarea>
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