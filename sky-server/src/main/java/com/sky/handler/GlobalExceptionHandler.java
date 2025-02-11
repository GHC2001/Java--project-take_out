package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }


    /**
     * 捕获SQL异常
     * @param ex
     * autor lianghx
     * date 2025/02/10 上午9:56
     * */
    //java.sql.SQLIntegrityConstraintViolationException: Duplicate entry
    //'zhangsan' for key 'employee.idx_username'
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
        String message = ex.getMessage();
        if (message.contains("Duplicate entry")){
            String[] magSplit = message.split(" ");
            String username = magSplit[2];
            return Result.error(username + MessageConstant.ALREADY_EXIST);
        }else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }
}
