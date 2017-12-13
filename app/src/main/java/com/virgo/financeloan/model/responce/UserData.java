package com.virgo.financeloan.model.responce;

import java.io.Serializable;

import lombok.Data;

/**
 * 功能说明： 登录接口数据
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-9-5
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
@Data
public class UserData implements Serializable {

    /**
     * 用户状态 1：正常，2：已注销
     */
    public String status;
    /**
     * 用户类型
     */
    public String userType;
    /**
     * 用户注册日期
     */
    public String regDate;
    /**
     * 最后登录时间，可为空
     */
    public String lastLoginTime;
    /**
     * 证件类型，常用的标准证件类型，可为空
     */
    public String userIdType;
    /**
     * 证件号码，可为空
     */
    public String userIdno;
    /**
     * 手机号
     */
    public String userPhone;
    /**
     * 是否是新用户,第一次登陆，1：是 0：否
     */
    public String changePwd;
    /**
     * 真实姓名
     */
    public String userRealName;
    /**
     * 用户名
     */
    public String userName;

    /**
     * 用户令牌.
     */
    public String token;
}
