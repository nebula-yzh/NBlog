package top.naccl.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nebula
 * @date 2021/12/18 10:01
 * @description: Swagger接口测试配置类
 * user 用户访问页面接口
 * admin 后台管理系统接口
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApiUser() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 是否启用swagger
                //.enable(true)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfoUser())
                .groupName("user")
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 采用注解的方式确定需要显示的接口（在Controller上加api注解）
                //.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                // 采用包扫描的方式确定需要显示的接口
                .apis(RequestHandlerSelectors.basePackage("top.naccl.controller.user"))
                .paths(PathSelectors.any())
                .build();
    }
    @Bean
    public Docket createRestApiAdmin() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 是否启用swagger
                //.enable(true)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfoAdmin())
                .groupName("Admin")
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 采用注解的方式确定需要显示的接口（在Controller上加api注解）
                //.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                // 采用包扫描的方式确定需要显示的接口
                .apis(RequestHandlerSelectors.basePackage("top.naccl.controller.admin"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(authAdminToken())
                .securityContexts(securityContexts());
    }
    private ApiInfo apiInfoAdmin() {
        return new ApiInfoBuilder()
                // 接口文档头
                .title("Swagger接口文档")
                // 接口文档描述
                .description("Swagger后台管理相关接口的文档")
                // 项目版本号
                .version("1.0")
                .build();
    }
    private ApiInfo apiInfoUser() {
        return new ApiInfoBuilder()
                // 接口文档头
                .title("Swagger接口文档")
                // 接口文档描述
                .description("Swagger用户访问相关接口的文档")
                // 项目版本号
                .version("1.0")
                .build();
    }
    private List<ApiKey> authAdminToken() {
        List list = new ArrayList();
        //需要与传到前端的请求头中的key对应
        list.add(new ApiKey("Authorization", "Authorization", "header"));
        return list;
    }
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts=new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!Auth).*$"))
                        .build());
        return securityContexts;
    }
    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences=new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }
}
