package com.virgo.financeloan.model.responce;

import java.io.Serializable;

import lombok.Data;

/**
 * 功能说明：借款用途
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/14 16:51
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
@Data
public class LoanUsingVo implements Serializable {
    /**
     * 贷款用途编码.
     */
    private String loanPurposeCode;
    /**
     * 贷款用途描述.
     */
    private String loanPurposeDesc;
}
