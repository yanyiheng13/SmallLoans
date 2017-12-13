package com.virgo.financeloan.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Enumeration;

/**
 * 一些通用的类
 * @author: huajie
 * @version: 1.0
 * @date: 2017/1/24
 * @email: huajie@le.com
 * @Copyright (c) 2017. le.com Inc. All rights reserved.
 */
public class CommonUtil {

    /**
     * 获取银行卡号后四位
     *
     * @param cardNumber
     * @return
     */
    public static String getCardEndNumber(String cardNumber) {
        if (!TextUtils.isEmpty(cardNumber) && cardNumber.length() >= 4) {
            return cardNumber.substring(cardNumber.length() - 4);
        }
        return cardNumber;
    }

    /**
     * 格式化金额
     *
     * @param amount
     * @param length 小数点后保留位数，如果为-1，小数点后有几位就显示几位(最大2位)，
     * @return amount为空用0补位
     */
    public static String amountFormat(String amount, int length) {
        if (TextUtils.isEmpty(amount)) {
            if (length == 0 || length == -1) {
                return "0";
            }
            return "0." + suffix("", length, "0");
        }

        NumberFormat format = null;
        if (length == 0) {
            format = new DecimalFormat("###,###,##0");
        } else if (length == -1) {
            format = new DecimalFormat("###,###,##0.##");
        } else {
            StringBuffer buff = new StringBuffer();
            buff.append("###,###,##0.");
            for (int i = 0; i < length; i++) {
                buff.append("0");
            }
            format = new DecimalFormat(buff.toString());
        }
        return format.format(Double.parseDouble(amount));
    }

    /**
     * 目前此规则使用范围为产品相关，不适用于个人资产相关
     * 需要特别注意
     * 1.传入参数单位为分
     * 2.转化为以元为单位后当小数部分为零，格式化后为整数
     * 3.转化为以元为单位后当小数部分不为零，格式化后保留两位小数
     * 4.格式化后含有千分为“,”因此切勿拿返回数据直接做运算
     *
     * @param amount
     * @return
     */
    public static String formatAmountByAutomation(String amount) {
        String keepTwo = formatAmountByKeepTwo(amount);//转化为元
        if (keepTwo.endsWith(".00")) {
            return keepTwo.replace(".00", "");
        }
        return keepTwo;
    }

    /**
     * 传入参数单位为分，保留两位小数
     * 格式化后含有千分为“,”因此切勿拿返回数据直接做运算
     *
     * @param amount
     * @return
     */
    public static String formatAmountByKeepTwo(String amount) {
        return amountFormat(centToYuan(amount), 2);
    }

    /**
     * 传入参数单位为分，保留整数
     * 格式化后含有千分为“,”因此切勿拿返回数据直接做运算
     *
     * @param amount
     * @return
     */
    public static String formatAmountByInteger(String amount) {
        return amountFormat(centToYuan(amount), 0);
    }

    /**
     * 格式化。千位分割。保留整数
     *
     * @param amount
     * @return
     */
    public static String amountFormat(String amount) {

        return amountFormat(amount, 0);
    }

    /**
     * 字符串key后面补足到length长度
     *
     * @param key
     * @param length
     * @param val
     * @return
     */
    public static String fillSuffix(String key, int length, String val) {
        StringBuilder builder = new StringBuilder(key == null ? "" : key);
        if (builder.length() > length) {
            return builder.toString();
        }
        int i = 0;
        while (i < length) {
            builder.append(val);
            i++;
        }
        return builder.toString();
    }

    /**
     * 分转元
     *
     * @param amount
     * @return
     */
    public static String centToYuan(String amount) {
        if (TextUtils.isEmpty(amount)) {
            return "0";
        }
        BigDecimal a = new BigDecimal(amount);
        return a.divide(new BigDecimal("100")).toString();
    }

    /**
     * 元转分
     *
     * @param amount
     * @return
     */
    public static String yuanToCent(String amount) {
        if (TextUtils.isEmpty(amount)) {
            return "0";
        }
        BigDecimal a = new BigDecimal(amount);
        return String.valueOf(a.multiply(new BigDecimal("100")).longValue());
    }

    /**
     * 字符串后面补足到length长度
     *
     * @param key
     * @param length
     * @param val
     * @return
     */
    public static String suffix(String key, int length, String val) {
        StringBuilder builder = new StringBuilder(key == null ? "" : key);
        if (builder.length() > length) {
            return builder.toString();
        }
        int i = 0;
        while (i < length) {
            builder.append(val);
            i++;
        }
        return builder.toString();
    }

    /**
     * 利率格式化
     *
     * @param amount
     * @param length 小数点后保留位数，如果为-1，小数点后有几位就显示几位，
     * @return
     */
    public static String rateFormat(String amount, int length) {
        if (TextUtils.isEmpty(amount)) {
            if (length == 0 || length == -1) {
                return "0";
            }
            return "0." + fillSuffix("", length, "0");
        }

        NumberFormat format = null;
        if (length == 0) {
            format = new DecimalFormat("########0");
        } else if (length == -1) {
            format = new DecimalFormat("########0.##");
        } else {
            StringBuffer buff = new StringBuffer();
            buff.append("########0.");
            for (int i = 0; i < length; i++) {
                buff.append("0");
            }
            format = new DecimalFormat(buff.toString());
        }
        return format.format(Double.parseDouble(amount));
    }

    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        if (TextUtils.isEmpty(deviceId)) {
            return "----";
        } else {
            return deviceId;
        }
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

}
