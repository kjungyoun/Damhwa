package com.ssafy.damhwa.common.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {

    Integer statusCode = null;
    String message = null;

    public BaseResponse(){}

    public BaseResponse(Integer statusCode){
        this.statusCode = statusCode;
    }

    public BaseResponse(Integer statusCode, String message){
        this.statusCode = statusCode;
        this.message = message;
    }

    public static BaseResponse of(Integer statusCode, String message){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.statusCode = statusCode;
        baseResponse.message = message;
        return baseResponse;
    }
}
