package com.zyd.springbootserviceseedproject.handler;

import com.alibaba.fastjson.JSON;
import com.zyd.springbootserviceseedproject.common.Result;
import com.zyd.springbootserviceseedproject.filter.LoginAttemptFilter;
import com.zyd.springbootserviceseedproject.utils.WebUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author zhaoyudong
 * @version 1.0
 * @description 登录失败处理
 * @date 2025/9/24 14:00
 */
@Component
public class LoginFailureHandlerImpl implements AuthenticationFailureHandler {

    @Autowired
    private LoginAttemptFilter loginAttemptFilter;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 获取用户名（账号）
        String username = request.getParameter("no");
        if (username != null) {
            // 记录登录失败次数
            boolean locked = loginAttemptFilter.recordFailedAttempt(username);
            if (locked) {
                // 账号已锁定
                Result result = Result.fail(HttpStatus.FORBIDDEN.value(), "登录失败次数过多，账号已锁定，请15分钟后重试");
                String json = JSON.toJSONString(result);
                WebUtils.renderString(response, json);
                return;
            }
        }
        
        Result result = Result.fail(HttpStatus.UNAUTHORIZED.value(), "登录失败: " + exception.getMessage());
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response, json);
    }
}
