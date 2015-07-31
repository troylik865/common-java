/**
 * 校验金额
 * @param obj
 * @return
 */
function checkMoney_6_1(obj){  
    var value = obj.value;
    var money=/^(0|[0-9]{0,6})$|^(0|[0-6]{0,6})\.([0-9]{0,1})$/;  
    var flag = money.test(value);
    if(!flag)  {
      alert("请输入正确的金额，最大支持6位整数加1位小数\n如：123456.1");
      obj.value="0.0";
      obj.focus();
      return false;
    }
    return true;
}

/**
 * 校验金额
 * @param obj
 * @return
 */
function checkMoneyfu_6_1(obj){  
    var value = obj.value;
    var money=/^(-?0|[0-9]{0,6})$|^(0|[0-6]{0,6})\.([0-9]{0,1})$/;  
    var flag = money.test(value);
    if(!flag)  {
      alert("请输入正确的金额，最大支持6位整数加1位小数\n如：123456.1");
      obj.value="0.0";
      obj.focus();
      return false;
    }
    return true;
}

/**
 * 
 * @param orgNumber
 * @param fractions
 * @return
 */
function halfFixNumber(orgNumber,fractions)
{
	if (isNaN(orgNumber)) return 'NaN';
	
	var	tempValue=parseFloat(orgNumber);
	var rtValue=tempValue.toFixed(parseInt(fractions));
	alert(rtValue);
	if (parseInt(rtValue.substr(rtValue.length-1,1))<5 && parseInt(rtValue.substr(rtValue.length-1,1))>0){
		rtValue=rtValue.substr(0,rtValue.length-1)+'5';	
	}
	if (parseInt(rtValue.substr(rtValue.length-1,1))>5 && parseInt(rtValue.substr(rtValue.length-1,1))<=9){
		rtValue=greaterNumber(rtValue,parseInt(fractions)-1)+'0';	
	}
	return rtValue;
}

function checkMoney(obj){  
    var value = obj.value;
    var money=/^(-?\d+)(\.\d+)?$/;  
    var flag = money.test(value);
    if(!flag)  {
      alert("请输入正确的金额！");
      obj.value="0.0";
      obj.focus();
      return false;
    }
    return true;
}


