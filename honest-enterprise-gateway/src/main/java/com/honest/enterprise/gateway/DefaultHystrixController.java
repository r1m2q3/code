package com.honest.enterprise.gateway;



import com.honest.enterprise.core.http.HttpResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 默认降级熔断处理
 */
@RestController
public class DefaultHystrixController {
    @RequestMapping("/defaultfallback")
    public HttpResult<Void> defaultfallback(){
        HttpResult<Void> ret=new HttpResult<>(HttpResult.SERVICE_FUSING.getRetCode(),HttpResult.SERVICE_FUSING.getRetMsg());

        return ret;

    }
}
