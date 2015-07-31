<%@page import="org.troy.system.entity.main.SysGroup"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<%!public String tree(SysGroup group, String basePath) {
		if (group.getChildren().isEmpty()) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("<ul>" + "\n");
		for(SysGroup o : group.getChildren()) {
			buffer.append("<li><a href=\"" + basePath + "/system/security/group/list/" + o.getId() + "\" target=\"ajax\" rel=\"jbsxBox2Group\">" + o.getName() + "</a>" + "\n");
			buffer.append(tree(o, basePath));
			buffer.append("</li>" + "\n");
		}
		buffer.append("</ul>" + "\n");
		return buffer.toString();
	}%>
<%
	SysGroup group2 = (SysGroup)request.getAttribute("group");
%>
  <ul class="tree treeFolder expand">
		<li><a href="${contextPath}/system/security/group/list/${group.id}" target="ajax" rel="jbsxBox2Group">${group.name }</a>
			<%=tree(group2, request.getContextPath()) %>
		</li>
   </ul>
