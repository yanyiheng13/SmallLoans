package com.virgo.financeloan.model.responce;

import java.io.Serializable;

import lombok.Data;

/**
 * 功能说明：
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/19 9:53
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
@Data
public class UploadFileVo implements Serializable{
    /**
     * 物理主键.
     */
    private String id;

    /**
     * 文件类型.
     */
    private String fileType;

    /**
     * 文件名称.
     */
    private String fileName;

    /**
     * 文件地址.
     */
    private String fileAdrress;

    /**
     * 若同文件类型的有多个，这个代表顺序.
     */
    private String order;
}
