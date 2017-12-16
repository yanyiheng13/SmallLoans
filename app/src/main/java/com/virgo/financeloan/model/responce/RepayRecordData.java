package com.virgo.financeloan.model.responce;

import java.util.List;

import lombok.Data;

/**
 * 功能说明：还款记录 还款计划数据
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/14 18:44
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
@Data
public class RepayRecordData {
    /**
     * 还款时间.
     */
    private String repayDate;

    /**
     * 还款金额，单位分.
     */
    private String repayAmount;

    /**
     * 还款科目信息列表.
     */
    private List<RepayRecordVo> subjectList;
}
