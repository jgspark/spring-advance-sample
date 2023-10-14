package com.webtoon.coding.core.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

import static com.webtoon.coding.core.resolver.UserIdentifier.HEADER_KEY;

public class UserIdentifierInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        var userId = Optional.ofNullable(request.getHeader(HEADER_KEY)).orElseThrow();
        MDC.put(HEADER_KEY, userId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        MDC.clear();
    }

}
