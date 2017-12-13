package com.virgo.financeloan.util;

/**
 * 银行卡号
 */
public class BankCardUtil {
    /**
     * 銀行卡號校驗
     * @param cardNo
     * @return
     */
    public static boolean isValidCRC(String cardNo){
        if (cardNo == null) {
            return false;
        }

        cardNo = cardNo.replace(" ", "");
        int length = cardNo.length();
        if (length < 13 || length > 19) {
            return false;
        }

        int sum = 0;
        char c_num;
        for (int i = length - 2, j = 0; i >= 0; i--, j++) {
            c_num = cardNo.charAt(i);
            if (!Character.isDigit(c_num)) {
                // 非数字
                return false;
            }

            int num = c_num - '0';
            // 右边第一个数字开始每隔一位乘2，超過10則兩位相加
            if (j % 2 == 0) {
                num = num * 2;
                num = num / 10 + num % 10;
            }
            sum += num;
        }
        // 计算校验位(总和个位数的10的补数)
        char crc = (sum % 10 == 0) ? '0' : (char) (10 - sum % 10 + '0');

        return crc == cardNo.charAt(length - 1);
    }
}
