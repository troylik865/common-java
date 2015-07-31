<%@page import="org.troy.system.util.SysConst"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<link rel="stylesheet" href="${contextPath}/webresources/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css" media="screen"/>
<!-- ztree -->
	<script type="text/javascript" src="${contextPath}/webresources/ztree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${contextPath}/webresources/ztree/js/jquery.ztree.excheck-3.5.js"></script>
	<SCRIPT type="text/javascript">
		<!--
		var setting = {
			view: {
				selectedMulti: false
			},
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onCheck: onCheck
			}
		};

		var zNodes =[
		         	<%=request.getAttribute(SysConst.TREE_JSON)%>
		];

		var clearFlag = false;
		function onCheck(e, treeId, treeNode) {
			count();
			if (clearFlag) {
				clearCheckedOldNodes();
			}
		}
		function clearCheckedOldNodes() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			nodes = zTree.getChangeCheckedNodes();
			for (var i=0, l=nodes.length; i<l; i++) {
				nodes[i].checkedOld = nodes[i].checked;
			}
		}
		function count() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var checkCount = zTree.getCheckedNodes(true).length;
			var checkNodes = zTree.getCheckedNodes(true);
			var ids="";
			if (checkNodes && checkNodes.length>0) {
				for(var i=0;i<checkNodes.length;i++){
					var checkNode = checkNodes[i];
					ids += checkNode.id+",";
				}
			}
			$("#moduleIds").val(ids.substring(0,ids.length-1));

		}
		function createTree() {
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			count();
		}

		$(document).ready(function(){
			createTree();			
		});
		//-->
</SCRIPT>
<div class="pageContent">
<h2 class="contentTitle">修改角色</h2>
<form method="post" action="${contextPath}/system/security/role/update" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);">
	<input type="hidden" name="id" value="${role.id }">
	<input type="hidden" name="moduleIds" id="moduleIds" value="" />
	<div class="pageFormContent" layoutH="100">
		<dl>
			<dt>名称：</dt>
			<dd>
				<input type="text" name="name" class="required" size="32" maxlength="32" alt="请输入角色名称" value="${role.name }"/>
			</dd>
		</dl>
		<dl>
			<dt>描述：</dt>
			<dd>
				<input type="text" name="description"  size="32" maxlength="255" alt="请输入角色描述" value="${role.description }"/>
			</dd>
		</dl>
		
		<div class="divider"></div>
		 <ul id="treeDemo" class="ztree"></ul>
	</div>	
			
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>