package com.honest.enterprise.core.utils;

import cn.hutool.core.util.ReflectUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * @author fanjie
 */
public class ReflectUtils {
    public static<T> void setValue(T item,String methodName,Object value){
        ReflectUtil.invoke(item, methodName, value);
        /*try{
            Method m2 = item.getClass().getMethod(methodName);
            m2.invoke(item,value);
        }catch (NoSuchMethodException e){
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }*/
    }
    public static<T> String getValue(T item,String methodName){
        try{
            Method m2 = item.getClass().getMethod(methodName);
            Object obj= m2.invoke(item);
            if(obj!=null){
                return obj.toString();
            }
        }catch (NoSuchMethodException e){
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static <T> Integer getIntValue(T item,String methodName){
        String value=getValue(item,methodName);
        if(value!=null){
            return Double.valueOf(value).intValue();
        }
        return null;

    }
    public static <T> BigDecimal getBigDecimalValue(T item, String methodName){
        String value=getValue(item,methodName);
        if(value!=null){
            return BigDecimal.valueOf(Double.valueOf(value));
        }
        return null;
    }
    public  static<T> Double getDoubleValue(T item,String methodName){
        String value=getValue(item,methodName);
        if(value!=null){
            return Double.valueOf(value);
        }
        return null;
    }
}
