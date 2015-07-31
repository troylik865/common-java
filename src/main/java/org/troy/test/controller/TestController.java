package org.troy.test.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.troy.common.component.log.LogLevel;
import org.troy.common.utils.dwz.Page;
import org.troy.test.service.TestService;

/**
 * 测试Controller
 * @author wangj
 * 2013-5-17
 */
@Controller
@RequestMapping("/test")
public class TestController {

	@Autowired
	HttpServletRequest request;
	@Autowired
	TestService testService;

	private static final String USER_LIST = "test/user_list";
	private static final String LOG_LIST = "test/log_list";
	private static final String INFO_LIST = "test/info_list";
	private static final String INFO = "test/info";
	private static final String PIE_CHART = "test/pie_chart";
	private static final String BAR_CHART = "test/bar_chart";
	private static final String PIE_HIGHCHART = "test/pie_highchart";
	private static final String CHECKBOX = "test/checkbox";
	private static final String LOOK_GROUP = "test/look_group";
	private static final String FAN_CHART = "test/fan_chart";
	private static final String MONTHLINE = "test/monthLine";
	private static final String BUILDPLAN = "test/buildPlan";
	private static final String BUILDAREA = "test/buildArea";
	
	/**
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/user_list", method={RequestMethod.GET})
	public String userList(Map<String, Object> map) {
		map.put("users", testService.findLists());
		return USER_LIST;
	}
	
	/**
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/log_list", method={RequestMethod.GET,RequestMethod.POST})
	public String logList(Page page,Map<String, Object> map) {
		map.put("logs", testService.findLogLists(page));
		map.put("page", page);
		return LOG_LIST;
	}
	
	/**
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info_list", method={RequestMethod.GET,RequestMethod.POST})
	public String infoList(Page page,Map<String, Object> map) {
		return INFO_LIST;
	}
	
	/**
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/info", method={RequestMethod.GET,RequestMethod.POST})
	public String info(Page page,Map<String, Object> map) {
		return INFO;
	}
	
	@RequestMapping(value="/ajxx", method={RequestMethod.POST})
	public @ResponseBody String ajxx() {
        return "{\"id\":1,\"name\":\"test\"}";
	}
	
	
	/**
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/checkbox", method={RequestMethod.GET,RequestMethod.POST})
	public String checkbox(Page page,Map<String, Object> map) {
		map.put("logs", testService.findLogLists(page));
		map.put("page", page);
		return CHECKBOX;
	}
	@RequestMapping(value="/look_group", method={RequestMethod.GET,RequestMethod.POST})
	public String lookGroup(Page page,Map<String, Object> map,String selectorgId) {
		Map<Long,Object> idsMap = new HashMap<Long,Object>();
		if(StringUtils.isNotBlank(selectorgId)){
			if (selectorgId.contains(",")) {
				String[] arr = selectorgId.split(",");
			    for(int i=0;i<arr.length;i++){
			    	idsMap.put(Long.parseLong(arr[i]), arr[i]);
			    }
			}else{
				idsMap.put(Long.parseLong(selectorgId), selectorgId);
			}
		}else{
		}
		map.put("logs", testService.findByLogLevel(LogLevel.LOGON, page));
		map.put("selectorgId", selectorgId);
		map.put("idsMap", idsMap);
		map.put("page", page);
		return LOOK_GROUP;
	}
	/**
	 * 饼图
	 * @return
	 */
	@RequestMapping(value="/pie_chart", method={RequestMethod.GET,RequestMethod.POST})
	public String pieChart(Map<String, Object> map) {
	    //String data = "[1149422, 551315, 172095, 166565, 53329]";
	    //String legend = "[\"%%.%% – Firefox\",\"%%.%% – Internet Explorer\",\"%%.%% – Chrome\",\"%%.%% – Safari\",\"%%.%% – Opera\"]";
	    List<Map<String,Object>> list = testService.findMapLists("select name,id from sys_group");
	  
	    List<String> dataList = new ArrayList<String>();
	    List<String> legendList = new ArrayList<String>();
	    if(list!=null && list.size()>0){
		    for(int i=0;i<list.size();i++){
		    	dataList.add(list.get(i).get("id").toString());
		    	legendList.add("\"%%.%% – "+list.get(i).get("name")+"\"");
		    }
	    }
	    
	    //System.out.println("dataList="+dataList);
	    //System.out.println("legendList="+legendList);
	  
		Map<String,Object> dataMap = new HashMap<String,Object>();  
		//dataMap.put("data", data);
		//dataMap.put("legend", legend);
		
		dataMap.put("data", dataList.toString());
		dataMap.put("legend", legendList.toString());
		
		map.put("dataMap",dataMap );
		return PIE_CHART;
	}
	
	/**
	 * 柱状图
	 * @return
	 */
	@RequestMapping(value="/bar_chart", method={RequestMethod.GET,RequestMethod.POST})
	public String barChart() {
		return BAR_CHART;
	}
	
	/*************************************highchart begin*************************************************/
	/**
	 * highchart饼图
	 * @return
	 */
	@RequestMapping(value="/pie_highchart", method={RequestMethod.GET,RequestMethod.POST})
	public String pieHighchart(Map<String, Object> map) {
		String data = "[['Firefox',45.0],['IE',26.8],['Chrome',12.8,],['Safari',8.5],['Opera',6.2],['Others',0.7]]";
		map.put("data",data);
		return PIE_HIGHCHART;
	}
	
	/**
	 * 扇形图
	 * @return
	 */
	@RequestMapping(value="/fan_chart", method={RequestMethod.GET,RequestMethod.POST})
	public String fanchart(Map<String, Object> map) {
		String categories = "['MSIE', 'Firefox', 'Chrome', 'Safari', 'Opera']";
		String y[] = new String[]{"55.11","21.63","11.94","7.15","2.14"};
		String names[]= new String[]{"MSIE versions","Firefox versions","Chrome versions","Safari versions","Opera versions"};
		List<Map<String,Object>> categoriesData = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < categories.length(); i++) {
			Map<String,Object> map4Object = new HashMap<String, Object>();
			Map<String,Object> data4Object = new HashMap<String, Object>();
			if(i ==0){
				map4Object.put(""+i, "['MSIE 6.0', 'MSIE 7.0', 'MSIE 8.0', 'MSIE 9.0']");
				data4Object.put(""+i, "[10.85, 7.35, 33.06, 2.81]");
			}else if(i == 1){
				map4Object.put(""+i, "['Firefox 2.0', 'Firefox 3.0', 'Firefox 3.5', 'Firefox 3.6', 'Firefox 4.0']");
				data4Object.put(""+i, "[0.20, 0.83, 1.58, 13.12, 5.43]");
			}else if(i == 2){
				map4Object.put(""+i, "['Chrome 5.0', 'Chrome 6.0', 'Chrome 7.0', 'Chrome 8.0', 'Chrome 9.0','Chrome 10.0', 'Chrome 11.0', 'Chrome 12.0']");
				data4Object.put(""+i, "[0.12, 0.19, 0.12, 0.36, 0.32, 9.91, 0.50, 0.22]");
			}else if(i == 3){
				map4Object.put(""+i, "['Safari 5.0', 'Safari 4.0', 'Safari Win 5.0', 'Safari 4.1', 'Safari/Maxthon','Safari 3.1', 'Safari 4.1']");
				data4Object.put(""+i, "[4.55, 1.42, 0.23, 0.21, 0.20, 0.19, 0.14]");
			}else if(i == 4){
				map4Object.put(""+i, "['Opera 9.x', 'Opera 10.x', 'Opera 11.x']");
				data4Object.put(""+i, "[ 0.12, 0.37, 1.65]");
			}
			categoriesData.add(map4Object);
			data.add(data4Object);
		}
		
		String dataStr = "[";
		for (int i = 0; i < names.length; i++) {
			dataStr +="{" +
					" y:"+y[i]+",color:colors["+i+"]," +
					" drilldown:{"+
					" name:'"+names[i]+"'," +
					" categories:"+categoriesData.get(i).get(i+"")+"," +
					" data:"+data.get(i).get(i+"")+","+
					" color:colors["+i+"]"+
		"}}";
			if( i != names.length -1)
				dataStr +=",";
			else
				dataStr +="]";
	}
		map.put("categories", categories);
		map.put("data", dataStr);
		return FAN_CHART;
  }
	

	/**
	 * 拆违进度曲线图
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/monthLine", method={RequestMethod.GET, RequestMethod.POST})
	public String monthLine(Map<String, Object> map) {
		String categories = "['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun','Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']";
		String data = "[{name: 'Tokyo',data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]}," +
				" {name: 'New York',data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]}," +
				" {name: 'Berlin',data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]}, " +
				"{name: 'London',data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]}]";
		map.put("categories",categories);
		map.put("data",data);
		
		return MONTHLINE;
	}
	
	/**
	 * 拆违进度分析图
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/buildPlan", method={RequestMethod.GET,RequestMethod.POST})
	public String buildPlan(Map<String, Object> map) {
		String categories = "['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun','Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']";
		String firstData = "[49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]";
		String secondData = "[7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]";
		map.put("firstData",firstData);
		map.put("secondData",secondData);
		map.put("categories",categories);
		return BUILDPLAN;
	}
	/**
	 * 拆违面积柱状图
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/buildArea", method={RequestMethod.GET, RequestMethod.POST})
	public String buildArea(Map<String, Object> map) {
		String categories = "['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun','Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']";
		String data = "[{name: 'Tokyo',data: [49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]}," +
				" {name: 'New York',data: [83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3]}," +
				" {name: 'Berlin',data: [48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2]}, " +
				"{name: 'London',data: [42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1]}]";
		map.put("categories",categories);
		map.put("data",data);
		
		return BUILDAREA;
	}
	
	/*************************************highchart end*************************************************/
	
}
