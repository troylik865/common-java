<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${ contextPath}/biz/backstage/columnList/list" page="${ page}">
	<input type="hidden" name="name" value="${member.name}"/>
</dwz:paginationForm>

<form method="post" action="${ contextPath }/biz/backstage/columnList/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader" >
		<div class="searchBar">
			<ul class="searchContent">
				<li>
				</li>
			</ul>
			<div class="subBar">
				<ul>						
				</ul>
			</div>
		</div>
	</div>
</form>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
				<li><a class="add" target="dialog" mask="true" width="530" height="350" href="${ contextPath }/biz/backstage/columnList/create"><span>添加</span></a></li>
				<li><a class="edit" target="dialog" mask="true" width="530" height="300" href="${ contextPath }/biz/backstage/columnList/update/{slt_uid}"><span>编辑</span></a></li>
				<li><a class="delete" target="selectedTodo"  href="${ contextPath }/biz/backstage/columnList/delete" title="确认要删除?"><span>删除</span></a></li>
				<li><a class="add" target="dialog" mask="true" width="530" height="350" href="${ contextPath }/biz/backstage/attach/create/{slt_uid}?type=softCenter"><span>上传附件</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
			<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
					   	 <th width="10%">栏目名称</th>
					   	 <th>栏目类型</th>
					   	 <th>类型</th>
					   	 <th>排序</th>
						</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${ data }">
			<tr target="slt_uid" rel="${item.id}">
			 <td><input name="ids" value="${item.id}" type="checkbox"></td>
							  <td>${ item.itemName }</td>
							  <td>${ item.itemType }</td>
							  <td>${ item.type }</td>
							  <td>${ item.rank }</td>
						</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page}"/>
</div>