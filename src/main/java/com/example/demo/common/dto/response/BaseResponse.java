package com.example.demo.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {
    protected Integer code;
    protected String message;

    public static BaseResponse badRequest() {
        return new BaseResponse(HttpStatus.BAD_REQUEST.value(), "잘못된 파라미터 입니다.");
    }

    public static BaseResponse badRequest(String resultMsg) {
        return new BaseResponse(HttpStatus.BAD_REQUEST.value(), resultMsg);
    }

    public static BaseResponse internalServerError() {
        return new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "알 수 없는 에러 입니다.");
    }

    public static BaseResponse internalServerError(String resultMsg) {
        return new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), resultMsg);
    }

    public static BaseResponse unauthorized() {
        return new BaseResponse(HttpStatus.UNAUTHORIZED.value(), "사용할 수 없는 토큰입니다.");
    }
}
