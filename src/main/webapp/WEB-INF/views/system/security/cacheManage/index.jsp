<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="CacheManage:edit">
				<li><a class="delete" target="ajaxTodo" href="${contextPath}/system/security/cacheManage/clear" title="确认要清空缓存?"><span>清空缓存</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="CacheManage:edit">
				<li><a class="delete" target="ajaxTodo" href="${contextPath}/system/security/cacheManage/update" title="确认要更新缓存?"><span>更新缓存</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="list" layoutH="0" width="100%">
		<thead>
			<tr>
				<th >清除系统所有缓存。更新系统缓存，重新加载行政区划，组织机构等数据</th>
			</tr>
		</thead>
		<tbody>

		</tbody>
	</table>	
</div>
