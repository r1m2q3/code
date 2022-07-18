package com.honest.enterprise.core.utils;

import cn.hutool.core.date.DateUtil;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 对象copy对象类
 * @author fanjie
 */
public class CopierUtils {
    /**
     * 对象 copy
     * @param source
     * @param target
     * @param <T>
     */
    public static <T> void copy(T source,T target){
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.getConverterFactory().registerConverter(new StringDateConverter());
        mapperFactory.getConverterFactory().registerConverter(new DateStringConverter());
        mapperFactory.getConverterFactory().registerConverter(new StringArrayConverter());
        mapperFactory.getConverterFactory().registerConverter(new ArrayStringConverter());
        mapperFactory.getMapperFacade().map(source, target);
    }

    /**
     * 对象 copy
     * @param source
     * @param type
     * @param <T>
     */
    public static <T,S> S copy(T source,Class type){
        S obj=null;
        if(source==null){
            return obj;
        }
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.getConverterFactory().registerConverter(new StringDateConverter());
        mapperFactory.getConverterFactory().registerConverter(new DateStringConverter());
        mapperFactory.getConverterFactory().registerConverter(new StringArrayConverter());
        mapperFactory.getConverterFactory().registerConverter(new ArrayStringConverter());
        mapperFactory.getConverterFactory().registerConverter(new BigDecimalIntegerConverter());
        obj= (S) mapperFactory.getMapperFacade().map(source, type);
        return obj;
    }

    /**
     * 集合 copy
     * @param source
     * @param <T>
     */
    public static <T,S> List<S> copyList(List<T> source,Class type){
        List<S> list=null;
        if(source==null){
            return list;
        }
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.getConverterFactory().registerConverter(new StringDateConverter());
        mapperFactory.getConverterFactory().registerConverter(new DateStringConverter());
        mapperFactory.getConverterFactory().registerConverter(new StringArrayConverter());
        mapperFactory.getConverterFactory().registerConverter(new ArrayStringConverter());
        mapperFactory.getConverterFactory().registerConverter(new BigDecimalIntegerConverter());
        //mapperFactory.classMap(UserDTO.class, NewUserDTO.class).field("sex","newSex").byDefault().register();
        list=mapperFactory.getMapperFacade().mapAsList(source,type);
        return  list;
    }

    static class StringDateConverter extends CustomConverter<String, Date> {

        @Override
        public Date convert(String source, Type<? extends Date> destinationType, MappingContext context) {
            if (StringUtils.isNotEmpty(source)) {
                //仅yyyy-MM-dd 追加时分秒
                if(source.length()==10&&!source.contains(" ")){
                    source+=" 00:00:00";
                }
                try {
                    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(source);
                } catch (ParseException e) {
                    return null;
                }
            }
            return null;

        }

    }

    static class DateStringConverter extends CustomConverter<Date,String> {

        @Override
        public String convert(Date source, Type<? extends String> destinationType, MappingContext context) {
            if (source!=null) {
                String dateStr=DateUtil.format(source,"yyyy-MM-dd HH:mm:ss");
                return dateStr;
            }
            return null;

        }

    }

    static class StringArrayConverter extends CustomConverter<String, String[]> {

        @Override
        public String[] convert(String source, Type<? extends String[]> destinationType, MappingContext context) {
            if (StringUtils.isNotEmpty(source)) {
                return source.split(",");
            }
            return new String[]{};

        }

    }
    static class ArrayStringConverter extends CustomConverter<String[], String> {

        @Override
        public String convert(String[] source, Type<? extends String> destinationType, MappingContext context) {
            if (source!=null&&source.length>0) {
                return StringUtils.join(source,",");
            }
            return null;

        }

    }

    static class BigDecimalIntegerConverter extends CustomConverter<BigDecimal, Integer> {

        @Override
        public Integer convert(BigDecimal source, Type<? extends Integer> destinationType, MappingContext context) {
            if (source!=null) {
                return source.intValue();
            }
            return 0;

        }

    }
}
