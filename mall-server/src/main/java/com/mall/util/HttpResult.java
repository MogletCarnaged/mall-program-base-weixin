package com.mall.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HttpResult {
    private Integer code;
    private String msg;
    private Object data;

    HttpResult(Integer code) {
        this.code = code;
    }
    HttpResult(Integer code,Object data) {
        this.code = code;
        this.data = data;
    }
    HttpResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    HttpResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static HttpResult success() {
        return new HttpResult(200);
    }
    public static HttpResult success(Object data) {
        return new HttpResult(200, data);
    }
    public static HttpResult success(String msg) {
        return new HttpResult(200, msg);
    }
    public static HttpResult success(String msg, Object data) {
        return new HttpResult(200, msg, data);
    }
}
