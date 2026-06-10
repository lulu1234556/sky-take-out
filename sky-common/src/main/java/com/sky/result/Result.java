package com.sky.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 后端统一返回结果
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {
    private Integer code; //编码：1成功，其他：失败
    private String msg;
    private T data;

    public static <T> Result<T> success(){
        Result<T> result=new Result<T>();
        result.code=1;
        return result;
    }
//有些接口返回成功需要数据，有些不需要
    public static <T> Result<T> success(T object){
        Result<T> result=new Result<T>();
        result.data=object;
        result.code=1;
        return result;
    }

    public static <T> Result<T> error(String msg){
        Result<T> result=new Result<T>();
        result.msg=msg;
        result.code=0;
        return result;
    }
}
