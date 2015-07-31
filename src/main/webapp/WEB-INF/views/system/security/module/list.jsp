<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<a id="refreshJbsxBox2moduleTree" rel="jbsxBox2moduleTree" target="ajax" href="${contextPath}/system/security/module/tree_list" style="display:none;"></a>
<dwz:paginationForm action="${contextPath}/system/security/module/list/${parentId}" page="${page }" onsubmit="return divSearch(this, 'jbsxBox2module');">
	<input type="hidden" name="keywords" value="${keywords}"/>
</dwz:paginationForm>

<form method="post" action="${contextPath}/system/security/module/list/${parentId}" onsubmit="return divSearch(this, 'jbsxBox2module');">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>模块名称：</label>
					<input type="text" name="keywords" value="${keywords}"/>
				</li>
			</ul>
			<div class="subBar">
				<ul>						
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</form>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
		<shiro:hasPermission name="Module:save">
			<li><a class="application_add" target="dialog" width="450" height="400" mask="true" href="${contextPath}/system/security/module/create/${parentId}" ><span>添加</span></a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="Module:edit">
			<li><a class="application_edit" target="dialog" rel="lookup2Group" width="450" height="430" mask="true" href="${contextPath}/system/security/module/update/{slt_uid}" ><span>编辑</span></a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="Module:delete">
			<li><a class="application_delete" target="ajaxTodo" callback="dialogReloadRel2Module" href="${contextPath}/system/security/module/delete/{slt_uid}" title="确认要删除该模块?"><span>删除</span></a></li>
		</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" layoutH="138" width="100%" rel="jbsxBox2module" >
		<thead>
			<tr>
				<th width="150" >名称</th>
				<th width="60" orderField="priority" class="${page.orderField eq 'priority' ? page.orderDirection : ''}">优先级</th>
				<th width="150" >父模块</th>
				<th>模块地址</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${modules}">
			<tr target="slt_uid" rel="${item.id}">
				<td>${item.name}</td>
				<td>${item.priority}</td>
				<td>${item.parent.name}</td>
				<td>${item.url}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- 分页 -->
	<dwz:pagination page="${page }" rel="jbsxBox2module" onchange="navTabPageBreak({numPerPage:this.value}, 'jbsxBox2module')"/>
	
</div>


