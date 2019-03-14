package com.lxl.lincore.common.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class SimpleCopyUtil {
	/**
	 * @Description 复制参数 
	 * @author linxili
	 * @param source 数据源
	 * @param target 需要补充参数的对象
	 * @param includArray 需要补充的参数数组
	 * @time:2018年7月26日
	 */
	public static void copyProperties(Object source, Object target, String[] includArray) {
		if (source == null || target == null || includArray == null || includArray.length < 1) {
			return ;
		}
		List<String> includList = Arrays.asList(includArray); // 列表对象
		Class<? extends Object> classType = source.getClass();
		Method[] methods = classType.getMethods();// 获取所有包含继承类的public方法,

		for (Method method : methods) {
			String methodName = method.getName();// 获取get方法
			if (!methodName.startsWith("get")) continue;// 排除get以外的方法
			
			String paramName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);// 获取字段名
			if (!includList.contains(paramName)) continue;//排除不需要复制的字段
			
			try {
				String setMethodName = "set" + methodName.substring(3);//拼接set方法名
				Method setMethod = classType.getMethod(setMethodName, new Class[]{method.getReturnType()});// 获取set方法
				Object getValue = method.invoke(source);//get值
				setMethod.invoke(target, new Object[]{getValue});//set值
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @Description 复制参数
	 * @author linxili
	 * @param source 数据源
	 * @param includArray 需要补充的参数数组
	 * @return Object
	 * @time:2018年7月26日
	 */
	public static Object copyProperties(Object source, String[] includArray) {
		if (source == null) {
			return null;
		}
		try {
			Class<? extends Object> classType = source.getClass();
			Constructor<? extends Object> constructor = classType.getConstructor();// 获取构造函数
			Object target = constructor.newInstance();// 利用构造函数创建实例 
			
			if (includArray == null || includArray.length < 1) return target;
			
			List<String> includList = Arrays.asList(includArray); // 列表对象
			Method[] methods = classType.getMethods();// 获取所有public方法,包含继承类
			
			for (Method method : methods) {
				String methodName = method.getName();// 获取get方法
				if (!methodName.startsWith("get")) continue;// 排除get以外的方法
				String paramName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);// 获取字段名
				
				if (!includList.contains(paramName)) continue;// 排除不需要复制的字段
				String setMethodName = "set" + methodName.substring(3);// 拼接set方法名
				Method setMethod = classType.getMethod(setMethodName, new Class[]{method.getReturnType()});// 获取set方法
				
				Object getValue = method.invoke(source);// get值
				setMethod.invoke(target, new Object[]{getValue});// set值
			}
			return target;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
