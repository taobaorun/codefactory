package com.jiaxy.util;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	public static boolean isEmpty(String source) {
		if(source == null || source.length() == 0 || source.trim().equals("null"))
			return true;
		else
			return false;
	}
	
	public static boolean isNotEmpty(String source) {
		return !isEmpty(source);
	}
	
	public static boolean isNum(String num) {
		Pattern pattern = Pattern.compile("(\\-?)[0-9]*(\\.?)[0-9]*");
		Matcher isNum = pattern.matcher(num);
		if(isNum.matches()){
			//System.out.println("是一个数字");
			return true; 
			}else{
			//System.out.println("不是一个数字");
			return false; 
			}
		
	}
	
	public static Timestamp time(String time) {
	Date date;
	Timestamp d;
	try {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date = sdf.parse(time);
		
		d=  new Timestamp(date.getTime());

	} catch (ParseException e) {
		
		d=null;
		e.printStackTrace();
	}
	return d;
	}
	
	public static String remind_pwd() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss"); 
		String nowdate = sdf.format(new Date()); //返回字符型
		//36位uuid
		UUID uuid = UUID.randomUUID();
		//36+14=50; 
		String uuid_time=uuid+nowdate;
		
	
		return uuid_time;
	}
	
	public static String getCallRemindPwd() {
	int num = (int) ((Math.random()) * 1000000);
	while (num<=99999) {
		
		num = (int) ((Math.random() ) * 1000000);
	}
	return num+"";
	}
	
	/**
     * 获得当前时间一小时前的时间，格式化成yyyyMMdd HHmmss:
     * 
     * @return 当前时间2小时前的时间
     */
    public static String getTwoHoursAgoTime() {
        String oneHoursAgoTime = "";
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        oneHoursAgoTime = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(cal.getTime());
        return oneHoursAgoTime;
    }
    
	public static Date parseDate(String date, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static String formatDate(Date date, String pattern) {
		if(date == null) return "";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	public static String nowDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String nowdate = sdf.format(new Date()); //返回字符型
		return nowdate;
	}
	public static String batchID (){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS"); 
		String nowdate = sdf.format(new Date()); //返回字符型
		int num = (int) ((Math.random()) * 1000);
		while (num<=99) {
			
			num = (int) ((Math.random() ) * 1000);
		}
		String UserID=nowdate+num;
		
		return UserID;
		
	}
	public static String imageID (){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS"); 
		String nowdate = sdf.format(new Date()); //返回字符型
		return nowdate;
		
	}
/*	生成卡号规则：
	卡号12位，9801 0000 0018 说明：98（卡BIN号）+010（产品代码）+000001（卡的序号递增）+8为固定数字（暂定）。
	生成代金卷（根据卡产品定义时，定义代金卷金额和数量生成代金卷）*/
	public static String cardID (String CardProductID,int num){
		DecimalFormat g=new DecimalFormat("000000");
		String cid="98"+CardProductID+g.format(num)+"8";
		return cid;
		
	}
	
	/**
	 * 按指定字符集返回字符串
	 * 
	 * @param sourceStr
	 * @param c
	 * @return
	 */
	public static String getStringByCharcter(String sourceStr,String c){
		if( StringUtil.isEmpty(sourceStr) ) return sourceStr;
		try {
			return new String(sourceStr.getBytes(c));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sourceStr;
	}
	
	/**
	 * 首字母小写
	 * 
	 * @param str
	 * @return
	 */
	public static String getFirstLowerCase(String str){
		if(StringUtil.isEmpty(str)){
			return str;
		}else{
			return str.substring(0, 1).toLowerCase().concat(str.substring(1, str.length()));
		}
	}
	/**
	 * 首字母大写
	 * 
	 * @param str
	 * @return
	 */
	public static String getFirstUpperCase(String str){
		if(StringUtil.isEmpty(str)){
			return str;
		}else{
			return str.substring(0, 1).toUpperCase().concat(str.substring(1, str.length()));
		}
	}
	
	/**
	 * 驼峰形状的字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String getHumpString(String str){
		if(!StringUtil.isEmpty(str)){
			if( str.length() <=2 ){
				return str.toLowerCase();
			}else{
				getFirstLowerCase(str);
			}
		}
		return str;
	}
	
	/**
	 * 将包路径转化成路径
	 * @param packagestr
	 * @return
	 */
	public static String covertPackageTOPath(String packagestr){
		if(! StringUtil.isEmpty(packagestr) ){
			return packagestr.replaceAll("\\.", "\\"+File.separator);
		}
		return packagestr;
	}
	
	/**
	 * 得到分界符中间的字符串
	 * 如 #hello#返回hello
	 * @param str
	 * @param delimite
	 * @return
	 */
	public static List<String> getMiddleStrByDelimiter(String str,String delimiter){
		String regex = "\\"+delimiter+"{1}?.*?"+"\\"+delimiter+"{1}?";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		String middlestr = "";
		List<String> list = new ArrayList<String>();
		while( m.find() ){
			middlestr = m.group();
			middlestr = middlestr.replaceAll("\\"+delimiter, "");
			list.add(middlestr);
		}
		return list;
		
	}
	
	/**
	 * 得到分界符中间的字符串
	 * 如 #hello##world#返回hello,world
	 * @param str
	 * @param delimite
	 * @param greedy 为false，只要找到满足条件的一个就返回
	 * @return
	 */
	public static List<String> getMiddleStrByDelimiter(String str,String delimiter,boolean greedy){
		return getMiddleStrByDelimiter(str,delimiter,false,greedy);
		
	}
	
	
	/**
	 * 得到分界符中间的字符串
	 * 如 #hello##world#返回hello,world
	 * @param str
	 * @param delimite
	 * @param caseinsensitive 为true，大小写不敏感
	 * @param greedy 为false，只要找到满足条件的一个就返回
	 * @return
	 */
	public static List<String> getMiddleStrByDelimiter(String str,String delimiter,boolean caseinsensitive, boolean greedy){
		if( caseinsensitive ){
			str = str.toLowerCase();
			delimiter = delimiter.toLowerCase();
		}
		if(StringUtil.isEmpty(delimiter)){
			return null;
		}
		String regexnw = "\\W";
		Pattern pnw = Pattern.compile(regexnw);
		Matcher mnw = pnw.matcher(delimiter.subSequence(0, 1));
		String regex = delimiter+"{1}?.*?"+delimiter+"{1}?";
		boolean isNW = false;
		if( mnw.matches()){//如果delimiter首字母是字符需转义
			regex = "\\"+delimiter+"{1}?.*?"+"\\"+delimiter+"{1}?";
			isNW = true;
		}
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		String middlestr = "";
		List<String> list = new ArrayList<String>();
		while( m.find() ){
			middlestr = m.group();
			middlestr = middlestr.replaceAll(isNW ?"\\"+delimiter:delimiter, "");
			list.add(middlestr);
			if( !greedy ){
				return list;
			}
		}
		return list;
		
	}
	
	/**
	 * 按指定字符，把一个字符串集合连接成一个字符
	 * 如果isQuote 为true将在每一个字符串元素加上单引号，用于sql一些场景中
	 * 
	 * @param col
	 * @param delimiter
	 * @param isQuote
	 * @return
	 */
	public static String concatByDelimiter(Collection<String> col,String delimiter,boolean isQuote){
		StringBuffer sb = new StringBuffer();
		if( col != null && col.size() > 0 ){
			for(String str : col ){
				if( isQuote ){
					str = addQuote(str);
				}
				sb.append(str).append(delimiter);
			}
		}
		if( sb.length() > 0 ){
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String addQuote(String str){
		if( StringUtil.isNotEmpty(str)){
			return "'"+str+"'";
		}
		return str;
	}
	
	/**
	 * 删除sb某尾指定的字符（删除sb符合的最后一个字符）
	 * 
	 * @param sb
	 * @param delimiter
	 */
	public static void removeSBLastDelimiter(StringBuffer sb,String delimiter ){
		int lastIndex = sb.lastIndexOf(delimiter);
		if( lastIndex != -1 && StringUtil.isEmpty(sb.substring(lastIndex +delimiter.length() , sb.length())) ){//
			sb.delete(lastIndex, sb.length());
		}
	}
	
	
	/**
	 * 得到异常的堆栈信息
	 * 
	 * @param e
	 * @return
	 */
	public static String exceptionErrorMsg(Exception e) {
		StackTraceElement[] ste = e.getStackTrace();
		StringBuffer sb = new StringBuffer();
		sb.append(e.getClass().getName() + "\n");
		sb.append(e.getMessage() + "\n");
		for (int i = 0; i < ste.length; i++) {
			sb.append(ste[i].toString() + "\n");
		}
		return sb.toString();
	}
	
	/**
	 * 得到异常的堆栈信息
	 * 
	 * @param e
	 * @return
	 */
	public static String getExceptionTrace(Throwable e) {
		if (e != null) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return sw.toString();
		}
		return "";
	}
	
	/**
	 * 按指定分隔符将字符串转化成List集合
	 * 
	 * @param str
	 * @param delimiter
	 * @return
	 */
	public static List<String> converStrToListByDelimiter(String str,String delimiter){
		List<String> list = new ArrayList<String>();
		if(StringUtil.isNotEmpty(str)){
			list.addAll(Arrays.asList(str.split(delimiter)));
		}
		return list;
	}
	
	/**
	 * 按指定分隔符将字符串转化成Map集合
	 * 
	 * <code>String abc = "cn,China,us,USA"
	 * 		Map map = converStrToMapByDelimiter(abc);
	 * 		key cn value China
	 *      key us value USA
	 * </code>
	 * 
	 * @param str
	 * @param delimiter
	 * @return
	 */
	public static Map<String,String> converStrToMapByDelimiter(String str,String delimiter){
		Map<String,String> map = new HashMap<String,String>();
		if(StringUtil.isNotEmpty(str)){
			String[] arr = str.split(delimiter);
			for( int i = 0 ;i < arr.length ; i++){
				String key = arr[i];
				String value = (i + 1) >= arr.length ? "":arr[++i];
				map.put(key != null ? key.trim():key, value != null ? value.trim():value);
			}
		}
		return map;
	}
	
	/**
	 * 判断字符串中是否含有中文
	 * 
	 * @param str
	 * @return
	 */
	public static boolean  isContainChinese(String str){
		Pattern pattern = Pattern.compile("[\u4E00-\u9FA5]");
		Matcher m = pattern.matcher(str);
		while( m.find()){
			return true;
		}
		return false;
		
	}
	
	/**
	 * 对字符串中的空白字符转义
	 * @param str
	 * @return
	 */
	public static String escapeWhitespace(String str ){
		String regex = "\\s+";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		StringBuffer sb = new StringBuffer();
		while( m.find()){
			String tmp = "";
			char[] charArrs = m.group().toCharArray();
			if( m.group().length() > 1 ){
				for( int i = 0 ;i < charArrs.length ;i++){
					tmp = tmp + escapeOneWhitespace(String.valueOf(charArrs[i]));
				}
			}
			m.appendReplacement(sb, tmp);
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * \n 回车(\u000a) 
		\t 水平制表符(\u0009) 
		\b 空格(\u0008) 
		\r 换行(\u000d) 
		\f 换页(\u000c) 
		
	 * @param str
	 * @return
	 */
	public static String whitespace2Unicode(String str){
		if("\n".equals(str)){
			return "\\\\u000a";
		}else if("\t".equals(str)){
			return "\\\\u0009";
		}else if("\b".equals(str)){
			return "\\\\u0008";
		}else if("\r".equals(str)){
			return "\\\\u000d";
		}else if("\f".equals(str)){
			return "\\\\u000c";
		}else{
			return str;
		}
	}
	
	public static String escapeOneWhitespace(String str){
		if("\n".equals(str)){
			return "\\\\\\\\n";
		}else if("\t".equals(str)){
			return "\\\\\\\\t";
		}else if("\b".equals(str)){
			return "\\\\\\\\b";
		}else if("\r".equals(str)){
			return "\\\\\\\\r";
		}else if("\f".equals(str)){
			return "\\\\\\\\f";
		}else{
			return str;
		}
	}
	
	/**
	 * 如果str为空则返回指定的defaultStr
	 * @param str
	 * @param defaultStr
	 * @return
	 */
	public static String getNotNullString(String str,String defaultStr){
		if(StringUtil.isEmpty(str)){
			return defaultStr;
		}else{
			return str;
		}
	}
	public static void main(String[] args) throws UnsupportedEncodingException {
		  System.out.println(covertPackageTOPath("java.util.Date"));
		  System.out.println(getMiddleStrByDelimiter("$Hello$$111$","$"));
		  StringBuffer sb = new StringBuffer("google,, AND,baidu, AND$$");
		  removeSBLastDelimiter(sb,"$");
		  System.out.println(sb);
		  System.out.println(converStrToMapByDelimiter("w,v1,w2,v2,w3,v3",",").get("w3"));
		  String expTrace = "EXCEPTIONDESC:Thread-25[com/iluguo.road/resource/vm/TH_HotelResRQ]请求HotelHub出错\n"+ 
							"ERRORCODE:1\n"+
							"ERRORDESC:2\n"+
							"	ERROR DETAILS:\n"+
							"		 errorCode:error.vendorError\n"+
							"		 errorDesc:担保方式不匹配1\n"+
							"		 errorCode:error.vendorError\n"+
							"		 errorDesc:担保方式不匹配2\n";
		 System.out.println(StringUtil.converStrToMapByDelimiter(expTrace.replaceAll("\n", ":"), ":").get("errorDesc")); 
		  System.out.println(escapeWhitespace("wutao\n\rgoog le	fff	ss	s" +
		  		"jjjj"));
		  
	}
	
	
}
