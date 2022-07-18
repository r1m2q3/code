package com.honest.enterprise.feign.core.utils;

import com.alibaba.fastjson.JSON;
import com.honest.enterprise.core.http.HttpResult;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @Author: fanjie
 * @Date: 2021-04-26 10:31:18
 */
@Slf4j
public final class ConvertHttpResultUtils {

/*    private static final Integer SUCCESS_CODE =   HttpResult.success(ResponseResultData.SUCCESS.setNewData(null));
   */

    /**
     * 私有化构造器，防止实例
     */
    private ConvertHttpResultUtils() {
    }

    /**
     * 获取feign调用结果
     *
     * @param resultData
     * @param <T>
     * @return
     */
    public static <T> T checkAndGetHttpResult(HttpResult<T> resultData) {
        log.info("request feign result data: {}", JSON.toJSONString(resultData));
      /*  if (Objects.nonNull(resultData) && "200".equals(resultData.getRetCode()) && Objects.nonNull(resultData.getData())) {
            return resultData.getData();
        }*/
        return null;
    }


    /**
     * 转换Long值
     *
     * @param id
     * @return
     */
    public static Long convertLongValue(String id) {
        Long value = null;
        try {
            value = Long.valueOf(id);
        } catch (NumberFormatException e) {
            return value;
        }
        return value;
    }

    public static String convertStringValue(Long id) {
        if (id == null) {
            return null;
        }
        String value = null;
        try {
            value = String.valueOf(id);
        } catch (NumberFormatException e) {
            return value;
        }
        return value;
    }
}
