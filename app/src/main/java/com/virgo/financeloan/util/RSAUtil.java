package com.virgo.financeloan.util;

import android.util.Base64;

/**
 * <P>Description: RSA工具类 </P>
 * <P>CALLED BY:   齐霞飞 </P>
 * <P>UPDATE BY:   齐霞飞 </P>
 * <P>CREATE DATE: 2017/5/27</P>
 * <P>UPDATE DATE: 2017/6/28</P>
 *
 * @author qixiafei
 * @version 0.0.3-SNAPSHOT
 * @since java 1.7.0
 */
public class RSAUtil {

    /**
     * 用公钥加密.
     *
     * @param src    原内容，明文
     * @param pubKey 公钥
     * @return 加密后的Base64字符串
     */
    public static String encryptByPubKey(String src, String pubKey) {

        try {
            byte[] bytes = RSA.encryptByPublicKey(src.getBytes("UTF-8"), pubKey);
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception e) {
//            log.error("RSA encrypt failure:src={}", src, e);
        }
        return null;
    }

    /**
     * 使用私钥解密.
     *
     * @param encryptedStr 已经加密的内容Base64字符串
     * @return 明文
     */
    public static String decryptByPriKey(String encryptedStr) {
        try {
            byte[] bytes = RSA.decryptByPrikey(Base64.decode(encryptedStr, Base64.DEFAULT));
            return new String(bytes, "UTF-8");
        } catch (Exception e) {
//            log.error("RSA decrypt failure:encryptedStr={}", encryptedStr, e);
        }
        return null;
    }

}
