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
	/* 字段名对应entity中的属性 */
	private String field;

	private String dbColumn;

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
		this.field = columnName;
		this.columnType = columnType;
		this.javaType = javaType;
		this.columnComment = columnComment;
	}

	public ColumnInfo( String dbColumn,String field, String columnType, String javaType, String columnComment) {
		this.field = field;
		this.dbColumn = dbColumn;
		this.columnType = columnType;
		this.javaType = javaType;
		this.columnComment = columnComment;
	}

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}
	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
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

	public String getDbColumn() {
		return dbColumn;
	}

	public void setDbColumn(String dbColumn) {
		this.dbColumn = dbColumn;
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
