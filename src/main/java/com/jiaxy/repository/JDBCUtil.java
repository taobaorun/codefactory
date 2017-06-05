/**
 * author wu tao time 2011-8-10
 * editor
 * JDBCUtil.java
 * copyright legalworker 2011
 */
package com.jiaxy.repository;

import com.jiaxy.repository.table.ColumnInfo;
import com.jiaxy.repository.table.TableInfo;
import com.jiaxy.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;



/**
 * 功能概述:<br/>
 * 
 */
public class JDBCUtil {
	private static ThreadLocal<Connection> c = new ThreadLocal<Connection>();
	private static String url;
	private static String username;
	private static String password;
	private static Connection conn;
	
	
	static{
		try {
			InputStream in = JDBCUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
			Properties jdbcProperty = new Properties();
			jdbcProperty.load(in);
			String driver = jdbcProperty.getProperty("jdbc.driverClassName");
			url = jdbcProperty.getProperty("jdbc.url");
			username = jdbcProperty.getProperty("jdbc.username");
			password = jdbcProperty.getProperty("jdbc.password");
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public static Connection getConnection() throws SQLException{
		if( c.get() == null ){
			conn = DriverManager.getConnection(url,username,password);
			c.set(conn);
			return conn;
		}else{
			return c.get();
		}
	}
	
	
	
	/**
	 * 释放连接
	 * 
	 * @throws SQLException
	 */
	public static void release() throws SQLException{
		if( c.get() != null && !c.get().isClosed()){
			c.get().close();
			c.set(null);
		}
	}
	
	/**
	 * 释放资源
	 * 
	 * @param ps
	 * @param rs
	 * @throws SQLException
	 */
	public static void release(PreparedStatement ps,ResultSet rs) throws SQLException{
		if( ps != null ){
			ps.close();
		}
		if( rs != null ){
			rs.close();
		}
		release();
	}
	
	/**
	 * 释放资源
	 * 
	 * @param conn
	 * @param ps
	 * @param rs
	 * @throws SQLException
	 */
	public static void release(Connection conn,PreparedStatement ps,ResultSet rs) throws SQLException{
		if( ps != null ){
			ps.close();
		}
		if( rs != null ){
			rs.close();
		}
		if( conn != null ){
			conn.close();
		}
	}
	
	/**
	 * 
	 * @param tableSchema
	 * @param tableNames
	 * @return
	 * @throws SQLException
	 */
	private static PreparedStatement  getMySQLTableInfoSchema(String tableSchema,Collection<String> tableNames) throws SQLException{
		String sql = "select * FROM information_schema.TABLES where  table_schema= ? ";
		if( tableNames != null && tableNames.size() > 0 ){
			sql = "select * FROM information_schema.TABLES where  table_schema= ? and table_name in("+StringUtil.concatByDelimiter(tableNames, ",", true)+") ";
		}
		Connection conn = getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, tableSchema);
		return ps;
	}
	
	private static PreparedStatement getMySQLColumnInfoSchema(String tableSchema,String tableName) throws SQLException{
		String sql = "select * FROM information_schema.COLUMNS where  table_schema= ? and table_name = ? ";
		Connection conn = getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, tableSchema);
		ps.setString(2, tableName);
		return ps;
	}
	
	/**
	 * 判断某个字段是否存在
	 * 
	 * @param tableName
	 * @param columnName
	 * @return
	 * @throws SQLException
	 */
	public static boolean isExistColumn(String tableName,String columnName) throws SQLException{
		Connection conn = getConnection();
		DatabaseMetaData metaData = conn.getMetaData();
		ResultSet rs = metaData.getColumns(null, getTableSchema(), tableName, columnName);
		boolean isExist = false;
		while( rs.next() ){
			isExist = true;
		}
		return isExist;
	}
	
	/**
	 * 获取数据库表信息
	 * 
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public static List<TableInfo> getTableInfoes(String ... includeTableNames) throws SQLException{
		String tableSchema = getTableSchema();
		List<TableInfo> list = new ArrayList<TableInfo>();
		//Connection conn = getConnection();
		//DatabaseMetaData metaData = conn.getMetaData();
		PreparedStatement ps = getMySQLTableInfoSchema(tableSchema,Arrays.asList(includeTableNames));//metaData.getTables(null, "%", "%", new String[]{"TABLE"});
		ResultSet rs = ps.executeQuery();
		TableInfo tableInfo = null;
		List<String> tableNames = new ArrayList<String>();
		Map<String,String> comments  =  new HashMap<String, String>();
		while( rs.next() ){
			String tableName = rs.getString("TABLE_NAME");
			tableNames.add(tableName);
			comments.put(tableName, rs.getString("TABLE_COMMENT"));
		}
		release(null,ps, rs);//释放资源
		for(String tableName : tableNames ){
			ps = getMySQLColumnInfoSchema(tableSchema, tableName);//metaData.getColumns(conn.getCatalog(), null, tableName, null);
			rs = ps.executeQuery();
			String clzName = StringUtil.decorateTableName(tableName);//默认为tableName;
			if( comments != null && StringUtil.isNotEmpty(comments.get(tableName))){
				clzName = comments.get(tableName);
				List<String> mList = StringUtil.getMiddleStrByDelimiter(clzName, "#");
				clzName = mList != null && mList.size() > 0 ?  mList.get(0):clzName;
			}
			tableInfo = new TableInfo(tableName,clzName);
			while( rs.next() ){
				String dbColumn = rs.getString("COLUMN_NAME");
				String columnName = StringUtil.getHumpString(dbColumn);
				String columnComment = rs.getString("COLUMN_COMMENT");
				String columnType = rs.getString("DATA_TYPE").toUpperCase();
				String javaType = MySQLColumnType.getMySQLColumnType(columnType).getJavaType();
				if ("int".equalsIgnoreCase(columnType)){
					columnType = "INTEGER";
				}
				tableInfo.getColumnInfoes().add(new ColumnInfo(dbColumn,columnName,columnType,javaType,columnComment));
			}
			list.add(tableInfo);
			JDBCUtil.release(ps,rs);
		}
		return list;
	}
	
	/**
	 * 获取MySQL数据表的注释
	 * @param tableSchema
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public static String getMySQLTableComment(String tableSchema,String tableName) throws SQLException{
		String sql = "select table_comment FROM information_schema.TABLES where  table_schema='"+tableSchema+"' and table_name ='"+tableName+"'";
		Connection conn = getConnection();
		PreparedStatement ps  = null;
		ResultSet rs = null;
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while( rs.next() ){
			return rs.getString(1);
		}
		release(ps,rs);
		return null;
		
	}
	
	
	/**
	 * 
	 * @param tableSchema
	 * @param tableNames
	 * @return 返回 以表名为 key ，注释为value的map
	 * @throws SQLException
	 */
	public static Map<String ,String> getMySQLTablesComment(Connection conn,String tableSchema,String tableNames) throws SQLException{
		String sql = "select table_name ,table_comment FROM information_schema.TABLES where  table_schema= ? and table_name in (?)";
		PreparedStatement ps  = null;
		ResultSet rs = null;
		ps = conn.prepareStatement(sql);
		ps.setString(1, tableSchema);
		ps.setString(2, tableNames);
		rs = ps.executeQuery();
		Map<String,String> map = new HashMap<String, String>();
		while( rs.next() ){
			map.put(rs.getString(1), rs.getString(2));
		}
		return map;
		
	}
	
	/**
	 * 根据url取schema
	 * @return
	 */
	public static String getTableSchema(){
		int end = url.indexOf("?");
		int start = -1;
		while( end -- > 0 ){
			start = url.lastIndexOf("/");
			if( start >= 0 ){
				break;
			}
		}
		return url.substring(start + 1 , end + 1);
	}
	
	/**
	 * 删除库中所有数据
	 * 
	 * @throws SQLException
	 */
	public static void deleteAllTables() throws SQLException{
		List<TableInfo> tables = getTableInfoes();
		Connection conn = getConnection();
		PreparedStatement ps = null;
		for(TableInfo table :tables){
			ps = conn.prepareStatement("delete from " +table.getTableName());
			ps.execute();
		}
		release(ps, null);
	}
	
	/**
	 * 根据返回的列号取得字段的名称
	 * 
	 * @param resultSetMetaData
	 * @param columnIndex
	 * @return
	 * @throws SQLException
	 */
	public static String lookupColumnName(ResultSetMetaData resultSetMetaData, int columnIndex) throws SQLException {
		String name = resultSetMetaData.getColumnLabel(columnIndex);
		if (name == null || name.length() < 1) {
			name = resultSetMetaData.getColumnName(columnIndex);
		}
		return name;
	}
	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
//		Connection conn = JDBCUtil.getConnection();
//		DatabaseMetaData metaData = conn.getMetaData();
//		ResultSet rs = metaData.getTables(null, null, null, new String[]{"TABLE"});
//		while( rs.next() ){
//			System.out.println(rs.getString("TABLE_NAME"));
//		}
//		JDBCUtil.release();
		
//		List<TableInfo> tables = JDBCUtil.getTableInfoes();
//		for( TableInfo info : tables){
//			System.out.println(" 表名 "+info.getTableName());
//			for(ColumnInfo column :info.getColumnInfoes()){
//				System.out.println("      列   ");
//				System.out.println("      列名   "+column.getField());
//				System.out.println("      列类型   "+column.getColumnType());
//				System.out.println("      java类型   "+column.getJavaType());
//				System.out.println("      simple java类型   "+column.getSimpleJavaType());
//				System.out.println("      列注释   "+column.getColumnComment());
//				
//			}
//		}
		
//		getTableInfoes();
		//deleteAllTables();
		System.out.println(isExistColumn("citydetails_cache", "citycode"));
		
		
	}

}
