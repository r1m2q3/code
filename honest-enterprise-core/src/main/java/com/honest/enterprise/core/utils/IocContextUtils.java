package com.honest.enterprise.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * ioc 容器内容 工具类
 * author fanjie
 * date:2022-07-17
 */
@Component
@Slf4j
public class IocContextUtils implements ApplicationContextAware, BeanFactoryAware {
    private BeanFactory beanFactory;
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(IocContextUtils.applicationContext == null) {
            IocContextUtils.applicationContext = applicationContext;
        }
        System.out.println("========ApplicationContext配置成功,在普通类可以通过调用SpringUtils.getAppContext()获取applicationContext对象,applicationContext="+ IocContextUtils.applicationContext+"========");
        System.out.println("---------------------------------------------------------------------");
    }

    //获取applicationContext
    public  ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public  Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public  <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }
    //通过class获取Bean.
    public  <T> Map<String,T> getBeansOfType(Class<T> clazz){
        return getApplicationContext().getBeansOfType(clazz);
    }

    //通过class获取Bean.
    public <T>  String getBeanNameForType(Class<T> clazz){
        return getApplicationContext().getBeanNamesForType(clazz)[0];
    }

    //通过name,以及Clazz返回指定的Bean
    public  <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * 通过注解类型获取所有使用该注解的所有类
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends Annotation> Map<String, Object> getClassMapByAnn(Class<T> clazz){
        Map<String, Object> orderStrategyMap = applicationContext.getBeansWithAnnotation(clazz);
        return orderStrategyMap;
    }

    /**
     * 注册类到容器
     * @param name
     * @param <T>
     */
    public <T>void registerBean(String name,T obj){
 /*       DefaultListableBeanFactory context = new DefaultListableBeanFactory();
        //用到了构建者模式
        BeanDefinitionBuilder b =BeanDefinitionBuilder.rootBeanDefinition(clazz).addPropertyValue("message", "this is a bean");
        context.registerBeanDefinition(name, b.getBeanDefinition());*/

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(obj.getClass());
        builder.addConstructorArgValue(obj);
        /*builder.addPropertyValue("name", "张三");
        builder.addPropertyValue("age", 20);*/
        //重点
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) this.beanFactory;
        registry.registerBeanDefinition(name, builder.getBeanDefinition());
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     * 执行bean中方法
     *
     * @param serviceName
     * @param methodName
     * @param params
     * @return
     */
    public Object springInvokeMethod(String serviceName, String methodName, Object... params) {

        Object service = applicationContext.getBean(serviceName);
        String runMethod = null;

        // 找到方法
        Class classz = service.getClass();
        Method[] methods = classz.getMethods();
        for (Method method : methods) {
            if(method.getName().equals(methodName)){
                runMethod = method.getName();
                break;
            }
        }

        if(runMethod == null){
            log.error("传入了错误的 code。没有此方法");
            return null;
        }
        Class<? extends Object>[] paramClass = null;
        if (Objects.nonNull(params)) {
            int paramsLength = params.length;
            paramClass = new Class[paramsLength];
            for (int i = 0; i < paramsLength; i++) {
                paramClass[i] = params[i].getClass();
            }
        }
        Method method = ReflectionUtils.findMethod(service.getClass(), runMethod, paramClass);
        // 执行方法
        return ReflectionUtils.invokeMethod(method, service, params);
    }

}