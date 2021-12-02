package com.rpc;

import lombok.Data;

/**
 * @描述 rpc的返回
 * @创建人 Xiong Nie
 * @创建时间 2021/11/30
 * @修改人和其它信息
 */
@Data
public class Response {
    private int code = 0;//返回的编码，0-成功，非0失败
    private String message = "ok";//描述错误信息或者成功信息
    private Object data;//返回的信息
}
