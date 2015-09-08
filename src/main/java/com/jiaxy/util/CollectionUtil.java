/**
 * author wu tao time 2011-8-22
 * editor
 * CollectionUtil.java
 * copyright legalworker 2011
 */
package com.jiaxy.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 功能概述:<br/>
 * 
 */
public class CollectionUtil {

	private static final Log log = LogFactory.getLog(CollectionUtil.class);
	
	/**
	 * 将一个集合按num分成几组
	 * 
	 * 注:此处用的是subList方法，对返回的集合所做修改都会反映到传入的list集合对象中
	 * 
	 * @param list
	 * @param num
	 * @return
	 */
	public static <T> List<List<T>> splitList(List<T> list,int num){
		List<List<T>> slist = new ArrayList<List<T>>();
		if( !isColEmpty(list) && num <= list.size()){
			int size = list.size()/num;
			int remainder = list.size() % num;
			for( int i = 0 ;i < num ; i++){
				slist.add(list.subList(i*size, size *(i+1) ));
			}
			if( remainder != 0 )
				slist.add(list.subList(size * num, (size * num ) + remainder));
		}else{
			slist.add(list);
		}
		return  slist;
	}
	
	/**
	 * 判断集合是否为空
	 * 
	 * @param col
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isColEmpty(Collection col){
		if( col == null || col.size() == 0 ){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 清空集合
	 * 
	 * @param col
	 */
	@SuppressWarnings("rawtypes")
	public static void clearCollection(Collection col){
		if( !isColEmpty(col)){
			col.clear();
			col = null;
		}
	}
	
	/**
	 * 反转map，key作为value，value作为key
	 * @param map
	 * @return
	 */
	public static <K,V extends Object> Map<V,K> reverseMap(Map<K,V> map){
		if(map == null || map.size() == 0){
			return null;
		}else{
			Map<V,K> newMap = new HashMap<V, K>();
			for(Map.Entry<K, V> entry :map.entrySet()){
				newMap.put(entry.getValue(), entry.getKey());
			}
			return newMap;
		}
	}
	
	/**
	 * 取两个集合相同的元素
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static <T extends Object> List<T> getSameElements(List<T> list1,List<T> list2){
		List<T> sameList = new ArrayList<T>();
		if(isColEmpty(list1) || isColEmpty(list2)){
			return sameList;
		}
		for(T element:list1){
			if( list2.contains(element)){
				sameList.add(element);
			}
		}
		return sameList;
	}
	
	/**
	 * 
	 * @param list1
	 * @param list2
	 * @param notCompareNull 为true 如果两个集合当中任何一个为null,返回另一个集合，
	 * 						 即其中如有一个没有元素，相当于两个相同集合比较相同元素
	 * @return
	 */
	public static <T extends Object> List<T> getSameElements(List<T> list1,List<T> list2,boolean notCompareNull){
		List<T> sameList = new ArrayList<T>();
		if(isColEmpty(list1) ){
			 sameList.addAll(list2);
			 return sameList;
		}
		if(isColEmpty(list2) ){
			sameList.addAll(list1);
			 return sameList;
		}
		for(T element:list1){
			if( list2.contains(element)){
				sameList.add(element);
			}
		}
		return sameList;
	}
	
	/**
	 * 将List集合元素指定的属性的值作为key,元素作为value 
	 * 
	 * @param list
	 * @param keyProperty
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K,V>Map<K,V> convertList2Map(List<V> list,String keyProperty){
		Map<K,V> map = new HashMap<K, V>();
		for(V obj : list){
			try {
				Object key = PropertyUtils.getProperty(obj, keyProperty);
				map.put((K)key, obj);
			} catch (Exception e) {
				log.error("集合转换成Map出错", e);
			} 
		}
		return map;
	}
	
	
	/**
	 * 将集合中的元素的某个属性的值作为list集合返回
	 * 
	 * @param list
	 * @param keyProperty
	 * @param propertyClz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T>List<T> propertyValueAsList(List<?> list,String keyProperty,Class<T> propertyClz){
		List<T> plist = new ArrayList<T>();
		for(Object obj : list){
			try {
				Object pVlaue = PropertyUtils.getProperty(obj, keyProperty);
				plist.add((T)pVlaue);
			} catch (Exception e) {
				log.error("将集合中的元素的"+propertyClz.getName()+"属性的值作为list集合返回出错", e);
			} 
		}
		return plist;
	}
	
	
	public static void main(String[] args) {
		List<String> list =  Arrays.asList("iluguo","jiaxy","google","facebook","baidu");
		//List<String> list2 =  Arrays.asList("iluguo","jiaxy","apple","facebook","baidu");
		List<String> list3 =  null;
		for(String e :getSameElements(list,list3,true)){
				System.out.println(e);
		}
		
	}
		
}
