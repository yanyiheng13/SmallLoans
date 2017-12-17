package com.virgo.financeloan.model.responce;

import java.util.List;

import lombok.Data;

/**
 * 功能说明：还款计划列表
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/14 18:44
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
@Data
public class RepayPlanData {
    /**
     * 期数.
     */
    private String period;

    /**
     * 还款时间.
     */
//    private String repaymentDate;

    /**
     * 还款计划状态
     */
    private String status;

    /**
     * 总金额.
     */
    private String totalAmount;

    /**
     * 还款科目信息列表.
     */
    private List<RepayRecordVo> subjectList;
}
