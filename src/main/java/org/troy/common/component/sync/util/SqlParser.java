package org.troy.common.component.sync.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.troy.common.component.jdbc.NameConverter;
import org.troy.common.component.sync.domain.DataSyncConst;
import org.troy.common.component.sync.domain.TransportSQL;

import com.google.common.collect.Maps;
/**
 * 根据List或实体类生成sql语句
 * @author 杨磊
 * @since 2014-5-22 下午5:16:01
 * Copyright © 2013 - 2014 CNNCT
 */
public class SqlParser {
	private static String primaryKey = "ID";

	/**
	 * 批量转换删除sql
	 * @param primaryKeyVals
	 * @param table
	 * @return
	 */
	public List<String> parseDeleteSQL(Object[] primaryKeyVals,String table){
		return Arrays.asList(genBatchDeleteSql(primaryKeyVals, table));
    }
	
	/**
	 * 批量转换sql(map)
	 * @param list
	 * @param dml
	 * @param table
	 * @return
	 */
	public List<String> parseMap2SQL(List<Map<String, Object>> list,String dml,String table){
		if("insert".equals(dml)){
			return genInsertSql(list,table);
		}else if("update".equals(dml)){
			return genUpdateSql(list,table);
		}else{
			return null;
		}
    }
	
	/**
	 * 转换单个sql(map)
	 * @param item
	 * @param dml
	 * @param table
	 * @return
	 */
	public String parseMap2SQL(Map<String, Object> item,String dml,String table){
		if("insert".equals(dml)){
			return genInsertSql(item,table);
		}else if("update".equals(dml)){
			return genUpdateSql(item,table);
		}else{
			return null;
		}
    }
	
	/**
	 * 批量转换sql(实体类)
	 * @param list
	 * @param dml
	 * @param table
	 * @return
	 */
	public List<String> parseEntity2SQL(List<?> list,String dml,String table){
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
    	for (Object object : list) {
    		result.add(convertObjToMap(object));
		}
		if("insert".equals(dml)){
			return genInsertSql(result,table);
		}else if("update".equals(dml)){
			return genUpdateSql(result,table);
		}else{
			return null;
		}
    }
	
	/**
	 * 转换单个sql(实体类)
	 * @param item
	 * @param dml
	 * @param table
	 * @return
	 */
	public static String parseEntity2SQL(Object item,String dml,String table){
		if("insert".equals(dml)){
			return genInsertSql(convertObjToMap(item),table);
		}else if("update".equals(dml)){
			return genUpdateSql(convertObjToMap(item),table);
		}else{
			return null;
		}
    }
	
	public static Object getPrimaryValueFromEntity(Object item,String... primaryKeys){
		HashMap<String, Object> map = convertObjToMap(item);
		String priKey = primaryKeys.length>0?primaryKeys[0]:primaryKey;
		if(map.containsKey(priKey)){
			return map.get(priKey);
		}else{
			return null;
		}
	}
	/**
	 * 实体类转换为map
	 * @param obj
	 * @return
	 */
	public static HashMap<String, Object> convertObjToMap(Object obj) {
		HashMap<String, Object> reMap = new HashMap<String, Object>();
		if (obj == null){
			return null;
		}
		Field[] fields = obj.getClass().getDeclaredFields();
		try {
			for (Field field:fields) {
				try {
					field.setAccessible(true);
					reMap.put(NameConverter.java2db(field.getName()), field.get(obj));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return reMap;
	}
	
	/**
	 * 生成insert语句
	 * @param list
	 * @param table 表名
	 * @return
	 */
	private List<String> genInsertSql(List<Map<String, Object>> list,String table){
		List<String> sqls = new ArrayList<String>();
		//得到表的字段
    	for(Map<String, Object> item:list){
    		sqls.add(genInsertSql(item,table));
    	}
    	return sqls;
	}
	
	/**
	 * 生成insert语句
	 * @param item
	 * @param table  表名
	 * @return
	 */
	public static String genInsertSql(Map<String, Object> item,String table){
		StringBuffer sql = new StringBuffer("insert into "+table);
		Stack<String> columns = new Stack<String>();
		for (String key : item.keySet()) {
			Object obj = item.get(key);
			//空值不参与insert
			if(obj==null){
				continue;
			} else{
				columns.add(key);
			}
		}
		sql.append(columns.toString().replace('[', '(').replace(']', ')'));
		sql.append("values");
		Stack<Object> values = new Stack<Object>();
		for (String column : columns) {
			if(item.containsKey(column)){
				Object obj = item.get(column);
				if(obj instanceof Integer){
					values.add(obj.toString());
				}else{
					values.add("'"+obj.toString()+"'");
				}
			}
		}
		String valuesStr =values.toString();
		sql.append(valuesStr.replace('[', '(').replace(']', ')'));
		return sql.toString();
	}
	
	/**
	 * 生成update语句
	 * @param list
	 * @param table 表名
	 * @return
	 */
	private List<String> genUpdateSql(List<Map<String, Object>> list,String table){
		List<String> sqls = new ArrayList<String>();
		//得到表的字段
    	for(Map<String, Object> item:list){
    		sqls.add(genUpdateSql(item,table));
    	}
    	return sqls;
	}
	
	/**
	 * 生成update语句
	 * @param item 
	 * @param table 表名
	 * @return
	 */
	private static String genUpdateSql(Map<String, Object> item,String table){
		StringBuffer sql = new StringBuffer("update "+table+" t set ");
		String values ="";
		String primaryValStr="";
		for (String column : item.keySet()) {
			if (item.containsKey(column)) {
				//主键不参与set
				if(primaryKey.equals(column)){
					//得到主键值
					Object primaryVal = item.get(column);
					if (primaryVal instanceof Integer) {
						primaryValStr += primaryVal.toString();
					}else{
						primaryValStr +="'"+primaryVal.toString()+"'";
					}
					continue;
				}
				//需要set的字段
				Object obj = item.get(column);
				if(obj!=null){
					if (obj instanceof Integer) {
						values += "t."+column + "=" + obj.toString() + ",";
					}else{
						values += "t."+column + "='" + obj.toString() + "',";
					}
				}else{
					continue;
				}
			}
		}
		sql.append(StringUtils.removeEnd(values, ","));
		sql.append(" where t." + primaryKey +"="+ primaryValStr);
		return sql.toString();
	}
	/**
	 * 生成删除sql
	 * @param primaryKeyVal 主键值
	 * @param table 表名
	 * @return
	 */
	public String genDeleteSql(Object primaryKeyVal,String table){
		String sql = "delete from "+table+" where "+primaryKey+"=";
		if(primaryKeyVal instanceof Integer){
			sql += Integer.parseInt(primaryKey);
		}else{
			sql += "'"+primaryKeyVal+"'";
		}
		return sql;
	}
	
	/**
	 * 生成批量删除sql
	 * @param primaryKeyVals 主键值数组
	 * @param table 表名
	 * @return
	 */
	public String genBatchDeleteSql(Object[] primaryKeyVals, String table) {
		String sql = "delete from " + table + " where " + primaryKey + " in ";
		sql += Arrays.toString(primaryKeyVals).replace('[', '(').replace(']', ')');
		return sql;
	}
	
	public String transUpdate2Insert(TransportSQL sql){
		String[] columns = StringUtils.substringAfter(sql.getSql(), "set").replaceAll("where", ",").split(",");
		Map<String, Object> entity = Maps.newConcurrentMap();
		for (String column : columns) {
			String[] columnAndValue = column.replaceAll("t.", "").trim().split("=");
			if(columnAndValue[1].startsWith("'")){
				entity.put(columnAndValue[0], StringUtils.remove(columnAndValue[1], "'"));
			} else {
				entity.put(columnAndValue[0], new Integer(columnAndValue[1]));
			}
		}
		return genInsertSql(entity,sql.getTable());
	}
	
	//1.生成insert语句
    //2.生成update语句
    public static List<TransportSQL> genTransportSQL(List<?> list,String dml,String table,int... weight){
        List<TransportSQL> sqls = new ArrayList<TransportSQL>();
        for(Object obj:list){
        	String sql = parseEntity2SQL(obj, dml, table);
        	TransportSQL transportSQL = new TransportSQL(dml, table, sql,weight);
        	if(DataSyncConst.DML_UPDATE.equals(dml)){
        		transportSQL.setPrimaryValue(getPrimaryValueFromEntity(obj));
        	}
        	sqls.add(transportSQL);
        }
        return sqls;
    }
    
    //1.生成insert语句
    //2.生成update语句
    public List<TransportSQL> genTransportSQL4Map(List<Map<String,Object>> list,String dml,String table){
        List<TransportSQL> sqls = new ArrayList<TransportSQL>();
        for(Map<String,Object> map:list){
        	String sql = parseMap2SQL(map, dml, table);
        	TransportSQL transportSQL = new TransportSQL(dml, table, sql);
        	if(DataSyncConst.DML_UPDATE.equals(dml)){
        		transportSQL.setPrimaryValue(map.get(primaryKey));
        	}
        	sqls.add(transportSQL);
        }
        return sqls;
    }
}
