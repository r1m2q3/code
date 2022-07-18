package com.honest.enterprise.gateway.utils;


import com.honest.enterprise.gateway.config.FilterProperties;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@EnableConfigurationProperties({FilterProperties.class})
public class WhiteList {

  @Autowired
  private FilterProperties filterProperties;

  public boolean isAllowPath(String path) {
    List<String> list = filterProperties.getAllowPaths();
    //遍历白名单
    for (String allowPath : list) {
      //判断是否允许
      if(allowPath.endsWith("/**") && allowPath.startsWith("/**")) {
          allowPath = allowPath.substring(3,allowPath.length() - 3);
          if(allowPath.contains(path)){
              return true;
          }
      } else if(allowPath.endsWith("/**")){
          allowPath = allowPath.substring(0,allowPath.length() - 3);
          if(path.startsWith(allowPath)){
            return true;
          } else if (allowPath.startsWith("/**")){
            allowPath = allowPath.substring(3);
            if(path.endsWith(allowPath)){
              return true;
            }
          }else {
            if(allowPath.startsWith(path)){
               return true;
            }
          }
      }
    }
    return  false;
  }

  @Test
  public void test(){
    String path2 = "/**/demo/**";
    path2 = path2.substring(3,path2.length() - 3);
    System.out.println(path2);

  }


}
