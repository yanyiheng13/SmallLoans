package com.virgo.financeloan.model.responce;

import java.util.List;

import lombok.Data;

/**
 * 功能说明：贷款记录明细
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/14 15:47
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
@Data
public class LoanRecordDetailData {
    /**
     * 当前期数
     */
    private String currentPeriod;

    /**
     * 总期数
     */
    private String totalPeriod;

    /**
     * 已还金额，单位分.
     */
    private String repaidAmount;

    /**
     * 未还金额，单位分.
     */
    private String noRepayAmount;

    /**
     * 账单编号
     */
    private String bookedBillNum;
    /**
     * 账单金额,单位分.
     */
    private String billAmount;

    /**
     * 贷款账号.
     */
    private String loanAccountDesc;

    /**
     * 起止时间
     */
    private String startToEndDate;

    /**
     * 贷款用途
     */
    private String loanPurpose;

    /**
     * 协议列表
     */
    private List<ProtocolVo> contractInfoList;

}
