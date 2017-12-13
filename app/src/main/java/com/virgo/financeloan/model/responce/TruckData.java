package com.virgo.financeloan.model.responce;

import java.io.Serializable;
import java.util.List;

/**
 * 功能说明：
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-9-7
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
public class TruckData implements Serializable {
    public List<TruckItem> truckList;
    public PageInfo pageInfo;
}
