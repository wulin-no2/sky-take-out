package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.ExceptionConstants;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * global exception handler, handle exceptions in the project
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * catch exception
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("exception info：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * SQL exception handler
     * @param e
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException e){
        String message = e.getMessage();
        if (message.contains("Duplicate entry")){
            log.error("username duplicated.");
            String[] split = message.split(" ");
            String username = split[2];
            String msg = username + MessageConstant.ALREADY_EXIST;
            return Result.error(msg);
        }else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }
}
