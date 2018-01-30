package com.virgo.financeloan.util;

import com.virgo.financeloan.R;
import com.virgo.financeloan.user.model.response.CardVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 对银行卡信息的管理
 */
public class BankListUtil {

    private static HashMap<String, CardVo> INSTANCE = new HashMap<>();

    public static CardVo getBankObjInfo(String bankCode) {
        if (INSTANCE.size() <= 0) {
            putInforToMap();
        }
        CardVo bean = null;
        if (INSTANCE.containsKey(bankCode)) {
            bean = INSTANCE.get(bankCode);
        } else {
            bean = new CardVo();
        }
        return bean;
    }

    public static List<CardVo> getBankList() {
        if (INSTANCE.size() <= 0) {
            putInforToMap();
        }
        List<CardVo> cardVoList = new ArrayList<>();
        Iterator<Map.Entry<String, CardVo>> it = INSTANCE.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, CardVo> entry = it.next();
            cardVoList.add(entry.getValue());
        }
        return cardVoList;
    }

    /**
     * 此方法不能轻易修改，修改的时候要注意，避免出错
     */
    private static void putInforToMap() {
        CardVo beanBeijing = new CardVo("BCCB", "北京银行", 0xFFF16273, "1", R.mipmap.icon_bank_beijing);
        CardVo beanChina = new CardVo("BOC", "中国银行", 0xFFF16273, "1", R.mipmap.icon_bank_china);
        CardVo beanGongshang = new CardVo("ICBC", "工商银行", 0xFFF16273, "1", R.mipmap.icon_bank_gongshang);
        CardVo beanZhaoshang = new CardVo("CMB", "招商银行", 0xFFF16273, "1", R.mipmap.icon_bank_zhaoshang);
        CardVo beanGuangda = new CardVo("CEB", "广大银行", 0xFFCA6EC3, "1", R.mipmap.icon_bank_guangda);
        CardVo beanGuangfa = new CardVo("GDB", "广发银行", 0xFFF16273, "1", R.mipmap.icon_bank_guangfa);
        CardVo beanHuaxia = new CardVo("HXB", "华夏银行", 0xFFF16273, "1", R.mipmap.icon_bank_huaxia);
        CardVo beanJianshe = new CardVo("CCB", "建设银行", 0xFF3A96DF, "1", R.mipmap.icon_bank_jianshe);
        CardVo beanJiaotong = new CardVo("COMM", "交通银行", 0xFF3A96DF, "1", R.mipmap.icon_bank_jiaotong);
        CardVo beanMinsheng = new CardVo("CMBC", "民生银行", 0xFF3A96DF, "1", R.mipmap.icon_bank_minsheng);
        CardVo beanNongye = new CardVo("ABC", "农业银行", 0xFF03B996, "1", R.mipmap.icon_bank_nongye);
        CardVo beanPingan = new CardVo("SZPAB", "平安银行", 0xFFE48954, "1", R.mipmap.icon_bank_pingan);
        CardVo beanPufa = new CardVo("SPDB", "浦发银行", 0xFF3A96DF, "1", R.mipmap.icon_bank_pufa);
        CardVo beanXingye = new CardVo("CIB", "兴业银行", 0xFF3A96DF, "1", R.mipmap.icon_bank_xingye);
        CardVo beanZhongxin = new CardVo("CITIC", "中信银行", 0xFFF16273, "1", R.mipmap.icon_bank_zhongxin);
        CardVo beanYouChu = new CardVo("PSBC", "邮储银行", 0xFF1A8E39, "1", R.mipmap.icon_bank_youchu);

        INSTANCE.put("BCCB", beanBeijing);
        INSTANCE.put("BOC", beanChina);
        INSTANCE.put("ICBC", beanGongshang);
        INSTANCE.put("CMB", beanZhaoshang);
        INSTANCE.put("CEB", beanGuangda);
        INSTANCE.put("GDB", beanGuangfa);
        INSTANCE.put("HXB", beanHuaxia);
        INSTANCE.put("CCB", beanJianshe);
        INSTANCE.put("COMM", beanJiaotong);
        INSTANCE.put("CMBC", beanMinsheng);
        INSTANCE.put("ABC", beanNongye);
        INSTANCE.put("SZPAB", beanPingan);
        INSTANCE.put("SPDB", beanPufa);
        INSTANCE.put("CIB", beanXingye);
        INSTANCE.put("CITIC", beanZhongxin);
        INSTANCE.put("PSBC", beanYouChu);
    }

}
