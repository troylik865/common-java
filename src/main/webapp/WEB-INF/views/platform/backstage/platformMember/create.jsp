<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${ contextPath }/platform/backstage/platformMember/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
 <div class="pageFormContent" layoutH="97">
              <dl>
         <dt>姓名：</dt>
         <dd>
             <input type="text" name="name"  size="20" maxlength="30" alt="请输入姓名"/>
          </dd>
       </dl>
         <dt>联系电话：</dt>
         <dd>
             <input type="text" name="telephone"  size="20" maxlength="30" alt="请输入联系电话"/>
          </dd>
       </dl>
              <dl>
         <dt>身份证：</dt>
         <dd>
             <input type="text" name="idCard"  size="20" maxlength="30" alt="请输入身份证"/>
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
