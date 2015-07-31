<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${ contextPath }/platform/backstage/platformMemberItemRelation/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
 <div class="pageFormContent" layoutH="97">
             <input type="hidden" name="id" value="${obj.id}"/>
              <dl>
         <dt>会员名：</dt>
         <dd>
         <input type="text" name="memberName" value="${obj.memberName}" readOnly size="20" maxlength="30" alt="请输入会员名称"/>
             <input type="hidden" name="memberId" value="${obj.memberId}"/>
          </dd>
       </dl>
              <dl>
         <dt>商品名称：</dt>
         <dd>
             <input type="text" name="itemName" value="${obj.itemName}" readOnly/>
             <input type="hidden" name="itemId" value="${obj.itemId}"/>
          </dd>
       </dl>
              <dl>
         <dt>商品数量：</dt>
         <dd>
             <input type="text" name="itemNum" value="${obj.itemNum}"  size="20" maxlength="30" alt="请输入商品数量"/>
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
m 