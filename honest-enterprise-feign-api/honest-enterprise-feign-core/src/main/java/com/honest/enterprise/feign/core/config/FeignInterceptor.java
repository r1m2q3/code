package com.honest.enterprise.feign.core.config;


import com.honest.enterprise.core.auth.AuthContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeignInterceptor implements RequestInterceptor {


  @Override
  public void apply(RequestTemplate template) {
//    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
//        .getRequestAttributes();
//    HttpServletRequest request = attributes.getRequest();
//    Enumeration<String> headerNames = request.getHeaderNames();
      String token = AuthContext.getUserName();
      template.header("hetoken",token);
  }
}
