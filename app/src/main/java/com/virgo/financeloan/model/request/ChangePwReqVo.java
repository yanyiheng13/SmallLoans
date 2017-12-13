package com.virgo.financeloan.model.request;

/**
 * 功能说明：修改密码请求参数
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-11-26
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */

public class ChangePwReqVo {
    /**
     * 原密码,Rsa加密.
     */
    private String oldPwd;

    /**
     * 修改后密码，Rsa加密.
     */
    private String newPwd;

    /**
     * 验证码.
     */
    private String verifyCode;

    private ChangePwReqVo(){};

    public ChangePwReqVo(String oldPwd, String newPwd, String verifyCode) {
        this.oldPwd = oldPwd;
        this.newPwd = newPwd;
        this.verifyCode = verifyCode;
    }
}
