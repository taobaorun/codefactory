/**
 * author wu tao time 2011-8-17
 * editor
 * BeanUtil.java
 * copyright legalworker 2011
 */
package com.jiaxy.util;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;


/**
 * 功能概述:<br/>
 * 
 */
public class BeanUtil {
	
	private static final Logger log = LoggerFactory.getLogger(BeanUtil.class);
	
	public static void setProperty(Object bean, String name, Object value)
				throws IllegalAccessException, InvocationTargetException {
		if( value instanceof Date){
			value = Long.valueOf(((Date)value).getTime()).toString();
			BeanUtilsBean.getInstance().getConvertUtils().register(new UtilDateConverter(), Date.class);
		}
		try {
			BeanUtilsBean.getInstance().setProperty(bean, name, value);
		} catch (ConversionException e) {
			log.info(bean.getClass().getName()+" 属性 "+name+" 赋值失败", e);
		} 
	}
	
	protected static class UtilDateConverter implements Converter{
		 /**
	     * The default value specified to our Constructor, if any.
	     */
	    private Object defaultValue = null;


	    /**
	     * Should we return the default value on conversion errors?
	     */
	    private boolean useDefault = true;
	    
	    
		@SuppressWarnings("rawtypes")
		public Object convert(Class type, Object value) {

	        if (value == null) {
	            if (useDefault) {
	                return (defaultValue);
	            } else {
	                throw new ConversionException("No value specified");
	            }
	        }
	        if (value instanceof Date) {
	            return (value);
	        }
	        try {
	        	long longtime = Long.parseLong(value.toString());
	            return new Date(longtime);
	        } catch (Exception e) {
	            if (useDefault) {
	                return (defaultValue);
	            } else {
	                throw new ConversionException(e);
	            }
	        }
		}
		
	}
}
