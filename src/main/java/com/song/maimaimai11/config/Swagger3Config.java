package com.song.maimaimai11.config;



import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * http://localhost:8080/swagger-ui/index.html 访问接口
 */
@Configuration
public class Swagger3Config {
    /**
     * use: 配置Swagger3
     * @return Docket
     */
    @Bean
    public Docket apiConfig(){
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * use：配置Swagger的信息
     * @return ApiInfo
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("宋宋接口文档")
                .description("本系统相关测试")
                .contact(new Contact("slf", "http://www.song.shop", "slf106639@gmail.com"))
                .version("1.0")
                .build();
    }
}