package com.jdk.projectinterface.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 捕获全局异常
 */
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NullPointerException.class)
    public Object NullPointExceptionH(){
        return ServiceResponse.createFailResponse("空指针异常");
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Object SQLIntegrityConstraintViolationExceptionH(){
        return ServiceResponse.createFailResponse("SQL完整性约束违反异常");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Object illegalArgumentExceptionH(){
        return ServiceResponse.createFailResponse("非法参数异常");
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public Object IndexOutOfBoundsExceptionH(){
        return ServiceResponse.createFailResponse("索引越界异常");
    }

    @ExceptionHandler(Exception.class)
    public Object exceptionH(){
        return ServiceResponse.createFailResponse("异常申请");
    }

}
