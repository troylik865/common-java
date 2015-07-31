<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${ contextPath }/platform/backstage/platformItem/create" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
 <div class="pageFormContent" layoutH="97">
              <dl>
         <dt>商品名称：</dt>
         <dd>
             <input type="text" name="name"  size="20" maxlength="30" alt="请输入商品名称"/>
          </dd>
       </dl>
              <dl>
         <dt>商品收益：</dt>
         <dd>
             <input type="text" name="itemEarn"  size="20" maxlength="30" alt="请输入商品收益"/>
          </dd>
       </dl>
              <dl>
         <dt>商品描述：</dt>
         <dd>
             <input type="text" name="itemDesc"  size="20" maxlength="30" alt="请输入商品描述"/>
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
