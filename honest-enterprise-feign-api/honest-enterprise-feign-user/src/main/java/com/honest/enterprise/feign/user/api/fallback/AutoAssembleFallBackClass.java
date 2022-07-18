package com.honest.enterprise.feign.user.api.fallback;


import com.honest.enterprise.feign.core.auto.IAutoAssembleFallBackClass;

/**
 * 自动装配fallback class字节码
 * @author fanjie
 * @date 2022-7-17
 */
public class AutoAssembleFallBackClass implements IAutoAssembleFallBackClass {
    /**当前类所在路径**/
    private final static String CUR_CLASS_PATH = AutoAssembleFallBackClass.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    /**注册中心名称**/
    private final static String REGISTER_CENTER="honest-enterprise-user";


    /**
     * 构造函数初始化
     */
    public AutoAssembleFallBackClass(){
         //this.registerByteCode();
    }

    @Override
    public String getCurClassPath() {
        return CUR_CLASS_PATH;
    }

    @Override
    public String getRegisterCenter() {
        return REGISTER_CENTER;
    }



}
