package com.zys.paylib.wxpay;



public class ParameterConfig {
    // appid
    private static  String WX_APP_ID = "";
    // 商户号
    private static  String WX_MCH_ID = "";
    // API密钥，在商户平台设置
    private static  String WX_API_KEY = "";
    //自己服务器回调地址
    private static  String WX_notifyUrl = "";
    private static  String intro = "订单-";

    public static String getWxAppId() {
        return WX_APP_ID;
    }

    public static void setWxAppId(String wxAppId) {
        WX_APP_ID = wxAppId;
    }

    public static String getWxMchId() {
        return WX_MCH_ID;
    }

    public static void setWxMchId(String wxMchId) {
        WX_MCH_ID = wxMchId;
    }

    public static String getWxApiKey() {
        return WX_API_KEY;
    }

    public static void setWxApiKey(String wxApiKey) {
        WX_API_KEY = wxApiKey;
    }

    public static String getWX_notifyUrl() {
        return WX_notifyUrl;
    }

    public static void setWX_notifyUrl(String WX_notifyUrl) {
        ParameterConfig.WX_notifyUrl = WX_notifyUrl;
    }

    public static String getIntro() {
        return intro;
    }

    public static void setIntro(String i) {
        intro = i;
    }
}

