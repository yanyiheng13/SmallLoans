package com.virgo.financeloan.model.request;


import lombok.Data;
/**
 * 功能说明：
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-11-26
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
@Data
public class UploadFileReqVo {


    /**
     * 贷款订单号.
     */
    private String loanOrderNo;

    /**
     * 文件类型码.
     *
     */
    private String fileType;

    /**
     * 操作类型.
     *
     */
    private String operateType;

    /**
     * 如果同样的文件类型有多张，新增的时候需要给一个顺序.
     */
    private String order;

}
