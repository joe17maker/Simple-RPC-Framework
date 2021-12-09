package com.rpc.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Xiong Nie
 * @date 2021/12/8
 */
@AllArgsConstructor
@Getter
public enum PackageType {
    REQUEST_PACKAGE(0),
    RESPONSE_PACKAGE(1);
    private int type;
}
