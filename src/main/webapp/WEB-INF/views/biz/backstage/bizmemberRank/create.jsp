<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${ contextPath }/biz/backstage/bizmemberRank/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<div class="pageFormContent" layoutH="97">
		  <dl>
		    <dt>投资方向：</dt>
			<dd>
				<select name="investDirection">
				<option value="">--请选择--</option>
				<c:forEach var="item" items="${data}">
					<option value="${item.value}">${item.name}</option>
				</c:forEach>
				</select>
			</dd>
		  </dl>
		  <dl>
		    <dt>排名：</dt>
			<dd>
				<input type="text" name="rank"  size="20" maxlength="20" alt="请输入排名"/>
			</dd>
		  </dl>
		<dl>
		<dt>会员编号：</dt>
			<dd>
				<input type="text" name="memberNo"  size="20" maxlength="30" alt="请输入会员编号"/>
			</dd>
		  </dl>
		  <dl>
		<dt>描述：</dt>
			<dd>
				<textarea name="desc"  alt="请输入评价信息"/>
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