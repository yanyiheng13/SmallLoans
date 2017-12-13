package com.virgo.financeloan.model.responce;

import lombok.Data;

/**
 * 功能说明：协议对象
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/13 15:53
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
@Data
public class ProtocolVo {
    /**
     * 协议编号.
     */
    private String code;
    /**
     * 协议名称.
     */
    private String name;
    /**
     * 协议保存路径.
     */
    private String path;
    /**
     * 协议版本号.
     */
    private String version;
}
