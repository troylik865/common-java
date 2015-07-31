<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${ contextPath }/biz/backstage/columnList/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="97">
		  <dl>
		    <dt>栏目类型：</dt>
			<dd>
				<select name="itemType">
				<option value="">--请选择--</option>
				<c:forEach var="item" items="${columnMap}">
					<option value="${item.value}">${item.name}</option>
				</c:forEach>
				</select>
			</dd>
		  </dl>
		   <dl>
		    <dt>链接类型：</dt>
			<dd>
				<select name="itemContentType">
				<option value="">--请选择--</option>
				<c:forEach var="item" items="${data}">
					<option value="${item.value}">${item.name}</option>
				</c:forEach>
				</select>
			</dd>
		  </dl>
		  <dl>
		    <dt>类目名称：</dt>
			<dd>
				<input type="text" name="itemName"  size="20" maxlength="20" alt="请输入类目名称"/>
			</dd>
		  </dl>
		<dl>
		<dt>排序：</dt>
			<dd>
				<input type="text" name="rank"  size="20" maxlength="30" alt="请输入排序号"/>
			</dd>
		  </dl>
		  <dl>
		<dt>链接地址：</dt>
			<dd>
				<input type="text" name="itemContent"  size="50" maxlength="200" alt="请输入链接地址"/>
			</dd>
		  </dl>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>