package com.honest.enterprise.config.config;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description:
 * @Author: fanjie
 * @Date: 2022-07-17 18:14:31
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String TOKEN_DESC = "用户token";
    private static final String STR = "string";
    private static final String HEADER = "header";

    @Value("${swagger.tokenName:hetoken}")
    private  String tokenName;

    @Bean
    public Docket api() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name(tokenName).description(TOKEN_DESC)
                .modelRef(new ModelRef(STR)).parameterType(HEADER)
                //header中的ticket参数非必填，传空也可以
                .required(false).build();
        //根据每个方法名也知道当前方法在设置什么参数
        pars.add(ticketPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                //.apis(RequestHandlerSelectors.basePackage("com.**.facade"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiEndPointsInfo())
                .globalOperationParameters(pars)
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("REST API")
                .description("REST API")
                //.contact(new Contact("zjm", "https://gede.com", "zjm@dingtalk.com"))
                .version("V1")
                .build();
    }
}