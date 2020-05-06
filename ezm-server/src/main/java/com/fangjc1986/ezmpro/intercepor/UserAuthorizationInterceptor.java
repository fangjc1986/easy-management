package com.fangjc1986.ezmpro.intercepor;

import com.fangjc1986.ezmpro.sys.model.User;
import com.fangjc1986.ezmpro.sys.service.impl.RequestLogServiceImpl;
import com.fangjc1986.support.service.TokenService;
import com.fangjc1986.support.service.UserAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserAuthorizationInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserAuthorizationService userAuthorizationService;
    @Autowired
    private RequestLogServiceImpl requestLogService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = tokenService.stringToUser(request.getHeader("token"));
        requestLogService.saveByRequest();
        return userAuthorizationService.validateAuth(user);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
