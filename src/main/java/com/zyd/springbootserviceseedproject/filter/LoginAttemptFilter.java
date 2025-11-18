package com.zyd.springbootserviceseedproject.filter;

import com.zyd.springbootserviceseedproject.cache.RedisCache;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 登录尝试限制过滤器
 * 防止暴力枚举突破登录
 */
@Component
public class LoginAttemptFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    // 最大登录失败次数
    private static final int MAX_ATTEMPTS = 5;
    // 锁定时间（分钟）
    private static final int LOCK_TIME = 15;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 只处理登录请求
        if ("/user/login".equals(request.getRequestURI()) && "POST".equals(request.getMethod())) {
            // 获取用户名（账号）
            String username = request.getParameter("no");
            // 如果参数中没有，从请求体中获取
            if (!StringUtils.hasText(username)) {
                // 这里可以根据实际情况从请求体中解析
                // 简单处理，先放行
                filterChain.doFilter(request, response);
                return;
            }

            // 检查用户是否被锁定
            String lockKey = "login:lock:" + username;
            String lockValue = redisCache.getCacheObject(lockKey);
            if (StringUtils.hasText(lockValue) && "LOCKED".equals(lockValue)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":403,\"message\":\"登录失败次数过多，账号已锁定，请15分钟后重试\"}");
                return;
            }

            // 放行，继续处理登录
            filterChain.doFilter(request, response);
        } else {
            // 非登录请求直接放行
            filterChain.doFilter(request, response);
        }
    }

    /**
     * 记录登录失败次数
     * @param username 用户名
     * @return 是否锁定账号
     */
    public boolean recordFailedAttempt(String username) {
        String attemptKey = "login:attempt:" + username;
        String lockKey = "login:lock:" + username;

        Integer attempts = redisCache.getCacheObject(attemptKey);
        if (attempts == null) {
            // 第一次失败，设置失败次数为1，过期时间为15分钟
            redisCache.setCacheObject(attemptKey, 1, LOCK_TIME, TimeUnit.MINUTES);
            return false;
        } else if (attempts < MAX_ATTEMPTS - 1) {
            // 失败次数未达上限，次数加1
            redisCache.setCacheObject(attemptKey, attempts + 1, LOCK_TIME, TimeUnit.MINUTES);
            return false;
        } else {
            // 失败次数达上限，锁定账号
            redisCache.setCacheObject(lockKey, "LOCKED", LOCK_TIME, TimeUnit.MINUTES);
            // 清除失败次数记录
            redisCache.deleteObject(attemptKey);
            return true;
        }
    }

    /**
     * 清除登录失败记录
     * @param username 用户名
     */
    public void clearFailedAttempts(String username) {
        String attemptKey = "login:attempt:" + username;
        String lockKey = "login:lock:" + username;
        redisCache.deleteObject(attemptKey);
        redisCache.deleteObject(lockKey);
    }
}
