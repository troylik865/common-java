<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
	<div class="tabs">

		<div class="tabsContent">
			<div>
				<div layoutH="0" id="jbsxBox2GroupTree" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
				    <c:import url="/system/security/group/tree_list"></c:import>
				</div>
				
				<div id="jbsxBox2Group" class="unitBox" style="margin-left:246px;">
					<!--#include virtual="list1.html" -->
				</div>
	
			</div>
		</div>
	</div>
</div>