<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<a id="refreshJbsxBox2AreaTree" rel="jbsxBox2AreaTree" target="ajax" href="${contextPath}/system/config/area/tree_list" style="display:none;"></a>
<dwz:paginationForm action="${contextPath}/system/config/area/list/${parentId}" page="${page }" onsubmit="return divSearch(this, 'jbsxBox2Area');">
	<input type="hidden" name="keywords" value="${keywords}"/>
</dwz:paginationForm>

<form method="post" action="${contextPath}/system/config/area/list/${parentId}" onsubmit="return divSearch(this, 'jbsxBox2Area');">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li>
					<label>区域名称：</label>
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
		<shiro:hasPermission name="Area:save">
			<li><a class="add" target="dialog" width="550" height="350" mask="true" href="${contextPath}/system/config/area/create/${parentId}" ><span>添加</span></a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="Area:edit">
			<li><a class="edit" target="dialog"  rel="lookup2Group" width="550" height="350" mask="true" href="${contextPath}/system/config/area/update/{slt_uid}" ><span>编辑</span></a></li>
		</shiro:hasPermission>
		<shiro:hasPermission name="Area:delete">
			<li><a class="delete" target="ajaxTodo" callback="dialogReloadRel2Area" href="${contextPath}/system/config/area/delete/{slt_uid}" title="确认要删除该区域?"><span>删除</span></a></li>
		</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" layoutH="138" width="100%" rel="jbsxBox2Area" >
		<thead>
			<tr>
				<th width="150" >名称</th>
				<th width="150" >区域编码</th>
				<th width="150" >父区域</th>
				<th >备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${areas}">
			<tr target="slt_uid" rel="${item.id}">
				<td>${item.areaName}</td>
				<td>${item.areaCode}</td>
				<td>${item.parent.areaName}</td>
				<td>${item.remark}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- 分页 -->
	<dwz:pagination page="${page }" rel="jbsxBox2Area" onchange="navTabPageBreak({numPerPage:this.value}, 'jbsxBox2Area')"/>
	
</div>
