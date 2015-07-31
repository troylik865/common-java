<%@page import="org.troy.system.entity.main.SysArea"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<%!public String tree(SysArea Area, String basePath) {
		if (Area.getChildren().isEmpty()) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("<ul>" + "\n");
		for(SysArea o : Area.getChildren()) {
			buffer.append("<li><a href=\"" + basePath + "/system/config/area/list/" + o.getId() + "\" target=\"ajax\" rel=\"jbsxBox2Area\">" + o.getAreaName() + "</a>" + "\n");
			buffer.append(tree(o, basePath));
			buffer.append("</li>" + "\n");
		}
		buffer.append("</ul>" + "\n");
		return buffer.toString();
	}%>
<%
	SysArea area2 = (SysArea)request.getAttribute("area");
%>
  <ul class="tree treeFolder expand">
		<li>
			<c:if test="${areaCode == ''}"><a href="${contextPath}/system/config/area/list/${area.id}" target="ajax" rel="jbsxBox2Area">${area.areaName }</a></c:if>
			<c:if test="${areaCode != ''}"><a>${area.areaName }</a></c:if>
			<%=tree(area2, request.getContextPath()) %>
		</li>
   </ul>
