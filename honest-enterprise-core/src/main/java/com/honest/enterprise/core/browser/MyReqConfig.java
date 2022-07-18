package com.honest.enterprise.core.browser;

import org.apache.http.client.config.RequestConfig;

//设置请求参数
public class MyReqConfig {

  /**
   * //设置请求参数
   * @param connectTime  创建连接最长时间，毫秒
   * @param connectionRequestTime 设置获取连接的最长时间，毫秒
   * @param socketTime  设置数据传输的最长时间，毫秒
   * @return
   */
  public static RequestConfig getConfig(int connectTime,
                                        int connectionRequestTime,
                                        int socketTime){

    //还可以配置代理之类的，等等

    RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(connectTime)
        .setConnectionRequestTimeout(connectionRequestTime)
        .setSocketTimeout(socketTime).build();

    return config;
  }

}
