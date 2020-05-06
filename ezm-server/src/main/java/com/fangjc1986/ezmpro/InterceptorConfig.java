package com.fangjc1986.ezmpro;


import com.fangjc1986.ezmpro.intercepor.EveryInterceptor;
import com.fangjc1986.ezmpro.intercepor.UserAuthorizationInterceptor;
import com.fangjc1986.ezmpro.intercepor.SignInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public UserAuthorizationInterceptor userAuthorizationInterceptor() {
        return new UserAuthorizationInterceptor();
    }


    @Bean
    public SignInterceptor signInterceptor() {
        return new SignInterceptor();
    }

    @Bean
    public EveryInterceptor everyInterceptor() {
        return new EveryInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(everyInterceptor())
            .addPathPatterns("/**");

        registry.addInterceptor(userAuthorizationInterceptor())
            .addPathPatterns("/**")
            .excludePathPatterns("/common/**")
            .excludePathPatterns("/access/**")
            .excludePathPatterns(
                "/**/*.html", "/**/*.json", "/**/*.css",
                "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg",
                "/**/*.ttf", "/swagger-resources/**", "/v2/*",
                "/webjars/**", "/error"
            );

        registry.addInterceptor(signInterceptor())
            .addPathPatterns("/access/**")
            .excludePathPatterns("/common/**");
    }


}
