package com.honest.enterprise.core.utils;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 *
 *  * @projectName
 *  * @title     AnnotationUtil
 *  * @package
 *  * @description    注解工具类
 *  * @author IT_CREAT
 *  * @date  2019 2019/9/14 21:16
 *  * @version V1.0.0
 *
 */
@Data
@Accessors(chain = true)
@ToString
@NoArgsConstructor
public class AnnotationUtils <T> {

    public Class<T> clazz;

    public AnnotationUtils(Class<T> clazz){
        this.clazz = clazz;
    }

    /**
     * 动态修改对象属性上某个注解的属性值，通过getClazz()方法可以得到修改后的class
     * @param fieldName 对象属性名称
     * @param annotationClass 注解class
     * @param attrName 注解属性名称
     * @param attrValue 注解属性值
     * @return 本工具类实例
     * @throws Exception 异常
     */
    public AnnotationUtils updateAnnoAttrValue(String fieldName, Class<? extends Annotation> annotationClass,String attrName, Object attrValue) throws Exception {
        Field[] declaredFields = this.clazz.getDeclaredFields();
        if(null != declaredFields && declaredFields.length != 0){
            for (int i=0;i<declaredFields.length;i++){
                Field declaredField = declaredFields[i];
                if(fieldName.equals(declaredField.getName())){
                    InvocationHandler invocationHandler = Proxy.getInvocationHandler(declaredField.getAnnotation(annotationClass));
                    Field hField = invocationHandler.getClass().getDeclaredField("memberValues");
                    hField.setAccessible(true);
                    Map memberValues  = (Map)hField.get(invocationHandler);
                    memberValues.put(attrName,attrValue);
                    break;
                }
            }
        }
        return this;
    }

    /**
     * 修改自定义注解属性
     * @param cz
     * @param annotationClass
     * @param attrName
     * @param attrValue
     * @return
     * @throws Exception
     */
    public AnnotationUtils updateAnnoAttrValue(Class cz,Annotation annotationClass,String attrName, Object attrValue) throws Exception {
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotationClass);
        Field hField = invocationHandler.getClass().getDeclaredField("memberValues");
        hField.setAccessible(true);
        Map memberValues  = (Map)hField.get(invocationHandler);
        memberValues.put(attrName,attrValue);
        return this;
    }

    public static void main(String[] args) {

    }
}
