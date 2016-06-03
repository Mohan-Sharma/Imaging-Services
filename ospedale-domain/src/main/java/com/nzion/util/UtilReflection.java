package com.nzion.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.util.EqualsHelper;
import org.hibernate.util.ReflectHelper;
import org.springframework.util.StringUtils;

/**
 * @author Sandeep Prusty
 * Apr 13, 2010
 */

@SuppressWarnings("unchecked")
public class UtilReflection {

	public final static Class<?>[] EMPTY_CLASS_ARRAY = new Class[] {};
	public final static Object[] EMPTY_OBJECT_ARRAY = new Object[] {};

	public static Type[] getGenericParameters(Class<?> klass) {
	Type type = klass.getGenericSuperclass();
	if (!(type instanceof ParameterizedType)) return null;
	ParameterizedType paramType = (ParameterizedType) type;
	return paramType.getActualTypeArguments();
	}

	public static Type getFirstGenericParameters(Class<?> klass) {
	Type[] types = getGenericParameters(klass);
	return UtilValidator.isEmpty(types) ? null : types[0];
	}
	
	public static Object getValue(Object object, String fieldName) {
	try {
		return getGetterMethod(fieldName, object.getClass()).invoke(object, EMPTY_OBJECT_ARRAY);
	} catch (Exception e) {
		throw new RuntimeException(e);
	}
	}

	public static Object getValue(Object object, String fieldName, Object defaultValue) {
	Object result = getValue(object, fieldName);
	return result == null ? defaultValue : result;
	}

	public static String getSimpleProperty(Object object, String propertyName) {
	try {
		return BeanUtils.getProperty(object, propertyName);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
	}

	public static String buildStringRepresentation(Object object) {
	try {
		return BeanUtils.describe(object).toString();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return "Cannot read the properties";
	}

	public static String getGetterMethodName(String fieldName) {
	return "get" + StringUtils.capitalize(fieldName);
	}

	public static Method getGetterMethod(String fieldName, Class<?> klass) throws SecurityException,
			NoSuchMethodException {
	return ReflectHelper.getGetter(klass, fieldName).getMethod();
	}

	public static Class<?> getNestedFieldType(String fieldName, Class<?> klass) {
	Class<?> currentClass = klass;
	try {
		if (fieldName.indexOf('.') == -1) return getGetterMethod(fieldName, klass).getReturnType();
		String[] fieldNames = fieldName.split("\\.");
		for (String currentFieldName : fieldNames)
			currentClass = getGetterMethod(currentFieldName, currentClass).getReturnType();
	} catch (Exception exception) {
		throw new RuntimeException(exception);
	}
	return currentClass;
	}

	public static String getSetterMethodName(String fieldName) {
	return "set" + StringUtils.capitalize(fieldName);
	}

	public static Method getSetterMethod(String fieldName, Class<?> klass, Class<?> argType) throws SecurityException,
			NoSuchMethodException {
	return klass.getMethod(getSetterMethodName(fieldName), new Class[] { argType });
	}

	public static void setFieldValue(Object object, String fieldName, Object value, Class<?> fieldType) {
	try {
		getSetterMethod(fieldName, object.getClass(), fieldType).invoke(object, value);
	} catch (Exception e) {
		e.printStackTrace();
	}
	}

	public static Method getMethodByName(String methodName, Class<?> klass, Class<?> argType) throws SecurityException,
			NoSuchMethodException {
	return klass.getMethod(methodName, new Class[] { argType });
	}

	public static void invokeMethod(Object object, String methodName, Object value, Class<?> fieldType) {
	try {
		getMethodByName(methodName, object.getClass(), fieldType).invoke(object, value);
	} catch (Exception e) {
		throw new RuntimeException(e);
	}
	}

	public static Object getFieldValue(Object object, String fieldName) {
	Object returnValue = null;
	try {
		returnValue = getGetterMethod(fieldName, object.getClass()).invoke(object, EMPTY_OBJECT_ARRAY);
	} catch (Exception e) {
		throw new RuntimeException(e);
	}
	return returnValue;
	}

	public static Object getNestedFieldValue(Object object, String fieldName) {
	if(object == null || fieldName == null)
		return null;
	if (fieldName.indexOf('.') == -1) return getFieldValue(object, fieldName);
	String[] fieldNames = fieldName.split("\\.");
	Object returnValue = object;
	for (String currentFieldName : fieldNames) {
		if (returnValue == null) return null;
		if (Map.class.isAssignableFrom(returnValue.getClass())) {
			returnValue = ((Map<?, ?>) returnValue).get(currentFieldName);
			continue;
		}
		returnValue = getFieldValue(returnValue, currentFieldName);
	}
	return returnValue;
	}
	
	public static void setNestedFieldValue(Object object, String fieldName, Object fieldValue) {
	try {
		if (fieldName.indexOf('.') == -1){ 
			BeanUtils.copyProperty(object, fieldName, fieldValue);
			return;
		}
		int lastIndexOfDot = fieldName.lastIndexOf('.');
		Object objectToReceiveSet = getNestedFieldValue(object, fieldName.substring(0,lastIndexOfDot));
		BeanUtils.copyProperty(objectToReceiveSet, fieldName.substring(lastIndexOfDot + 1), fieldValue);
	}catch (Exception e) {
		throw new RuntimeException(e);
	}
	}

	public static void putAllFieldsInMap(Object object, Map<String, Object> map) {
	Field[] fields = object.getClass().getDeclaredFields();
	try {
		for (Field field : fields) {
			field.setAccessible(true);
			if (Map.class.isAssignableFrom(field.getType())) {
				map.putAll((Map<String, Object>) field.get(object));
				continue;
			}
			map.put(field.getName(), field.get(object));
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	}

	public static boolean areEqual(Object obj1, Object obj2, String... fields) {
	if(obj1==null || obj2==null || UtilValidator.isEmpty(fields))
		return false;
	if (!obj1.getClass().equals(obj2.getClass()))
		return false;
	try {
		for (String field : fields) {
			Object obj1Value = UtilReflection.getFieldValue(obj1, field);
			Object obj2Value = UtilReflection.getFieldValue(obj2, field);
			if (!EqualsHelper.equals(obj1Value, obj2Value))
				return false;
		}
	} catch (Throwable th) {
		th.printStackTrace();
		return false;
	}
	return true;
	}
	
	public static Annotation getAnnotationOnGetter(Class<?> klass, Class<? extends Annotation> annotationClass, String fieldName){
	try {
		return getGetterMethod(fieldName, klass).getAnnotation(annotationClass);
	} catch (Exception e) {
		throw new RuntimeException(e);
	}
	}
	
	public static void copyDeclaredProperties(Object dest, Object orig, String ...excludedFields){
	List<String> exclusions = Arrays.asList(excludedFields);
	Field[] fields = orig.getClass().getDeclaredFields();
	for(Field field : fields){
		if(exclusions.contains(field.getName()))
			continue;
		field.setAccessible(true);
		try {
			BeanUtils.copyProperty(dest, field.getName(), field.get(orig));
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	}
}