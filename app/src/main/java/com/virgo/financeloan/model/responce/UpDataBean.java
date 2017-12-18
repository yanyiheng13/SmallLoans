package com.virgo.financeloan.model.responce;

import lombok.Data;

/**
 * Created by Hulai-10 on 2017/12/18.
 */
@Data
public class UpDataBean {
    public String message;
    public String code;
    public String result;
    public String data;

    public boolean isSuccess(){
        return "0".equals(code);
    }
}
