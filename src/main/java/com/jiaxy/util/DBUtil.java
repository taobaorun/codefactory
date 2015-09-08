/**
 * creator BigBillows
 * time 2011-7-19
 * DBUtil.java
 * copyright legalworker 2011
 */
package com.jiaxy.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 *  数据库操作工具类
 *  
 *
 */
public class DBUtil {
	
	/**
	 * 根据数据源取得conn
	 * @param ds
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConenction(DataSource ds,boolean autoCommit) throws SQLException{
		if( ds == null){
			return null;
		}else{
			Connection conn = ds.getConnection();
			conn.setAutoCommit(autoCommit);
			return conn;
		}
	}
	
	/**
	 * 根据数据源取得conn
	 * @param ds
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConenction(DataSource ds) throws SQLException{
		if( ds == null){
			return null;
		}else{
			Connection conn = ds.getConnection();
			return conn;
		}
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
		if( rs != null ){
			rs.close();
		}
		if( ps != null ){
			ps.close();
		}
		if( conn != null ){
			conn.close();
		}
	}
	
//	@SuppressWarnings("unchecked")
//	public static <T> T getRSValue(ResultSet rs,String column,DataTypeEnum dataType){
//		T result = null;
//		try {
//			if(DataTypeEnum.INT == dataType ){
//				result = (T) Integer.valueOf(rs.getInt(column));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return result;
//	}

}
