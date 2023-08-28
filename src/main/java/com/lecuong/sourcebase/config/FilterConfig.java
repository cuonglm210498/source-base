package com.lecuong.sourcebase.config;

import com.lecuong.sourcebase.filter.ApiKeyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author CuongLM
 * @created 28/08/2023 - 10:07 PM
 * @project source-base
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<ApiKeyFilter> loggingFilter() {
        FilterRegistrationBean<ApiKeyFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ApiKeyFilter());
        registrationBean.addUrlPatterns("/api-key"); // Set URL patterns to which the filter should apply
        return registrationBean;
    }

}
