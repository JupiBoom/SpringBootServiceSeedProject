package com.zyd.springbootserviceseedproject.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 密码强度验证工具类
 */
public class PasswordValidator {

    // 密码强度正则表达式：至少8位，包含大写字母、小写字母、数字和特殊字符
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    /**
     * 验证密码强度
     * @param password 密码
     * @return 是否符合强度要求
     */
    public static boolean validatePasswordStrength(String password) {
        if (!StringUtils.hasText(password)) {
            return false;
        }
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * 获取密码强度要求提示
     * @return 密码强度要求提示
     */
    public static String getPasswordStrengthTips() {
        return "密码必须至少8位，包含大写字母、小写字母、数字和特殊字符(@$!%*?&)";
    }
}