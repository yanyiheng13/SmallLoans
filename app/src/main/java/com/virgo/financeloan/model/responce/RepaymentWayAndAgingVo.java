package com.virgo.financeloan.model.responce;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 功能说明：还款对象
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-11-27
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
@Data
public class RepaymentWayAndAgingVo implements Serializable{
    /**
     * 还款方式序号
     */
    private String repaymentWaySerialNumber;
    /**
     * 还款方式编号.
     */
    private String repaymentWayNo;

    /**
     * 还款方式名称
     */
    private String repaymentWayName;

    /**
     * 还款方式描述.
     */
    private String repaymentDescribe;

    private List<AgingVo> agingInfoList;

}
