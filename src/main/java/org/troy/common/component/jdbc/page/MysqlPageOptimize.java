package org.troy.common.component.jdbc.page;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.troy.common.utils.dwz.Page;


/**
 *
 * <p>Title: 分頁功能</p>
 *
 * <p>Description: Mysql的分頁sql優化器</p>
 *
 * @author wangj
 * 2013-5-30
 */

public class MysqlPageOptimize {
	/**
	 * 构造分页查询sql语句
	 * @param page
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("unused")
	public String getOptimizeSQLString(Page page, String sql) {
    	int pageNo = page.getPageNum();
        int pageItems = page.getNumPerPage();
        String sFromItem = String.valueOf((pageNo - 1) * pageItems);
        String sToItem = String.valueOf(pageNo * pageItems);
        String strSqlQuery;
    	
        //所有记录排序
        if (StringUtils.isNotBlank(page.getOrderField())) {
        	String orderBy = "";
        	if(sql.contains("order by")){
        		orderBy = " , ";
        	}else{
        		orderBy = " order by ";
        	}
	        if (page.getOrderDirection().equalsIgnoreCase(Page.ORDER_DIRECTION_ASC)) {
	        	sql =  sql + orderBy +page.getOrderField()+" "+Sort.Direction.ASC;
			} else {
				sql =  sql + orderBy +page.getOrderField()+" "+Sort.Direction.DESC;
			}
		}
        
    	strSqlQuery =  sql + " limit " + sFromItem + "," + pageItems;   
    	
    	/**此种写法，只排序当前页
    	 if (StringUtils.isNotBlank(page.getOrderField())) {
			// 忽略大小写
			if (page.getOrderDirection().equalsIgnoreCase(Page.ORDER_DIRECTION_ASC)) {
				strSqlQuery = strSqlQuery + " order by "+page.getOrderField()+" "+Sort.Direction.ASC;
			} else {
				strSqlQuery = strSqlQuery + " order by "+page.getOrderField()+" "+Sort.Direction.DESC;
			}
		} */
    	return strSqlQuery;
    }
	
    /**  
     * 构造数据总数查询 SQL  
     * @param queryString  
     * @return  
     */  
   public String getCountQuerySQL(String queryString){   
     String sql = "";   
      if (StringUtils.isNotEmpty(queryString)){   
          sql = "select count(1) from (" + queryString + ") totalCount";   
       }   
       return sql;   
    } 
}
