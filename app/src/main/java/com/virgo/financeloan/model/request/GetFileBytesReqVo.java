package com.virgo.financeloan.model.request;

import lombok.Data;

/**
 * 功能说明：文件详情请求参数
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 17-11-26
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
@Data
public class GetFileBytesReqVo  {

    /**
     * 文件路径.
     */
    private String path;
}
