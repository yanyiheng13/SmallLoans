package com.virgo.financeloan.model.request;

import lombok.Data;

/**
 * 功能说明：申请贷款明细请求model
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 17-11-26
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
@Data
public class LoanApplyReqVo {
    /**
     * 借款订单号.
     */
    private String loanOrderNo;

    /**
     * 还款方式序列号.
     */
    private String repaymentWaySerialNumber;
    /**
     * 分期号.
     */
    private String agingNo;
    /**
     * 期数.
     */
    private String aging;
    /**
     * 产品基类编号.
     */
    private String productBaseNo;

    /**
     * 产品大类编号.
     */
    private String productTypeOne;

    /**
     * 借款金额，单位分.
     */
    private String loanAmt;

    /**
     * 对公对私.
     */
    private String loanAccountCategory;

    /**
     * 借款用途编码.
     */
    private String loanPurposeCode;

    /**
     * 借款用途描述.
     */
    private String loanPurposeDesc;

    /**
     * 绑卡id
     */
    private String bindId;

    /**
     * 参考月利率.
     */
    private String refMonthRate;
    /**
     * 设备ip.
     */
    private String sourceIp;
    /**
     * 设备指纹会话标识，非必传
     */
    private String fingerprintMark;

    /**
     * 是否上传资料.
     * 0-否，1-是
     */
    private String uploadFile;

}
