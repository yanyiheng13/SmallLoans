package com.virgo.financeloan.model.responce;

import java.io.Serializable;

/**
 * 功能说明：
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-9-8
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class Waybill implements Serializable {

    public String waybillId	;	//运单id
    public String goodsName	;	//货物名称
    public String goodsLoad	;	//货物重量
    public String waybilltime	;	//运单时间
    public String amount	;	//运单金额(单位分)
    public String status	;	//运单的状态(1.进行中 2已完成)

    public Address startAddress;
    public Address destination;
}
