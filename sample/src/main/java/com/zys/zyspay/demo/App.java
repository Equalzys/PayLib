package com.zys.zyspay.demo;

import android.app.Application;

import com.zys.paylib.PayConfig;

public class App extends Application {

    private final String RSA_PRIVATE = "MIIEv****很长很长******stnwxntWIrgr/fvtXuyXSfI=";


    @Override
    public void onCreate() {
        super.onCreate();

        //用到支付宝的时候配置
        PayConfig.alipay()
                .appId("2017******3659")
                .rsa(RSA_PRIVATE)
                .notifyUrl("http://www.xx**xx.com/api/trade/notify/alipay")
                .intro("支付宝订单-")//非必填
                .init();

        //用到微信的时候配置
        PayConfig.wxpay()
                .appId("wx62*******87")
                .mchId("15******1")
                .apiKey("Rd88G******I0ci406")
                .notifyUrl("http://www.xx**xx.com/api/trade/notify/wxpay")
                .intro("微信订单-")//非必填
                .init();
    }
}
