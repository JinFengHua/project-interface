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
    public Object NullPointExceptionCatch(NullPointerException e){
        return ServiceResponse.createFailResponse("空指针异常");
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Object SQLIntegrityConstraintViolationExceptionCatch(SQLIntegrityConstraintViolationException e){
        return ServiceResponse.createFailResponse("SQL完整性约束违反异常");
    }

}
