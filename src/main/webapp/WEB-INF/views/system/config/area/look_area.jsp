<%@page import="org.troy.system.entity.main.SysArea"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<%!
	public String tree(SysArea org) {
		if (org.getChildren().isEmpty()) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("<ul>" + "\n");
		for(SysArea o : org.getChildren()) {
			buffer.append("<li><a href=\"javascript:\" onclick=\"$.bringBack({id:'" + o.getId() + "', name:'" + o.getAreaName() + "'})\">" + o.getAreaName() + "</a>" + "\n");
			buffer.append(tree(o));
			buffer.append("</li>" + "\n");
		}
		buffer.append("</ul>" + "\n");
		return buffer.toString();
	}
%>
<%
   SysArea sa = (SysArea)request.getAttribute("area");
%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
		<ul class="tree expand">
			<li><a href="javascript:"><%=sa.getAreaName() %></a>
				<%=tree(sa) %>
			</li>
		</ul>
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关闭</button></div></div></li>
		</ul>
	</div>
</div>
