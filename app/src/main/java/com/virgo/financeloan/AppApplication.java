package com.virgo.financeloan;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.virgo.financeloan.model.responce.UserData;
import com.virgo.financeloan.net.Repository;
import com.virgo.financeloan.util.SharePrefrenceUtil;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;


public class AppApplication extends Application {

    public static AppApplication mApplication;
    private static UserData mUserData;

    @Setter
    @Getter
    private String webContent = "<!doctype html>\n" +
            "<html>\n" +
            "<head>\n" +
            "    <script type=\"text/javascript\" data-main=\"/static/js/page/audit/loanAudit\"  src=\"/static/js/modules/require.js\"></script>\n" +
            "    <style>\n" +
            "        .s_one label{float:none;display: inline-block;}\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div class=\"container\">\n" +
            "    <div class=\"content\">\n" +
            "        <h2 style=\"text-align: center;\" class=\"aduit_title\"><span class=\"displayName_title\"></span><span class=\"op_tip\"></span></h2>\n" +
            "        <div class=\"search border\">\n" +
            "            <input type=\"hidden\" class=\"loanApplyNo\" value=\"$!{loanAuditDetail.loanApplyNo}\"/>\n" +
            "            <input type=\"hidden\" class=\"userAccountId\" value=\"$!{loanAuditDetail.userAccountId}\"/>\n" +
            "            <div class=\"s_one\">\n" +
            "                <label for=\"\">借款订单号：</label>\n" +
            "                <span class=\"totalAmount\">$!{loanAuditDetail.loanApplyNo}</span>\n" +
            "            </div>\n" +
            "            <div class=\"clear\"></div>\n" +
            "            <div class=\"s_one\">\n" +
            "                <label for=\"\">合同信息：</label>\n" +
            "                <span class=\"displayName\">\n" +
            "                    #foreach ($contract in $loanAuditDetail.fileInfos)\n" +
            "                        <a href=\"javascript:void(0);\" fileUploadPath=\"$!{contract.fileAddress}\" class=\"contractInfo\" style=\"width:100px;cursor:pointer\">$!{contract.fileName}</a>\n" +
            "                    #end\n" +
            "                </span>\n" +
            "            </div>\n" +
            "            <div class=\"clear\"></div>\n" +
            "            <div class=\"s_one\">\n" +
            "                <label for=\"\">受托支付金额：</label>\n" +
            "                <span class=\"totalAmount\">$!BigDecimalUtils.roundingAnd2validData($!{loanAuditDetail.loanAmt})</span>元\n" +
            "            </div>\n" +
            "            <div class=\"clear\"></div>\n" +
            "            <div class=\"s_one\">\n" +
            "                <label for=\"\">客户姓名：</label>\n" +
            "                <span class=\"totalAmount\">$!{loanAuditDetail.userAccountName}</span>\n" +
            "            </div>\n" +
            "            <div class=\"clear\"></div>\n" +
            "            <div class=\"s_one\">\n" +
            "                <label for=\"\">客户身份证号：</label>\n" +
            "                <span class=\"marketId\">$!{loanAuditDetail.idCardNo}</span>\n" +
            "            </div>\n" +
            "            <div class=\"clear\"></div>\n" +
            "            <div class=\"s_one\">\n" +
            "                <label for=\"\">收款账户名称：</label>\n" +
            "                <span class=\"tagID\">$!{loanAuditDetail.loanAccountName}</span>\n" +
            "            </div>\n" +
            "            <div class=\"clear\"></div>\n" +
            "            <div class=\"s_one\">\n" +
            "                <label for=\"\">收款账号：</label>\n" +
            "                <span class=\"tagID\">$!{loanAuditDetail.loanAccount}</span>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "        <div class=\"border\">\n" +
            "            <form class=\"op-form\" id=\"aduit-form\">\n" +
            "                <div class=\"s_one\">\n" +
            "                    <label for=\"\">审核意见：</label>\n" +
            "                    <label class=\"lable\"><input type=\"radio\" class=\"pass\" name=\"pass\" value=\"201\">通过</label>\n" +
            "                    <label class=\"lable\"><input type=\"radio\" class=\"pass\" name=\"pass\" value=\"202\">拒绝</label>\n" +
            "                    #if(${isDataUpload})\n" +
            "                        <label class=\"lable\"><input type=\"radio\" class=\"pass\" name=\"pass\" value=\"102\">审核退回</label>\n" +
            "                    #end\n" +
            "                </div>\n" +
            "                <div class=\"clear\"></div>\n" +
            "                <div class=\"s_one\">\n" +
            "                    <label for=\"\" style=\"float:left\">备注：</label>\n" +
            "                    <textarea class=\"text decisionView\"></textarea>\n" +
            "                </div>\n" +
            "                <div class=\"clear\"></div>\n" +
            "                <div class=\"s_one\">\n" +
            "                    <div class=\"s_btn submit_btn\">提交</div>\n" +
            "                    <div class=\"s_btn cancel_btn\">取消</div>\n" +
            "                </div>\n" +
            "            </form>\n" +
            "            <div class=\"clear\"></div>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "\n" +
            "    <!-- 显示Base64图片的popup begin-->\n" +
            "    <div class=\"pop\" id=\"image-base64\" style=\"display:none;\">\n" +
            "        <div class=\"pop_bg\"></div><!--遮罩-->\n" +
            "        <div class=\"pop_box\">\n" +
            "            <h2 class=\"pop_title\">影像信息</h2>\n" +
            "            <div >\n" +
            "                <img class=\"middleImage\"  src=\"\" alt=\"没有找到图片\" />\n" +
            "            </div>\n" +
            "            <div class=\"s_one center-wrapper mar20\" style=\"margin-top:30px;\">\n" +
            "                <div class=\"s_btn pop_close\" style=\"width: 200px;\">关闭</div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
//        String user = SharePrefrenceUtil.getString("config", "user_info");
        Repository.get().setOnDynamicParameterListener(new Repository.OnDynamicParameterListener() {
            @Override
            public HashMap<String, String> headers() {
                return null;
            }

            @Override
            public HashMap<String, String> commonParam() {
//                HashMap<String, String>  hashMap = new HashMap();
//                hashMap.put("source", "android");
//                hashMap.put("token", isLogin() ? mUserData.token : "");
//                hashMap.put("uid", isLogin() ? mUserData.uid : "");
                return null;
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(base);
    }

    public static boolean isLogin() {
        return getUserData() != null && !TextUtils.isEmpty(mUserData.token);
    }

    public static UserData getUserData() {
        if (mUserData == null) {
            String userData = SharePrefrenceUtil.getString("user", "logindata");
            if (TextUtils.isEmpty(userData) || userData.length() < 10) {
                return null;
            }
            mUserData = new Gson().fromJson(userData, new TypeToken<UserData>() {
            }.getType());
        }
        return mUserData;
    }

    public static void setUserData(UserData userData) {
        mUserData = userData;
    }
}
