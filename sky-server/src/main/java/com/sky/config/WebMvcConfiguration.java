package com.sky.config;

import com.sky.interceptor.JwtTokenAdminInterceptor;
import com.sky.interceptor.JwtTokenUserInterceptor;
import com.sky.json.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

/**
 * configure class, register web layer components
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;
    @Autowired
    private JwtTokenUserInterceptor jwtTokenUserInterceptor;

    /**
     * register customized filter
     *
     * @param registry
     */
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("start to register customized filter...");
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/employee/login");
        registry.addInterceptor(jwtTokenUserInterceptor)
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/user/login")
                .excludePathPatterns("/user/shop/status");
    }

    /**
     * generate admin interfaces document with knife4j
     * @return
     */
    @Bean
    public Docket docketAdmin() {
        log.info("ready to generate interface document..");
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("sky_project_interface_doc")
                .version("2.0")
                .description("sky_project_interface_doc")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("admin interfaces")
                .apiInfo(apiInfo)
                .select()
                // the directory we need to scan
                .apis(RequestHandlerSelectors.basePackage("com.sky.controller.admin"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    /**
     * generate user interfaces document with knife4j
     * @return
     */
    @Bean
    public Docket docketUser() {
        log.info("ready to generate interface document..");
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("sky_project_interface_doc")
                .version("2.0")
                .description("sky_project_interface_doc")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("user interfaces")
                .apiInfo(apiInfo)
                .select()
                // the directory we need to scan
                .apis(RequestHandlerSelectors.basePackage("com.sky.controller.user"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }


    /**
     * configure static resources reflection
     * @param registry
     */
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * extend MVC message convert
     * @param converters
     */

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("extend message converter.. ");
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new JacksonObjectMapper());
        // add customized converter into converters list. set priority to first;
        converters.add(0, converter);
    }
}
