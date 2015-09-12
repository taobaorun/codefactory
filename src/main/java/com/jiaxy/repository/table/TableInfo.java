/**
 * author wu tao time 2011-8-10
 * editor
 * TableInfo.java
 * copyright legalworker 2011
 */
package com.jiaxy.repository.table;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能概述:<br/>
 * 
 * 用于描述数据表的信息
 * 
 */
public class TableInfo {

	/* 表名 */
	private String tableName;
	/* 改表要映射成的java类的类名 */
	private String clzName;
	
	private List<ColumnInfo> columnInfoes;
	
	public TableInfo() {
	}


	public TableInfo(String tableName,String clzName) {
		this.tableName = tableName;
		this.clzName = clzName;
		this.columnInfoes = new ArrayList<ColumnInfo>();
	}


	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	/**
	 * @return the columnInfoes
	 */
	public List<ColumnInfo> getColumnInfoes() {
		return columnInfoes;
	}


	/**
	 * @param columnInfoes the columnInfoes to set
	 */
	public void setColumnInfoes(List<ColumnInfo> columnInfoes) {
		this.columnInfoes = columnInfoes;
	}


	/**
	 * @return the clzName
	 */
	public String getClzName() {
		return clzName;
	}


	/**
	 * @param clzName the clzName to set
	 */
	public void setClzName(String clzName) {
		this.clzName = clzName;
	}

	
}
