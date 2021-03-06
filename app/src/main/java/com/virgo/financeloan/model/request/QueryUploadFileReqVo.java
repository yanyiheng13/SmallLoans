package com.virgo.financeloan.model.request;

import lombok.Data;

/**
 * 功能说明：上传资料列表
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 17-12-19
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
@Data
public class QueryUploadFileReqVo {

    /**
     *  贷款订单号.
     */
    private String loanOrderNo;
}
