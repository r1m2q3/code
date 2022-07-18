package com.honest.enterprise.core.strategy;

import com.honest.enterprise.core.annotation.AnnStrategy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 扫描实现 AnnStrategy注解的实现类，通过枚举建立策略映射关系
 * date:2022-07-17 10:56:00
 * author:fanjie
 */
@Component
public class StrategyHandleProcessor implements ApplicationContextAware {
    /**
     * 获取所有的策略Beanclass 加入AuthCenterContext属性中
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //获取所有策略注解的Bean
        Map<String, Object> orderStrategyMap = applicationContext.getBeansWithAnnotation(AnnStrategy.class);
        if(orderStrategyMap!=null&&orderStrategyMap.size()>0){
            for(Map.Entry<String, Object> entry : orderStrategyMap.entrySet()){
                Class<?> strategyClass = entry.getValue().getClass();
                String key=getStrategyKey(strategyClass);
                //将class加入map中,type作为key
                if(StringUtils.isNoneEmpty(key)) {
                    StrategyContext.strategyMap.put(key, strategyClass);
                }
            }
        }
    }

    /**
     * 获取策略key
     * @param strategyClass
     * @return
     */
    private String getStrategyKey(Class<?> strategyClass){
        //接口名称
        String infName=null;

        Class<?>[] inf=strategyClass.getInterfaces();
        //如果当前类 实现了策略接口
        if(inf.length==1){
            infName=inf[0].getName();
        }else if(inf.length==0){
            //当前类没有策略接口，则直接往上级找
            inf=strategyClass.getSuperclass().getInterfaces();
            if(inf.length==1){
                infName=inf[0].getName();
            }
        }
        if(StringUtils.isNoneEmpty(infName)){
            //获取当前类的注解枚举类型
            String type = strategyClass.getAnnotation(AnnStrategy.class).value();
            return infName+"_"+type;
        }
        return "";
    }
}
