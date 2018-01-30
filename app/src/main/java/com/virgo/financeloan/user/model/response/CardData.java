package com.virgo.financeloan.user.model.response;

import java.util.List;

import lombok.Data;

/**
 * 功能说明： 后端返回的银行卡列表信息
 *
 * @author： yanyiheng
 * @email： yanyiheng86@163.com
 * @version： 1.0
 * @date： 2017/12/13
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
@Data
public class CardData {
    /**
     * 用户绑定的银行卡信息
     */
    private List<CardVo> cardInfoList;
}
