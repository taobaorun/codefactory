/**
 * author wu tao time 2011-8-10
 * editor
 * CodeGenerator.java
 * copyright legalworker 2011
 */
package com.jiaxy.repository;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.jiaxy.repository.table.ColumnInfo;
import com.jiaxy.repository.table.TableInfo;
import com.jiaxy.util.DateTimeUtil;
import com.jiaxy.util.StringUtil;
import com.jiaxy.util.VelocityUtil;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.VelocityContext;

/**
 * 功能概述:<br/>
 * 
 * 用于产生实体对象
 * 
 */
public class CodeGenerator {
	
	private static Properties p;
	
	private static void init() throws IOException{
		p = new Properties();
		p.load(CodeGenerator.class.getClassLoader().getResourceAsStream("entity.properties"));
	}
	
	public static void generateCode(String ... includeTableNames) throws IOException, SQLException{
		System.out.println("----code generating ......");
		init();
		VelocityUtil.VelocityProxy vp = VelocityUtil.getDefaultInitVelocity();
		String savePath = p.getProperty("prjabsolutepath")+File.separator+StringUtil.covertPackageTOPath(p.getProperty("package"));
		VelocityContext context = new VelocityContext();
		context.put("StringUtil", StringUtil.class);
		context.put("package", p.getProperty("package"));
		context.put("time", DateTimeUtil.getFormatedDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		context.put("year", Integer.valueOf(DateTimeUtil.getYear(new Date())));
		List<TableInfo> tables = JDBCUtil.getTableInfoes(includeTableNames);
		for(TableInfo tableInfo : tables ){
			VelocityContext contextEntity = new VelocityContext(context);
			contextEntity.put("impotList", getImportClass(tableInfo));
			contextEntity.put("tableInfo", tableInfo);
			StringWriter sw = new StringWriter();
			if(vp.mergeTemplate("entity.vm", "UTF-8", contextEntity, sw)){
				String javaClz = sw.toString();
				File srcfile = new File(savePath+File.separator+StringUtil.getFirstUpperCase(tableInfo.getClzName())+".java");
				FileUtils.write(srcfile, javaClz);
			}
		}
		System.out.println("----code over ----");
		
	}
	
	/**
	 * 
	 * @param tableInfo
	 * @return
	 */
	private static List<String> getImportClass(TableInfo tableInfo){
		List<String> importClz = new ArrayList<String>();
		for(ColumnInfo column : tableInfo.getColumnInfoes() ){
			if( column.getJavaType().indexOf(".") > 0 ){
				if( !importClz.contains(column.getJavaType())){
					importClz.add(column.getJavaType());
				}
			}
		}
		return importClz;
	}

	/**
	 * @param args
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, SQLException {
		generateCode("police_info");
	}

}
