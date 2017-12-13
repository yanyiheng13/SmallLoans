package com.virgo.financeloan.model.responce;

import java.io.Serializable;

/**
 * 功能说明：
 *
 * @author: Yiheng Yan
 * @Email: yanyiheng86@163.com
 * @date: 17-8-24 上午12:16
 * @Copyright (c) 2017. Inc. All rights reserved.
 */

public class BaseBean<T> implements Serializable {

    public String message;
    public String code;
    public String result;
    public T data;

    public boolean isSuccess(){
        return "0".equals(code);
    }

}
