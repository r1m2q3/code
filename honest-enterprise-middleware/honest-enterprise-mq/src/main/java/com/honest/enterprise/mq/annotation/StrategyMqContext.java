package com.honest.enterprise.mq.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 获取通用策略类
 * @date:2021-12-23 10:56:00
 * @author:fanjie
 */
@Component
public class StrategyMqContext {
    @Autowired
    private ApplicationContext applicationContext;

    /**
     *使用线程安全的ConcurrentHashMap (策略映射关系)
     *key:beanName
     *value：实现Strategy接口Bean
     */
    //存放所有策略类Bean的map
    public static Map<String, Class<?> > strategyMap= new ConcurrentHashMap<>();


    /**
     * 获取策略实现类
     * @param inf
     * @param type
     * @return
     */
    public <T> T  getStrategyImpl(Class<?> inf,String type){
        String key=inf.getName()+"_"+type;
        Class<?> strategyClass = strategyMap.get(key);
        if(strategyClass==null) {
           // throw new GdCenteringException(HttpResult.INVALID_AUTH_CENTER.getRetMsg(),HttpResult.INVALID_AUTH_CENTER.getRetCode());
        }
        //从容器中获取对应的策略Bean
        return (T) applicationContext.getBean(strategyClass);
    }
}
