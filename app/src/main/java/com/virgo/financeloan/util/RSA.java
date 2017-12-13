package com.virgo.financeloan.util;

import android.util.Log;

import android.util.Base64;
import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * <P>Description: RSA签名,加解密处理核心文件，注意：密钥长度1024 </P>
 * <P>CALLED BY:   齐霞飞 </P>
 * <P>UPDATE BY:   齐霞飞 </P>
 * <P>CREATE DATE: 2013-11-15</P>
 * <P>UPDATE DATE: 2017/6/28</P>
 *
 * @author qixiafei
 * @version 0.0.3-SNAPSHOT
 * @since java 1.7.0
 */
class RSA {

    /**
     * 签名算法
     */
    private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    /**
     * 加密算法RSA
     */
    private static final String KEY_ALGORITHM = "RSA";
    /**
     * 1024密钥RSA加密最大分段大小.
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * 1024密钥RSA解密最大分段大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * Rsa公钥，给别人提供.
     */
    private static final String RSA_PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCGdOxalt/5zCMXxDueXPeHb42/1M03WCNV2gKyt6TFj6VVr1bFxRYHXz3R6vxne+7ayaA56IuXE8mM5zKlrsUjKSoEORqmvWZ78It2R+yqN1FSE0PjB62fhfbXIcxCUxrDuBvSRG12A7PhPKxR1ekWpVPPhZTo33vgNG2Czg4mgQIDAQAB";

    /**
     * Rsa私钥，不要给别人看哦.
     */
    private static final String RSA_PRI_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIZ07FqW3/nMIxfEO55c94dvjb/UzTdYI1XaArK3pMWPpVWvVsXFFgdfPdHq/Gd77trJoDnoi5cTyYznMqWuxSMpKgQ5Gqa9Znvwi3ZH7Ko3UVITQ+MHrZ+F9tchzEJTGsO4G9JEbXYDs+E8rFHV6RalU8+FlOjfe+A0bYLODiaBAgMBAAECgYB2RYNibDS4VojH7yVCOCTETawygc3CURVD/3FAI6oc3i5hIMsDTEoEUhLfBATiQnHoolL+nT+stQsllnlGpGVjLniqz3D4G5k7/xRbKcG3K8Hbf362yQM10iWDuoDJkAEYCEU/a3GqLVq3eSxYaB/vTcHKUbq09kQwMZ82ZTKJAQJBANbV7eCnXmXcFlGpVUtAtfLYUqFQ97DUZsbAm7U/I3pXQSbFvBMndYOHkTxifHUkAn+9mr75yiDd5mfQ5RpZsA0CQQCgOEPIYDUH9N66KmoJZ2KNHl/bRgWqSkU6b8IROsDXb/0FBVZrmixqRHLezlaaACkwPVr8cOKzkDvsO4YFqL9FAkBhomexKV8lO5iJFEPHJ2mJK/ahiYKHwn/S48Xf4Qnml7DsRgDrU4t8cyf2IWwecHcSRsEE+dB6j14e4Di+ePQhAkEAhRpZDWUHiWWfhw9nagqOb+gVAEgquq7a1lIz6jZiMFgj3gDyK5A4Z8wXlgvbHDMYBLBkHyX03/HqJjXJAHiQ9QJAHV5+9yYQ+prmSEuDpvFKvlxfyslsryh3Y2n4jI4eLqs2m1B1Y5KxYkjetgtoqozg9UjRQfRfg6mcRmtZkWcCbA==";

    /**
     * Rsa私钥对象.
     */
    private static RSAPrivateKey privateKey;

    static {
        try {
            privateKey = getPrivateKey();
        } catch (Exception e) {
            Log.d("debug","Rsa private key init failed");
        }
    }

    /**
     * 用私钥分段解密.
     *
     * @param encryptedData 已加密字节流
     * @return 明文字节流
     */
    static byte[] decryptByPrikey(final byte[] encryptedData) throws Exception {
        // 初始化key对象
        if (privateKey == null) {
            privateKey = getPrivateKey();
        }
        // 初始化密码操作器
        final Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        // 分段处理
        return segmentDeal(cipher, MAX_DECRYPT_BLOCK, encryptedData);
    }

    /**
     * 公钥分段加密.
     *
     * @param data     源数据字节流
     * @param pbKeyStr 别人的公钥(BASE64编码)
     */
    static byte[] encryptByPublicKey(byte[] data, String pbKeyStr) throws Exception {
        // 初始化key对象
        final RSAPublicKey publicK = getPublicKey(pbKeyStr);
        // 初始化密码操作器
        final Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        // 分段处理
        return segmentDeal(cipher, MAX_ENCRYPT_BLOCK, data);
    }

    /**
     * 分段处理.
     *
     * @param cipher       密码操作对象
     * @param segmentLengh 分段大小
     * @param data         数据字节流
     * @return 处理后的字节流
     */
    private static byte[] segmentDeal(final Cipher cipher, final int segmentLengh, final byte[] data) throws Exception {
        final int inputLen = data.length;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            // 对数据分段加密
            for (int offSet = 0; offSet < inputLen; offSet += segmentLengh) {
                final byte[] cache;
                if (inputLen >= segmentLengh + offSet) {
                    cache = cipher.doFinal(data, offSet, segmentLengh);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
            }
            return out.toByteArray();
        }
    }

    /**
     * 通过Rsa公钥字符串获取公钥对象.
     *
     * @param publicKey 公钥字符串
     * @return 公钥对象
     * @throws NoSuchAlgorithmException 没有这个算法异常
     * @throws InvalidKeySpecException  公钥非法异常
     */
    private static RSAPublicKey getPublicKey(final String publicKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        return (RSAPublicKey) KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(Base64.decode(publicKey, Base64.DEFAULT)));
    }


    /**
     * 通过Rsa私钥字符串获取私钥对象.
     *
     * @return 私钥对象
     * @throws NoSuchAlgorithmException 没有这个算法异常
     * @throws InvalidKeySpecException  公钥非法异常
     */
    private static RSAPrivateKey getPrivateKey()
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        return (RSAPrivateKey) KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(RSA_PRI_KEY, Base64.DEFAULT)));
    }


    /**
     * 签名字符串
     *
     * @param text       需要签名的字符串
     * @param privateKey 私钥(BASE64编码)
     * @return 签名结果(BASE64编码)
     */
    static String sign(String text, String privateKey, String charset) throws Exception {

        byte[] keyBytes = Base64.decode(privateKey, Base64.DEFAULT);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(getContentBytes(text, charset));
        byte[] result = signature.sign();
        return Base64.encodeToString(result, Base64.DEFAULT);

    }

    /**
     * 使用指定字符集将内容转成字节流.
     *
     * @param content 内容
     * @param charset 字符集名字
     * @return 字节流
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

}
