<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include/var.jsp"%>
<div id="leftside">
		<div id="sidebar_s">
			<div class="collapse">
				<div class="toggleCollapse"><div></div></div>
			</div> 
		</div>
		<div id="sidebar">
			<div class="toggleCollapse"><h2>菜单</h2><div>collapse</div></div>
			<div class="accordion" fillSpace="sideBar">
					<c:forEach var="level1" items="${menuModule.children }">
						<div class="accordionHeader">
							<h2><span>Folder</span>${level1.name }</h2>
						</div>
						<div class="accordionContent">
							<ul class="tree treeFolder">
								<c:forEach var="level2" items="${level1.children }">
									<li>
										<c:if test="${level2.actions == 'BizMember'}">
											<a href="${contextPath}${level2.url}" target="navTab" rel="moduleListNav_${level2.id }">${level2.name }</a>
										</c:if>
										<c:if test="${level2.actions != 'BizMember'}">
											<a>${level2.name }</a>
										    <c:forEach var="level3" items="${level2.children }">
										         <ul>
	     											<li><a href="${contextPath}${level3.url}" target="navTab" rel="moduleListNav_${level3.id }">${level3.name }</a></li>
										         </ul>
											</c:forEach>
										</c:if>
									</li>
								</c:forEach>
							</ul>
						</div>												
					</c:forEach>
			</div>			
		</div>
	</div>
