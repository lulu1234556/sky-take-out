package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.info("异常信息：{}",ex.getMessage());
        String message=ex.getMessage();
        if(message.contains("Duplicate entry")){
            String[] split=message.split(" ");
            String name=split[2];
            return Result.error(name+ MessageConstant.ALREADY_EXIST);
        }
        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }
}
