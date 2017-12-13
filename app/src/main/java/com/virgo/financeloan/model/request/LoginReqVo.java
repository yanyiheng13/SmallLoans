package com.virgo.financeloan.model.request;

/**
 * 功能说明：登录请求
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-11-26
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class LoginReqVo {
    /**
     * 用户名
     */
    public String phone;
    /**
     * 密码
     */
    public String password;
    /**
     * IP地址
     */
    public String ipAddress;
    /**
     * 设备码
     */
    public String imei;

    private LoginReqVo(){};
    public LoginReqVo(String phone, String password, String ipAddress, String imei) {
        this.phone = phone;
        this.password = password;
        this.ipAddress = ipAddress;
        this.imei = imei;
    }
}
