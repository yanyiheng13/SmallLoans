package com.virgo.financeloan.model.request;

import lombok.Data;

/**
 * <P>Description: 查询上传资料列表请求Vo. </P>
 * <P>CALLED BY:   齐霞飞 </P>
 * <P>UPDATE BY:   齐霞飞 </P>
 * <P>CREATE DATE: 2017/12/6</P>
 * <P>UPDATE DATE: 2017/12/6</P>
 *
 * @author qixiafei
 * @version 1.0
 * @since java 1.7.0
 */
@Data
public class QueryUploadFileReqVo {

    /**
     *  贷款订单号.
     */
    private String loanOrderNo;
}
