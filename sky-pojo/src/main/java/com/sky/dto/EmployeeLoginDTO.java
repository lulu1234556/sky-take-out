package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 前端登录传回的账号密码
 */
@Data
public class EmployeeLoginDTO implements Serializable {
    private String username;
    private String password;
}
