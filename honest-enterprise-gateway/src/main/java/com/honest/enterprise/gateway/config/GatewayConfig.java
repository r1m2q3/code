package com.honest.enterprise.gateway.config;



import com.honest.enterprise.gateway.vo.SysGatewayConfig;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {

        RouteLocatorBuilder.Builder routes=builder.routes();
        List<SysGatewayConfig> ret = new ArrayList<>();
        SysGatewayConfig data=new SysGatewayConfig();
        data.setAppName("honest-enterprise-user");
        data.setPrefix("api/user");
        ret.add(data);


        if(!CollectionUtils.isEmpty(ret)){
            for(SysGatewayConfig item:ret){
                routes=routes.route(item.getAppName(), r -> r.path("/"+item.getPrefix()+"/**")
                        .filters(f ->f.stripPrefix(2).hystrix(ff->ff.setFallbackUri("forward:/defaultfallback")))
                        .uri("lb://"+item.getAppName()));
            }
        }

        //websocket 网关配置demo
        String appName="honest-enterprise-user";
        String prefix="websocket/"+appName;
        routes=routes.route(appName, r -> r.path("/"+prefix+"/**")
                .filters(f ->f.stripPrefix(2).hystrix(ff->ff.setFallbackUri("forward:/defaultfallback")))
                .uri("lb:ws://"+appName));



       return routes.build();
    }


}
