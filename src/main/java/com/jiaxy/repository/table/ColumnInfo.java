/**
 * author wu tao time 2011-8-10
 * editor
 * ColumnInfo.java
 * copyright legalworker 2011
 */
package com.jiaxy.repository.table;


import com.jiaxy.util.StringUtil;

/**
 * 功能概述:<br/>
 * 
 */
public class ColumnInfo {
	/* 字段名 */
	private String columnName;
	/* 字段类型  */
	private String columnType;
	/* 对应java属性类型  */
	private String javaType;
	/* 字段注释说明  */
	private String columnComment;
	
	
	public ColumnInfo() {
		super();
	}
	
	
	public ColumnInfo(String columnName, String columnType, String javaType,
			String columnComment) {
		this.columnName = columnName;
		this.columnType = columnType;
		this.javaType = javaType;
		this.columnComment = columnComment;
	}


	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	/**
	 * @return the columnType
	 */
	public String getColumnType() {
		return columnType;
	}
	/**
	 * @param columnType the columnType to set
	 */
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	/**
	 * @return the columnComment
	 */
	public String getColumnComment() {
		return columnComment;
	}
	/**
	 * @param columnComment the columnComment to set
	 */
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}


	/**
	 * @return the javaType
	 */
	public String getJavaType() {
		return javaType;
	}


	/**
	 * @param javaType the javaType to set
	 */
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
	
	/**
	 * 返回java类型简单属性类型
	 * 
	 * 如 java.util.Date 返回 Date
	 * @return
	 */
	public String getSimpleJavaType(){
		if( !StringUtil.isEmpty(javaType) && javaType.indexOf(".") > 0 ){
			return javaType.substring(javaType.lastIndexOf(".")+1, javaType.length());
		}
		return javaType;
	}
}
