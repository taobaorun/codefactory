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
		String daopath = p.getProperty("daopath")+File.separator;
		String servicePath = p.getProperty("servicepath")+File.separator;
		String serviceTestPath = p.getProperty("servicetestpath")+File.separator;
		VelocityContext context = new VelocityContext();
		context.put("StringUtil", StringUtil.class);
		context.put("package", p.getProperty("package"));
		context.put("daopackage", p.getProperty("daopackage"));
		context.put("servicepackage", p.getProperty("servicepackage"));
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
			StringWriter daoxml = new StringWriter();
			if(vp.mergeTemplate("mybatisDAO.vm","UTF-8",contextEntity,daoxml)){
				String daoXML = daoxml.toString();
				File daoXMLFile = new File(daopath+"resources"+File.separator+"mapper"+File.separator+StringUtil.getFirstUpperCase(tableInfo.getClzName())+"DAO.xml");
				FileUtils.write(daoXMLFile, daoXML);

			}
            StringWriter dao = new StringWriter();
			if(vp.mergeTemplate("dao.vm","UTF-8",contextEntity,dao)){
				String daoStr = dao.toString();
				File daoFile = new File(daopath+"java"+File.separator+StringUtil.covertPackageTOPath(p.getProperty("daopackage"))+ File.separator + StringUtil.getFirstUpperCase(tableInfo.getClzName()) + "DAO.java");
				FileUtils.write(daoFile, daoStr);

			}
            StringWriter serviceSW = new StringWriter();
            if(vp.mergeTemplate("service.vm", "UTF-8", contextEntity, serviceSW)){
                String serviceStr = serviceSW.toString();
                File servicefile = new File(servicePath+File.separator+StringUtil.covertPackageTOPath(p.getProperty("servicepackage"))+File.separator+StringUtil.getFirstUpperCase(tableInfo.getClzName())+"Service.java");
                FileUtils.write(servicefile, serviceStr);
            }
            StringWriter serviceImplSW = new StringWriter();
            if(vp.mergeTemplate("serviceImpl.vm", "UTF-8", contextEntity, serviceImplSW)){
                String serviceStr = serviceImplSW.toString();
                File servicefile = new File(servicePath+File.separator+StringUtil.covertPackageTOPath(p.getProperty("servicepackage"))+File.separator+"impl"+File.separator+StringUtil.getFirstUpperCase(tableInfo.getClzName())+"ServiceImpl.java");
                FileUtils.write(servicefile, serviceStr);
            }
            StringWriter serviceImplTestSW = new StringWriter();
            if(vp.mergeTemplate("serviceTest.vm", "UTF-8", contextEntity, serviceImplTestSW)){
                String serviceStr = serviceImplTestSW.toString();
                File servicefile = new File(serviceTestPath+File.separator+StringUtil.covertPackageTOPath(p.getProperty("servicepackage"))+File.separator+"impl"+File.separator+StringUtil.getFirstUpperCase(tableInfo.getClzName())+"ServiceImplTest.java");
                FileUtils.write(servicefile, serviceStr);
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
		generateCode("see_doctor_type");
	}

}
