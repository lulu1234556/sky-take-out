package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
/**
 * 登录成功后 返回给前端的数据
 */
public class EmployeeLoginVO implements Serializable {
    private Long id;
    private String userName;
    private String name;
    private String token;
}
