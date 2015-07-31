<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${ contextPath}/platform/backstage/platformMemberItemRelation/list" page="${ page}">
 <input type="hidden" name="name" value="${platformMemberItemRelation.id}"/>
</dwz:paginationForm>
<form method="post" action="${ contextPath }/platform/backstage/platformMemberItemRelation"/list" onsubmit="return navTabSearch(this)">
 <div class="pageHeader">
     <div class="searchBar">
         <ul class="searchContent">
              <!--<li>
                 <label>名称：</label>
                 <input type="text" name="name" value="${ platformMemberItemRelation.id}"/>
             </li>-->
         </ul>
         <div class="subBar">
             <ul>
                <!-- <li><div class="button"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>-->
             </ul>
         </div> 
     </div>
 </div>
</form>
<div class="pageContent">
 <div class="panelBar">
     <ul class="toolBar">
         <li><a class="add" target="dialog" mask="true" width="530" height="600" href="${ contextPath }/platform/backstage/platformMemberItemRelation/create"><span>添加</span></a></li>
         <li><a class="edit" target="dialog" mask="true" width="530" height="400" href="${ contextPath }/platform/backstage/platformMemberItemRelation/update/{slt_uid}"><span>编辑</span></a></li>
         <li><a class="delete" target="selectedTodo"  href="${ contextPath }/platform/backstage/platformMemberItemRelation/delete" title="确认要删除?"><span>删除</span></a></li>
     </ul>
     </div>
 <table class="table" layoutH="137" width="100%">
     <thead>
         <tr>
         <th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
                      <th>用户名</th>
                      <th>购买的产品</th>
                      <th>产品数量</th>
                     </tr>
     </thead>
     <tbody>
         <c:forEach var="item" items="${ data }">
         <tr target="slt_uid" rel="${item.id}">
          <td><input name="ids" value="${item.id}" type="checkbox"></td>
                           <td>${ item.memberName }</td>
                           <td>${ item.itemName }</td>
                           <td>${ item.itemNum }</td>
                     </tr>
         </c:forEach>
     </tbody>
 </table>
 <dwz:pagination page="${page}"/>
</div>
