package com.honest.enterprise.feign.core.auto;

import cn.hutool.core.util.StrUtil;

import com.honest.enterprise.core.utils.JavassistUtils;
import com.honest.enterprise.core.utils.PackageUtils;
import com.honest.enterprise.feign.core.config.FeignConfigurationNoRetryer;
import javassist.CtClass;
import lombok.SneakyThrows;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * 自动注册fallback class字节码接口
 * @author fanjie
 * @date 2022-6-22
 */
public interface  IAutoAssembleFallBackClass {
    /**
     * 获取当前类路径虚方法
     * @return
     */
    String getCurClassPath();
    /**
     * 获取当前注册中心名称虚方法
     * @return
     */
    String getRegisterCenter();


    /**
     * 注册字节码
     */
    @SneakyThrows
    default void registerByteCode(){
        String feignClientPackageBasePath = this.getClass().getPackage().getName().replaceAll(".fallback","");
        //1.获取所有feign client的类全名称 list
        Set<String> list= PackageUtils.getClassName(feignClientPackageBasePath,false);
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        //2.遍历类
        for(String className:list) {
            //2.1通过类全名加载class，得到FeignClient注解信息
            Class cz = Class.forName(className);
            //获取此类的 FeignClient注解信息
            FeignClient annoFeignClient = (FeignClient) cz.getAnnotation(FeignClient.class);
            if(annoFeignClient==null){
                continue;
            }

            //2.2判断FeignClient注解信息中是否本身有fallback或fallbackFactory，如果有则不予处理
            InvocationHandler h = Proxy.getInvocationHandler(annoFeignClient);
            Field hField = h.getClass().getDeclaredField("memberValues");
            hField.setAccessible(true);
            Map<String,Object> memberValues = (Map) hField.get(h);
            //如果有fall back或者fallbackFactory
            Object fallBack=memberValues.get("fallback");
            if(fallBack.toString().equals("void")){
                fallBack=memberValues.get("fallbackFactory");
            }
            //校验是否有fallback 注解值，如果有就不需要生成字节码 continue即可
            if(!fallBack.toString().equals("void")){
                continue;
            }

            //2.3.生成fallback class类
            int one = className.lastIndexOf(".");
            String feignClientName=className.substring((one+1));
            String fullBackClassName=feignClientPackageBasePath+".fallback."+feignClientName+"FallBack";
            String fullIncName=className;
            CtClass ctInc = JavassistUtils.getCtClass(fullIncName);
            CtClass backClass= JavassistUtils.buildClassForInc(fullBackClassName,ctInc);

            //2.4.给fallback追加注解
            Map<String, Map<String, Object>> mapAnn=new HashMap<>();
            Map<String, Object> mapAnnValue=new HashMap<>();
            mapAnnValue.put("value","");
            mapAnn.put("org.springframework.stereotype.Component",mapAnnValue);

            mapAnnValue=new HashMap<>();
            mapAnnValue.put("topic","");
            mapAnn.put("lombok.extern.slf4j.Slf4j",mapAnnValue);
            JavassistUtils.modifyClassAnn(backClass, mapAnn, getCurClassPath());
            Class backFall= JavassistUtils.save(backClass,getCurClassPath(),true);


            //2.5.修改FeignClient接口的注解信息
            mapAnn=new HashMap<>();
            mapAnnValue=new HashMap<>();
            mapAnnValue.put("fallback",backFall);
            String contextId="f-"+getRegisterCenter()+"-"+ StrUtil.lowerFirst(feignClientName);
            mapAnnValue.put("contextId", contextId);
            //如果没有指定注册中心名称，则默认给当前注册中心名称
            Object objName=memberValues.get("name");
            if(objName==null||"".equals(objName.toString())) {
                mapAnnValue.put("name", getRegisterCenter());
            }else {
                mapAnnValue.put("name", objName);
            }
            mapAnnValue.put("path", annoFeignClient.path());
            //如果没有configuration，则默认追加configuration注解
            Class<?>[] configuration= (Class<?>[]) memberValues.get("configuration");
            if(configuration!=null&&configuration.length==0){
                mapAnnValue.put("configuration", FeignConfigurationNoRetryer.class);
            }else {
                mapAnnValue.put("configuration", configuration[0]);
            }
            mapAnn.put("org.springframework.cloud.openfeign.FeignClient",mapAnnValue);
            JavassistUtils.modifyClassAnn(ctInc, mapAnn, getCurClassPath());
            JavassistUtils.save(ctInc,getCurClassPath(),false);
        }
    }
}
