/**
 * author wu tao time 2011-8-10
 * editor
 * MySQLColumnType.java
 * copyright legalworker 2011
 */
package com.jiaxy.repository;


import com.jiaxy.util.StringUtil;

/**
 * 功能概述:<br/>
 * 
 */
public enum MySQLColumnType {
	/**
	 *  INT	一种数值类型，值的范围如下 带符号的-2147483648~2147483647 不带符号的0~4294967295 最多十位，所以存手机号是不行的
		DECIMAL	一种数值类型，支持浮点数或者小数
		DOUBLE	一种数值类型，支持双精度浮点数
		DATE	YYYYMMDD格式的日期字段
		TIME	HH:MM:SS格式的时间字段
		DATETIME	YYMMDD HH:MM:SS格式的日期/时间类型 注意“年月日”与“时分秒”之间的空格
		YEAR	以YYYY或YY格式，范围在1901~2155之间指定的年字段 
		TIMESTAMP	以YYYYMMDDHHMMSS格式的时间戳
		CHAR	最大长度为255个字符且有固定长度的字符串类型
		VARCHAR	最大长度为255个字符但是变长的字符串类型
		TEXT	最大长度为65535个字符的字符串的类型
		BLOB	可变数据的二进制类型
		ENUM	可以从定义数值的列表上接受值的数据类型
		SET	可以从定义数值的集合上接受0个或者多个值的数据类型
	 */
	 INT("INT","Integer"),
	 TINYINT("TINYINT","Integer"),
	 BIGINT("BIGINT","Integer"),
	 DECIMAL("DECIMAL","Double"),
	 DOUBLE("DOUBLE","Double"),
	 FLOAT("FLOAT","Float"),
	 DATE("DATE","java.util.Date"),
	 TIME("TIME","java.util.Date"),
	 DATETIME("DATETIME","java.util.Date"),
	 TIMESTAMP("TIMESTAMP","java.util.Date"),
	 CHAR("CHAR","String"),
	 VARCHAR("VARCHAR","String"),
	 TEXT("TEXT","String"),
	 BLOB("BLOB","String");
	
	 private String columnType;//数据库类型
	
	 private String  javaType;//java类型
	
	
	 private MySQLColumnType(String columnType, String javaType){
		 this.columnType = columnType;
		 this.javaType = javaType;
	 }


	 /**
	  * 根据字段类型取得枚举
	  * 
	  * @param columnType
	  * @return
	  */
	 public static MySQLColumnType getMySQLColumnType(String columnType){
		 if(StringUtil.isEmpty(columnType)){
			 return null;
		 }else{
			 for(MySQLColumnType type:MySQLColumnType.values()){
				 if( type.getColumnType().equals(columnType)){
					 return type;
				 }
			 }
			 return null;
		 }
	 }
	 
	/**
	 * @return the columnType
	 */
	public String getColumnType() {
		return columnType;
	}


	/**
	 * @return the javaType
	 */
	public String getJavaType() {
		return javaType;
	}
	
	
}
