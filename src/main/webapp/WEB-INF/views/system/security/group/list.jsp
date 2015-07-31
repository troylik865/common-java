<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<a id="refreshJbsxBox2GroupTree" rel="jbsxBox2GroupTree" target="ajax" href="${contextPath}/system/security/group/tree_list" style="display:none;"></a>
<dwz:paginationForm action="${contextPath}/system/security/group/list/${parentId}" page="${page }" onsubmit="return divSearch(this, 'jbsxBox2Group');">
	<input type="hidden" name="keywords" value="${keywords}"/>
</dwz:paginationForm>

<form method="post" action="${contextPath}/system/security/group/list/${parentId}" onsubmit="return divSearch(this, 'jbsxBox2Group');">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>组织名称：</label>
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
		<shiro:hasPermission name="Group:save">
			<li><a class="group_add" target="dialog" width="550" height="350" mask="true" href="${contextPath}/system/security/group/create/${parentId}" ><span>添加</span></a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="Group:edit">
			<li><a class="group_edit" target="dialog"  rel="lookup2Group" width="550" height="350" mask="true" href="${contextPath}/system/security/group/update/{slt_uid}" ><span>编辑</span></a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="Group:delete">
			<li><a class="group_delete" target="ajaxTodo" callback="dialogReloadRel2Group" href="${contextPath}/system/security/group/delete/{slt_uid}" title="确认要删除该组织?"><span>删除</span></a></li>
		</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" layoutH="138" width="100%" rel="jbsxBox2Group" >
		<thead>
			<tr>
				<th width="150" >名称</th>
				<th width="150" >父组织</th>
				<th >描述</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${groups}">
			<tr target="slt_uid" rel="${item.id}">
				<td>${item.name}</td>
				<td>${item.parent.name}</td>
				<td>${item.description}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- 分页 -->
	<dwz:pagination page="${page }" rel="jbsxBox2Group" onchange="navTabPageBreak({numPerPage:this.value}, 'jbsxBox2Group')"/>
	
</div>
