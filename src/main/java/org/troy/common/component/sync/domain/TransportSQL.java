package org.troy.common.component.sync.domain;

/**
 * 报文中的sql格式
 * @author 杨磊
 * @since 2014-4-24 下午6:27:58
 * Copyright © 2013 - 2014 CNNCT
 */
public class TransportSQL {
	/**
	 * 代表dml操作类型, update和insert,delete三种
	 */
	private String dml; 
	/**
	 * 数据表名
	 */
	private String table; 
	/**
	 * 主键名称
	 */
	private String primaryKey = "id";
	/**
	 * 主键值
	 */
	private Object primaryValue;
	/**
	 * 要执行的sql语句
	 */
	private String sql; 
	/**
	 * 权重值  当有多表关联时 要保证子表先写入,将子表sql的weight设为最大 (例如有2个表有关联,那子表的sql权重值设为2,主表sql的权重值设为1,保证子表写完后才能写入主表)
	 */
	int weight = 1; 

	public TransportSQL(){}
    public TransportSQL(String dml, String table, String sql, int... weight) {
        this.dml = dml;
        this.table = table;
        this.sql = sql;
        this.weight=weight.length>0?weight[0]:1;
    }
    
    public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	public Object getPrimaryValue() {
		return primaryValue;
	}
	public void setPrimaryValue(Object primaryValue) {
		this.primaryValue = primaryValue;
	}
	public String getDml() {
        return dml;
    }

    public void setDml(String dml) {
        this.dml = dml;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
