<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<script>
$(function (){
	$('#refuse').click(function(){
		$('#form1').attr("action","${contextPath}/biz/backstage/bizmember/refuse?messageId=${messageId}");
		$('#form1').submit();
	});
	
	$('#pass').click(function(){
		$('#form1').attr("action","${contextPath}/biz/backstage/bizmember/pass?messageId=${messageId}");
		$('#form1').submit();
	});

});

</script>


<form method="post" action="${contextPath}/biz/backstage/bizmember/pass?messageId=${messageId}" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogReloadNavTab);" id="form1">
	<div class="pageContent">
		<table class="table" layoutH="60" width="100%">
			<thead>
				<tr>
				<th width="22"><input type="checkbox" group="values" class="checkboxCtrl"></th>
						   	 <th>参赛品种</th>
							</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${ data }">
				<tr target="slt_uid" rel="${item.value}">
				 <td><input name="values" value="${item.value}" type="checkbox"></td>
								  <td>${ item.name }</td>
							</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" id="pass">审批通过</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" id="refuse">驳回</button></div></div></li>
			</ul>
		</div>
	</div>
</form>