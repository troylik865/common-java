/**
 * 同步获取数据
 * @param type      投资方向类型
 * @param startDate 开始时间
 * @param endDate   结束时间
 * @return
 */
function getData(memberNo,investType,startDate,endDate){
	var data = [];
	$.ajax({
        type: "POST",
        async:false,
        url: "/biz/transRecord/findTransRecordValues?memberNo="+memberNo+"&investType="+investType+"&startDate="+startDate+"&endDate="+endDate,
        data: "",
        success: function (msg) {
			var obj = jQuery.parseJSON(msg);
			if(obj.resultCode == "200"){
				var dateArray=(obj.data.dates).split(",");
				var valueArray=(obj.data.values).split(",");
			    var i;
			    for (i = 0; i <dateArray.length; i++) {
			    	if((dateArray[i]!=null && dateArray[i]!='')&&(valueArray[i]!=null && valueArray[i]!='')){
			    		var dateForm=dateArray[i];
			    		dateForm = dateForm.replace(/-/g,"/");
			    		var date = new Date(dateForm);
			    		data.push({
				            x: date.getTime(),
				            y: Number(valueArray[i])
				        });
			    	}
			    }
			    if(data.length==0){
			    	data.push({
			            x: (new Date()).getTime(),
			            y: 0.0
			        });
			    }
			} else {
				alert("数据获取异常");
			}
        }
    });
    return data;
}

function getAllData(memberNo,investType,startDate,endDate){
	var data = [];
	$.ajax({
        type: "POST",
        async:false,
        url: "/biz/transRecord/findTransRecordValues?memberNo="+memberNo+"&investType="+investType+"&startDate="+startDate+"&endDate="+endDate,
        data: "",
        success: function (msg) {
			var obj = jQuery.parseJSON(msg);
			if(obj.resultCode == "200"){
				data = obj.data;
			} else {
				alert("数据获取异常");
			}
        }
    });
    return data;
}

function getTotalData(memberNo,investType,startDate,endDate){
	var data = [];
	$.ajax({
        type: "POST",
        async:false,
        url: "/biz/transRecord/findTransRecordTotal?memberNo="+memberNo+"&investType="+investType+"&startDate="+startDate+"&endDate="+endDate,
        data: "",
        success: function (msg) {
			var obj = jQuery.parseJSON(msg);
			if(obj.resultCode == "200"){
				data = obj.data;
			} else {
				alert("数据获取异常");
			}
        }
    });
    return data;
}

function getValue(obj){
	var datesArray = (obj.dates).split(",");
	return datesArray;
}

function getXValue(obj){
	var valueArray=(obj.values).split(",");
	var array = new Array();
	for(var i = 0;i<valueArray.length;i++){
		array.push(parseFloat(valueArray[i]));
	}
	return array;
}
