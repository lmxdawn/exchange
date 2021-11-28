package com.lmxdawn.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@EnableOpenApi
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        return new Docket(
                // 设置使用 OpenApi 3.0 规范
                DocumentationType.OAS_30)
                .pathMapping("/admin") // 解决gateway使用swagger的问题
                // 是否开启 Swagger
                .enable(true)
                // 配置项目基本信息
                .apiInfo(apiInfo())
                // 选择那些路径和api会生成document
                .select()
                // 对所有api进行监控
                .apis(RequestHandlerSelectors.any())
                // 如果需要指定对某个包的接口进行监控，则可以配置如下
                //.apis(RequestHandlerSelectors.basePackage("com.lmxdawn.user.controller"))
                // 对所有路径进行监控
                .paths(PathSelectors.any())
                // 忽略以"/error"开头的路径,可以防止显示如404错误接口
                .paths(PathSelectors.regex("/error.*").negate())
                // 忽略以"/actuator"开头的路径
                .paths(PathSelectors.regex("/actuator.*").negate())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> apiKeyList= new ArrayList<>();
        apiKeyList.add(new ApiKey("x-auth-token", "x-auth-token", "header"));
        return apiKeyList;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts=new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .build());
        return securityContexts;
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences=new ArrayList<>();
        securityReferences.add(new SecurityReference("x-auth-token", authorizationScopes));
        return securityReferences;
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                // 文档标题
                .title("后台管理服务接口文档")
                // 文档描述
                .description("后台管理服务")
                // 文档版本
                .version("1.0")
                // 设置许可声明信息
                .license("Apache LICENSE 2.0")
                // 设置许可证URL地址
                .licenseUrl("https://github/my-dlq")
                // 设置管理该API人员的联系信息
                .contact(new Contact("lmxdawn", "作者URL", "lmxdawn@qq.com"))
                .build();
    }

}
