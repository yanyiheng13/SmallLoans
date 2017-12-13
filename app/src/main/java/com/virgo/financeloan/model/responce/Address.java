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
public class Address implements Serializable {

    public String province;	//非	String	省
    public String city;	//非	String	市
    public String county;	//非	String	县区
    public String provinceId;	//是	String
    public String cityId;	//是	String
    public String countyId;	//是	String
}
