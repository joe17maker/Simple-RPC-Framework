package com.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @描述 rpc请求
 * @创建人 Xiong Nie
 * @创建时间 2021/11/30
 * @修改人和其它信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request implements Serializable {
    private ServiceDescriptor service;
    private Object[] parameters;
}
