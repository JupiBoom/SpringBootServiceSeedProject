package com.zyd.springbootserviceseedproject.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhaoyudong
 * @version 1.0
 * @description 用户表
 * @date 2025/9/23 20:14
 */
@Data
@TableName("user")
public class UserEntity implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    private String no;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    private String name;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private int age;
    private int sex;
    private String phone;
    private String isValid;

}
