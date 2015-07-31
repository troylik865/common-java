<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:hcFanChart  id="fanContainer" categories="${categories}" name="报表" data="${data}" 
					title="某某分析">
</dwz:hcFanChart>
