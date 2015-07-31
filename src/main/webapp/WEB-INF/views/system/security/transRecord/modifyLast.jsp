<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">

<script>
	function investTypeEvent(obj) {
		 $.ajax({
    		        type: "POST",
    		        url: "/biz/transRecord/modifyLast1?type="+obj.value+"&id=${memberId}",
    		        data: "",
    		        success: function (msg) {
    		       			 if('' == msg || null == msg){
	    		        		return;
	    		        	}
    		        	var obj = jQuery.parseJSON(msg);
	            			if(obj.resultCode == "200"){
	            				var data = obj.data;
	            				var investTypeName = data.investType;
	            				$('#investTypeName').html(investTypeName);
	            				$('#importDate').val(data.importDate);
	            				$('#origionValueWYuan').val(data.origionValue/100);
	            				$('#lastDayValue').val(data.lastDayValue/100);
	            				$('#feeWYuan').val(data.fee/100);
	            				$('#gainsAndLossesWYuan').val(data.gainsAndLosses/100);
	            				$('#currValueWYuan').val(data.currValue/100);
	            				$('#currIncomeWYuan').val(data.currIncome/100);
	            				$('#currOutcomeWYuan').val(data.currOutcome/100);
	            				$('#currValueWYuan').val(data.currValue/100);
	            				
	            			} 
    		        }
    		    });
	}

</script>
<form method="post"  id="form1" action="${ contextPath }/biz/transRecord/updateRecordWithStatis"   class="required-validate pageForm"    onsubmit="return validateCallback(this, dialogReloadNavTab);">
           <input type="hidden" id="investType" name="investType" value="${investType}"/>
           <input type="hidden" id="id" name="id" value="${id}"/>
            <div  style="margin-left:50px;margin-top:30px;width: 85%">
            		<select id="investType" class="troySelect" name="investType" datatype="*" nullmsg="请投资品种！" errormsg="请投资品种！" onchange="investTypeEvent(this)">
                                <option value="">请选择</option>
                                <option value="qh" >&nbsp;&nbsp;期货&nbsp;&nbsp;</option>
                                <option value="gp">&nbsp;&nbsp;股票&nbsp;&nbsp;</option>
                                <option value="wh">&nbsp;&nbsp;外汇&nbsp;&nbsp;</option>
                                <option value="hj">&nbsp;&nbsp;贵金属&nbsp;&nbsp;</option>
                                <option value="by">&nbsp;&nbsp;模拟区&nbsp;&nbsp;</option>
                            </select>
                    <table class="registTableL" id="fullTable">
                        <tr>
                            <td style="width:100px">
                                <div style="width:70px">投资品种</div>
                            </td>
                            <td ><span id="investTypeName">${investTypeName}</span></td>
                            <td style="width:20%"><div>导入时间</div></td>
                            <td style="width:20%"><input type="text" id="importDate" name="importDate" class="form-control full"  value="${importDate}" readOnly/></td>
                        </tr>
                        <tr>
                            <td style="width:100px">
                                <div>初期资金(元)</div>
                            </td>
                        <td width="30%">
                            <input type="text" id="origionValueWYuan" name="origionValue" class="form-control largeLi"  value="${origionValue}"/>
                        </td>
                        <td >
                            <div style="width: 90px;">上日权益(元)</div>
                        </td>
                        <td><input id="lastDayValue" type="text" name="lastDayValue" class="form-control full"  value="${lastDayValue}"/></td>
                        </tr>
                        <tr>
                            <td style="width:100px">
                                <div>手续费(元)</div>
                            </td>
                            <td width="30%">
                                <input type="text" id="feeWYuan" name="fee" class="form-control largeLi" value="${fee}" onchange="checkMoney3(this)"/>
                            </td>
                            <td >
                                <div style="width: 90px;">当日盈亏(元)</div>
                            </td>
                            <td><input type="text" id="gainsAndLossesWYuan" name="gainsAndLosses" class="form-control full" value="${gainsAndLosses}" onchange="checkMoney3(this)"/></td>
                        </tr>
                        <tr>
                            <td style="width:100px">
                                <div>最新权益(元)</div>
                            </td>
                            <td width="30%">
                                <input type="text" id="currValueWYuan"  name="currValue" class="form-control largeLi" value="${currValue}" =""/>
                            </td>
                            <td style="width:100px">
                                <div>当日入金(元)</div>
                            </td>
                            <td width="30%">
                                <input type="text" id="currIncomeWYuan" value="${currIncome}" name="currIncome" class="form-control largeLi"  datatype="*" onchange="checkMoney1(this)"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="width:100px">
                                <div>当日出金(元)</div>
                            </td>
                            <td width="30%">
                                <input type="text" id="currOutcomeWYuan" value="${currOutcome}"   name="currOutcome" class="form-control largeLi"  datatype="*" onchange="checkMoney1(this)"/>
                            </td>
                        </tr>
                    </table>
            </div>
            <div class="buttonDiv" style="margin-top:50px;">
                <input type="submit" value="提    交" class="btn btn-lg btn-danger" />
           </div>
        </form>
</div>
