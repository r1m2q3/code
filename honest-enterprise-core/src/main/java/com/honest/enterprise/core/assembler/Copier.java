package com.honest.enterprise.core.assembler;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * @author fanjie
 * create on 2022-07-17 14:58
 * 实现此接口让对象具有基本属性拷贝的能力
 */
public interface Copier {

    /**
     * 传入对象,对其进行属性拷贝
     *
     * @param target 拷贝目标对象
     */
    default <T> void copy(T target) {
        BeanUtils.copyProperties(this, target);
    }

    /**
     * 传入对象,对其进行属性拷贝（Orika模式,推荐使用性能高）
     *
     * @param target 拷贝目标对象
     */
    default <T> void copyOrika(T target) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.getMapperFacade().map(this, target);
    }



    /**
     * 传入对象类型, 进行属性拷贝并且得到其对象
     *
     * @param type 对象类型 Class对象
     * @return 对象
     */
    default <T> T copy(Class<T> type) {
        T target = BeanUtils.instantiateClass(type);
        copy(target);
        return target;
    }

    /**
     * 不为null的属性值才会拷贝
     *
     * @param target 拷贝目标对象
     */
    default <T> void copyNotNull(T target) {
        BeanWrapper wrapper = new BeanWrapperImpl(this);
        PropertyDescriptor[] pds = wrapper.getPropertyDescriptors();
        Set<String> properties = new HashSet<>();
        for (PropertyDescriptor propertyDescriptor : pds) {
            String propertyName = propertyDescriptor.getName();
            Object propertyValue = wrapper.getPropertyValue(propertyName);
            if (propertyValue != null) {
                if (propertyValue instanceof String && !StringUtils.hasText((String) propertyValue)) {
                    properties.add(propertyName);
                }
            } else {
                properties.add(propertyName);
            }
        }
        BeanUtils.copyProperties(this, target, properties.toArray(new String[0]));
        properties.clear();
    }
}
