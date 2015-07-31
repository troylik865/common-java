<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${ contextPath}/biz/backstage/news/list" page="${ page}">
</dwz:paginationForm>

<form method="post" action="${ contextPath }/biz/backstage/news/list" onsubmit="return navTabSearch(this)">
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
				<li><a class="add" target="dialog" mask="true" width="530" height="350" href="${ contextPath }/biz/backstage/news/create"><span>添加</span></a></li>
				<li><a class="edit" target="dialog" mask="true" width="530" height="300" href="${ contextPath }/biz/backstage/news/update/{slt_uid}"><span>编辑</span></a></li>
				<li><a class="delete" target="selectedTodo"  href="${ contextPath }/biz/backstage/news/delete" title="确认要删除?"><span>删除</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
			<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
					   	 <th width="30%">新闻标题</th>
					   	 <th width="50%">新闻内容</th>
					   	 <th>序号</th>
					   	 <th>创建时间</th>
						</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${ news }">
			<tr target="slt_uid" rel="${item.id}">
			 <td><input name="ids" value="${item.id}" type="checkbox"></td>
							  <td>${ item.title }</td>
							  <td>${ item.content }</td>
							  <td>${ item.turn }</td>
							  <td><fmt:formatDate value="${item.gmtCreate}" pattern="yyyy-MM-dd"/></td>
						</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page}"/>
</div>