package com.virgo.financeloan.model.responce;

import java.util.List;

import lombok.Data;

/**
 * 功能说明： 银行卡信息
 *
 * @author： Yiheng Yan
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/13 10:47
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
@Data
public class CardData {
    /**
     * 用户绑定的银行卡信息
     */
    private List<CardVo> cardInfoList;
}
