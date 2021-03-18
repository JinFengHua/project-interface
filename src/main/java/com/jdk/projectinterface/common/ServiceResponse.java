package com.jdk.projectinterface.common;

import com.jdk.projectinterface.utils.Utils;
import lombok.Data;

import java.io.Serializable;

@Data
public class ServiceResponse <T> implements Serializable{
    private static final int RESPONSE_SUCCESS = 200;
    private static final int RESPONSE_EMPTY = 204;
    private static final int RESPONSE_FAIL = 401;
    
    private int code;
    private String message;
    private T data;

    /**
     * 创建一个请求失败的response
     * @param msg 请求失败返回的消息
     * @return ServiceResponse
     */
    public static  <T> ServiceResponse<T>  createFailResponse(String msg){
        ServiceResponse<T> response = new ServiceResponse<>();
        response.setCode(RESPONSE_FAIL);
        response.setMessage(msg);
        response.setData(null);
        return response;
    }

    /**
     * 请求成功，但查询结果为空的ServiceResponse
     * @param msg 请求为空，返回的消息
     * @return ServiceResponse
     */
    public static  <T> ServiceResponse<T>  createEmptyResponse(String msg){
        ServiceResponse<T> response = new ServiceResponse<>();
        response.setCode(RESPONSE_EMPTY);
        response.setMessage(msg);
        response.setData(null);
        return response;
    }

    /**
     * 请求成功，返回相应data
     * @param msg 返回成功的消息
     * @param object 成功时的data数据
     * @return ServiceResponse
     */
    @SafeVarargs
    public static  <T> ServiceResponse<T>  createResponse(String msg, T... object){
        ServiceResponse<T> response = new ServiceResponse<>();
        response.setCode(RESPONSE_SUCCESS);
        response.setMessage(msg);
        if (object.length < 1){
            response.setData(null);
        } else {
            response.setData(object[0]);
        }
        return response;
    }

    public static <T>ServiceResponse<T> backFailResponse(String msgOnEmpty,String msgOnSuccess,T object){
        ServiceResponse<T> response;
        if (Utils.isEmpty(object)){
            response = ServiceResponse.createEmptyResponse(msgOnEmpty);
            return response;
        } else {
            response = ServiceResponse.createResponse(msgOnSuccess,object);
            return response;
        }
    }
}
