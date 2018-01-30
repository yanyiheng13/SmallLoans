package com.virgo.financeloan.model.responce;

import lombok.Data;

/**
 * 功能说明：还款科目信息
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/14
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
@Data
public class RepayRecordVo {
    /**
     * 科目描述.
     */
    private String subjectDesc;
    /**
     * 科目金额，单位分.
     */
    private String subjectAmount;
}
