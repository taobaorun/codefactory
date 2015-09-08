/**
 * author wu tao time 2011-8-12
 * editor
 * TimeLog.java
 * copyright legalworker 2011
 */
package com.jiaxy.util;

import java.util.Stack;


/**
 * 功能概述:<br/>
 * 
 * 统计操作耗时
 * 
 * 使用时已 TimeLog.reset()开始TimeLog.getExcuteTime()结束 须成对出现
 *  TimeLog.reset()
 *  	 TimeLog.reset()
 *  		code... 
 * 		 TimeLog.getExcuteTime()
 * 
 *  TimeLog.getExcuteTime()
 * 
 */
public class TimeLog {
	
	
	private static ThreadLocal<Stack<Long>> timeLocal = new ThreadLocal<Stack<Long>>();
	
	public static  void reset(){
		if( timeLocal.get() == null ){
			Stack<Long> stack = new Stack<Long>();
			stack.push(System.currentTimeMillis());
			timeLocal.set(stack);
		}else{
			Stack<Long> stack = timeLocal.get();
			stack.push(System.currentTimeMillis());
		}
	}
	
	public static  long getExcuteTime(){
		return System.currentTimeMillis() - (timeLocal.get().peek() != null ? timeLocal.get().pop() : System.currentTimeMillis());
		
	}
}
