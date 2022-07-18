package com.honest.enterprise.core.browser;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

//连接池
@Slf4j
public class HttpClientPool {
  static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
  static  HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
    @Override
    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
      if (executionCount >= 3) {// 如果已经重试了3次，就放弃
        return false;
      }
      if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
        log.info("服务连接重试");
        return true;
      }
      if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
        log.info("不要重试SSL握手异常");
        return false;
      }
      if (exception instanceof InterruptedIOException) {// 超时
        log.info("超时");
        return false;
      }
      if (exception instanceof UnknownHostException) {// 目标服务器不可达
        log.info("目标服务器不可达");
        return false;
      }
      if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
        log.info("连接被拒绝");
        return false;
      }
      if (exception instanceof SSLException) {// SSL握手异常
        log.info("SSL握手异常");
        return false;
      }

      HttpClientContext clientContext = HttpClientContext.adapt(context);
      HttpRequest request = clientContext.getRequest();
      // 如果请求是幂等的，就再次尝试
        return !(request instanceof HttpEntityEnclosingRequest);
    }
  };

  /**
   * httpclient请求超时配置
   */
  static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20*1000).setConnectTimeout(2000).setConnectionRequestTimeout(1000).build();

  static {
    //设置最大连接数
    cm.setMaxTotal(50);
    //设置每个主机的最大连接数  同一个HOST最大连接数
    cm.setDefaultMaxPerRoute(20);
  }

  public static CloseableHttpClient getHttpclientPool() {
    return HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig).setRetryHandler(httpRequestRetryHandler).build();
  }



}
