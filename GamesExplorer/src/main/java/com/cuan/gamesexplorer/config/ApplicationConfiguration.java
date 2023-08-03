package com.cuan.gamesexplorer.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.Nullable;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class ApplicationConfiguration {
    private final Logger log = LoggerFactory.getLogger(ApplicationConfiguration.class);
    @Bean
    public String SuccessMessage() {
        return "Success! The request was processed successfully.";
    }
    @Bean
    @Profile("dev,qa") //Don't use this in production, because it will incur a small performance penalty
    public FilterRegistrationBean<OncePerRequestFilter> executionTimeLoggingFilter() {
        return new FilterRegistrationBean<>() {{
            setOrder(OrderedFilter.REQUEST_WRAPPER_FILTER_MAX_ORDER);
            setFilter(new OncePerRequestFilter() {

                @Override
                protected void doFilterInternal(@Nullable HttpServletRequest req,@Nullable HttpServletResponse res,
                                                @Nullable FilterChain chain) throws ServletException, IOException {
                    StopWatch watch = new StopWatch();
                    watch.start();
                    try {
                        assert chain != null;
                        chain.doFilter(req, res);
                    }finally {
                        watch.stop();
                        assert req != null;
                        log.info("REQUEST: {} completed within {} ms",
                                getUriWithMethodAndQuery(req), watch.getTotalTimeMillis());
                    }
                }

                private String getUriWithMethodAndQuery(HttpServletRequest req) {
                    return req.getMethod() + ": " + req.getRequestURI() +
                            (StringUtils.hasText(req.getQueryString()) ? "?" + req.getQueryString() : "");
                }
            });
        }};
    }
}
