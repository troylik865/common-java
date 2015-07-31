package org.troy.common.component.jdbc.page;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.troy.common.utils.dwz.Page;


/**
 *
 * <p>Title: 分頁功能</p>
 *
 * <p>Description: Oracle的分頁sql優化器</p>
 *
 * @author wangj
 * 2013-5-30
 */

public class OraclePageOptimize {
	/**
	 * 构造分页查询sql语句
	 * @param page
	 * @param sql
	 * @return
	 */
    public String getOptimizeSQLString(Page page, String sql) {
    	int pageNo = page.getPageNum();
        int pageItems = page.getNumPerPage();
        String sFromItem = String.valueOf((pageNo - 1) * pageItems + 1);
        String sToItem = String.valueOf(pageNo * pageItems + 1);
        String strSqlQuery;
    	
        //所有记录排序
        if (StringUtils.isNotBlank(page.getOrderField())) {
	        if (page.getOrderDirection().equalsIgnoreCase(Page.ORDER_DIRECTION_ASC)) {
	        	sql = "select * from ("+ sql + ") order by "+page.getOrderField()+" "+Sort.Direction.ASC;
			} else {
				sql = "select * from ("+ sql + ") order by "+page.getOrderField()+" "+Sort.Direction.DESC;
			}
		}
        
    	strSqlQuery = "SELECT * FROM (SELECT ROWNUM AS MY_ROWNUM,TABLE_A.* FROM(" + sql;
    	strSqlQuery = strSqlQuery + ") TABLE_A WHERE ROWNUM<" + sToItem;
    	strSqlQuery = strSqlQuery + ") WHERE MY_ROWNUM>=" + sFromItem;
    	
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
