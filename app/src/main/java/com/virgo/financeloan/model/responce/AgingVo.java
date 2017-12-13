package com.virgo.financeloan.model.responce;

import java.io.Serializable;

import lombok.Data;

/**
 * 功能说明：还款期数对象
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-11-27
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
@Data
public class AgingVo implements Serializable {
    /**
     * 分期编号.
     */
    private String agingNo;

    /**
     * 分期数，如果为固定分期数必输.
     */
    private String aging;
}
