<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${ contextPath }/platform/backstage/platformMemberItemRelation/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
 <div class="pageFormContent" layoutH="97">
              <dl>
         <dt>会员名</dt>
         <dd>
         	<select name="memberId">
				<option value="">--请选择--</option>
				<c:forEach var="item" items="${data}">
					<option value="${item.id}">${item.name}</option>
				</c:forEach>
				</select>
          </dd>
       </dl>
              <dl>
         <dt>商品名称：</dt>
         <dd>
             <select name="itemId">
				<option value="">--请选择--</option>
				<c:forEach var="item" items="${items}">
					<option value="${item.id}">${item.name}</option>
				</c:forEach>
				</select>
          </dd>
       </dl>
              <dl>
         <dt>购买数量：</dt>
         <dd>
             <input type="text" name="itemNum"  size="20" maxlength="30" alt="请输入字段名"/>
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
