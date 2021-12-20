package top.naccl.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Nebula
 * @date 2021/12/18 10:01
 * @description: Swagger接口测试配置类
 *
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 是否启用swagger
                .enable(true)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo())
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 采用注解的方式确定需要显示的接口（在Controller上加api注解）
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                // 采用包扫描的方式确定需要显示的接口
                //.apis(RequestHandlerSelectors.basePackage("top/naccl/controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 接口文档头
                .title("Swagger接口文档")
                // 接口文档描述
                .description("Swagger相关接口的文档")
                // 项目版本号
                .version("1.0")
                .build();
    }
}
