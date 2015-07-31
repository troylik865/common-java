<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:hcColumnBasic  id="buildColumnBasicContainer" categories="${categories}" name="Rainfall (mm)" data="${data}" 
				title="Monthly Average Rainfall" subtitle="Source: WorldClimate.com">
</dwz:hcColumnBasic>

