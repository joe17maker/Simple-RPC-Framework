package com.rpc.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Xiong Nie
 * @date 2021/12/6
 */
@AllArgsConstructor
@Getter
public enum ResponseCode {
    SUCCESS(200,"success"),
    FAIL(500, "invoke failed"),
    METHOD_NOT_FOUND(500, "method not found"),
    CLASS_NOT_FOUND(500,"class not found");

    private final int code;
    private final String message;
}
