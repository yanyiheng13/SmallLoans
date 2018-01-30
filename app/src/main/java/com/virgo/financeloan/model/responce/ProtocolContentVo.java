package com.virgo.financeloan.model.responce;

import lombok.Data;

/**
 * 功能说明：协议内容返回字段
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/13 16:45
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
@Data
public class ProtocolContentVo {
    /**
     * 协议内容(Html模板).
     */
    private String template;
}
