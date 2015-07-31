<%@page import="org.troy.system.entity.main.SysModule"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<%!
	public String tree(SysModule module, String basePath) {
		if (module.getChildren().isEmpty()) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("<ul>" + "\n");
		for(SysModule o : module.getChildren()) {
			buffer.append("<li><a href=\"" + basePath + "/system/security/module/list/" + o.getId() + "\" target=\"ajax\" rel=\"jbsxBox2module\">" + o.getName() + "</a>" + "\n");
			buffer.append(tree(o, basePath));
			buffer.append("</li>" + "\n");
		}
		buffer.append("</ul>" + "\n");
		return buffer.toString();
	}
%>
<%
    SysModule module2 = (SysModule)request.getAttribute("module");
%>
	    <ul class="tree treeFolder collapse">
			<li><a href="${contextPath}/system/security/module/list/${module.id}" target="ajax" rel="jbsxBox2module">${module.name }</a>
				<%=tree(module2, request.getContextPath()) %>
			</li>
	     </ul>
