<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
    <div class="pageFormContent" >
		<p>
			<label>投资品种:</label>
			<input type="text" name="investTypeStr"  value="${transRecord.investType }" readonly="readonly"/>
		</p>
		<p>
			<label>导入时间:</label>
			<input type="text" name="fee"   value="${transRecord.importDate }" readonly="readonly"/>
		</p>	
		<p>
			<label>初期资金:</label>
			<input type="text" name="investType" value="${transRecord.origionValueWYuan }" readonly="readonly"/>
		</p>
		<p>
			<label>上日权益:</label>
			<input type="text" name="fee"  value="${transRecord.lastDayValueWYuan }" readonly="readonly"/>
		</p>	
		<p>
			<label>手续费:</label>
			<input type="text" name="investType"   value="${transRecord.investType }" readonly="readonly"/>
		</p>
		<p>
			<label>当日盈亏:</label>
			<input type="text" name="fee"  value="${transRecord.importDate }" readonly="readonly"/>
		</p>	
		<p>
			<label>当日入金:</label>
			<input type="text" name="investType"  value="${transRecord.origionValueWYuan }" readonly="readonly"/>
		</p>
		<p>
			<label>当日出金:</label>
			<input type="text" name="fee"  value="${transRecord.lastDayValueWYuan }" readonly="readonly"/>
		</p>
	</div>
	</div>
