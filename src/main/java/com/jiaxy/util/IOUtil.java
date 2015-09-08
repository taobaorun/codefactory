/**
 * author wu tao time 2011-8-10
 * editor
 * IOUtil.java
 * copyright legalworker 2011
 */
package com.jiaxy.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * 功能概述:<br/>
 * 
 */
public class IOUtil {

	
	public static void main(String[] args) throws IOException {
		FileUtils.write(new File("c://wutao.txt"), "wutao happy birthday");
	}
}
