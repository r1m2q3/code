package com.honest.enterprise.config.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fanjie
 * @desc 自定义JsonHttpMessageConverter解决long类型id丢精度问题
 * @date 2022-07-17 11:26
 */
//@Configuration
@Deprecated
public class CustomFastJsonHttpMessageConverter {

//    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();

        List<SerializerFeature> list = new ArrayList<>();
//        list.add(SerializerFeature.PrettyFormat);
        list.add(SerializerFeature.WriteMapNullValue);
        list.add(SerializerFeature.WriteNullStringAsEmpty);
        list.add(SerializerFeature.WriteNullListAsEmpty);
        list.add(SerializerFeature.QuoteFieldNames);
        list.add(SerializerFeature.WriteDateUseDateFormat);
        list.add(SerializerFeature.DisableCircularReferenceDetect);
        list.add(SerializerFeature.WriteBigDecimalAsPlain);

        fastJsonConfig.setSerializerFeatures(list.toArray(new SerializerFeature[list.size()]));

        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(supportedMediaTypes);

        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        fastJsonConfig.setSerializeFilters((ValueFilter) (object, name, value) -> {
            if (isId(name,value)) {
                return String.valueOf(value);
            }
            return value;
        });
        return new HttpMessageConverters(converter);
    }

    private boolean isId(String name, Object value) {
        return (StringUtils.endsWith(name, "Id") || StringUtils.equals(name, "id")) && value != null
                && value.getClass() == Long.class;
    }

}
