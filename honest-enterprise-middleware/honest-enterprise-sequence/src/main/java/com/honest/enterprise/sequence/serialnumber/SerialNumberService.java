package com.honest.enterprise.sequence.serialnumber;

import cn.hutool.core.util.NumberUtil;

import com.honest.enterprise.redis.utils.RedisUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 编号 生成服务
 * date:2021-04-01 09:49
 * author:fanjie
 */
@Service
public class SerialNumberService {
    //redis前缀
    private static final String redis_key_prefix="incr_";
    @Autowired
    private RedisUtils redisUtils;
    /**
     * 获取下一编号
     * @param prefix
     * @param prefixDigit
     * @return
     */
    public String nextNumber(String prefix,int prefixDigit){
        String  key=redis_key_prefix+prefix;
        long incy=redisUtils.incr(key,1);
        if(String.valueOf(incy).length()<prefixDigit){
            //高位补齐
            return prefix+String.format("%0"+prefixDigit+"d", incy);
        }
        return prefix+incy;
    }

    /**
     * @author luxun
     * @date 2021/4/23 11:21
     * @Description 获取下一编号（返回不带前缀值）
     */
    public String notPrefixNextNumber(String prefix, int prefixDigit) {
        String key = redis_key_prefix + prefix;
        long incy = redisUtils.incr(key, 1);
        if (String.valueOf(incy).length() < prefixDigit) {
            //高位补齐
            return String.format("%0" + prefixDigit + "d", incy);
        }
        return prefix + incy;
    }

    /**
     * 更新最终编号,返回更新后的编号
     * @param prefix
     * @param number
     * @return
     */
    public String updateLastNumber(String prefix,String number,int prefixDigit){
        String key=redis_key_prefix+prefix;
        String num=number.replace(prefix,"");
        //如果不是整数，则抛出异常
        if(!NumberUtil.isInteger(num)){
            throw new RuntimeException("编号必须是整数");
        }
        //计数器值
        long incy=Integer.valueOf(num);
        //原始redis里的值
        String originalValue=redisUtils.get(key);
        originalValue= StringUtils.isEmpty(originalValue)?"0":originalValue;
        //原始redis值常规校验
        if(!NumberUtil.isInteger(originalValue)){
            throw new RuntimeException("编号必须是整数");
        }
        //如果比原始值小，则计数器更新为原始值
        if(incy<=Integer.valueOf(originalValue)){
            incy=Integer.valueOf(originalValue);
            //throw new RuntimeException("更新的值比原始值小！");
        }
        //设置原始值
        boolean ret=redisUtils.set(key,incy);
        if(ret) {
            //刷新计数器
            incy=redisUtils.incr(key,1);
        }
        //返回结果
        if(String.valueOf(incy).length()<prefixDigit){
            //高位补齐
            return prefix+String.format("%0"+prefixDigit+"d", incy);
        }
        return prefix+incy;
    }


}
