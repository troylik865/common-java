<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<dwz:paginationForm action="${ contextPath}/platform/backstage/platformItem/list" page="${ page}">
 <input type="hidden" name="name" value="${platformItem.id}"/>
</dwz:paginationForm>
<form method="post" action="${ contextPath }/platform/backstage/platformItem/list" onsubmit="return navTabSearch(this)">
 <div class="pageHeader">
     <div class="searchBar">
         <ul class="searchContent">
             <!--<li>
                 <label>名称：</label>
                 <input type="text" name="name" value="${ platformItem.id}"/>
             </li>-->
         </ul>
         <div class="subBar">
             <ul>
                 <!--<li><div class="button"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>-->
             </ul>
         </div>
     </div>
 </div>
</form>
<div class="pageContent">
 <div class="panelBar">
     <ul class="toolBar">
         <li><a class="add" target="dialog" mask="true" width="530" height="600" href="${ contextPath }/platform/backstage/platformItem/create"><span>添加</span></a></li>
         <li><a class="edit" target="dialog" mask="true" width="530" height="400" href="${ contextPath }/platform/backstage/platformItem/update/{slt_uid}"><span>编辑</span></a></li>
         <li><a class="delete" target="selectedTodo"  href="${ contextPath }/platform/backstage/platformItem/delete" title="确认要删除?"><span>删除</span></a></li>
     </ul>
     </div>
 <table class="table" layoutH="137" width="100%">
     <thead>
         <tr>
         <th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
                      <th>商品名称</th>
                      <th>商品收益</th>
                      <th>商品描述</th>
                     </tr>
     </thead>
     <tbody>
         <c:forEach var="item" items="${ data }">
         <tr target="slt_uid" rel="${item.id}">
          <td><input name="ids" value="${item.id}" type="checkbox"></td>
                           <td>${ item.name }</td>
                           <td>${ item.itemEarn }</td>
                           <td>${ item.itemDesc }</td>
                     </tr>
         </c:forEach>
     </tbody>
 </table>
 <dwz:pagination page="${page}"/>
</div>
