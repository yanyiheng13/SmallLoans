package com.virgo.financeloan.model.responce;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 功能说明： 小贷产品列表
 *
 * @author： Yiheng Yan
 * @email： yanyiheng@le.com
 * @version： 1.0
 * @date： 17-9-8
 * @Copyright (c) 2017. yanyiheng Inc. All rights reserved.
 */
@Data
public class LoanData implements Serializable {
  private List<LoanVo> list;
}
