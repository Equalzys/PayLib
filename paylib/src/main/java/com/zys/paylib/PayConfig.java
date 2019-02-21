package com.zys.paylib;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.zys.paylib.alipay.AlipayUtil;
import com.zys.paylib.wxpay.ParameterConfig;

public class PayConfig {

    private static final String TAG = "PayConfig";


    public static AliConfig alipay() {
        return new AliConfig();
    }

    public static class AliConfig {
        /**
         * appid
         */
        public AliInfo appId(String appid) {
            return new AliInfo(appid);
        }

        public class AliInfo {
            private String app_id;
            private String rsa_private;
            private String notify_url;
            private String intro;


            public AliInfo(String appid) {
                this.app_id = appid;
            }
            /**
             * 商户私钥，pkcs8格式,rsa2
             */
            public AliInfo rsa(String rsa_private) {
                this.rsa_private = rsa_private;
                return this;
            }

            /**
             * 自己服务器支付回调地址
             */
            public AliInfo notifyUrl(String notify_url) {
                this.notify_url = notify_url;
                return this;
            }

            /**
             * 支付宝窗口显示的订单信息：默认为   "订单-实际订单号"，'-实际订单号'为固定值
             * 这里可以修改 "订单"这两个字，修改为 "测试订单-实际订单号"
             */
            public AliInfo intro(String i) {
                this.intro = i;
                return this;
            }

            public void init() {
                AlipayUtil.setAPPID(app_id);
                AlipayUtil.setNotifyUrl(notify_url);
                AlipayUtil.setRsaPrivate(rsa_private);
                if (!TextUtils.isEmpty(intro)) {
                    AlipayUtil.setIntro(intro);
                }
            }
        }

    }

    public static WXPConfig wxpay() {
        return new WXPConfig();
    }

    public static class WXPConfig {
        /**
         * appid
         */
        public WXInfo appId(String appid) {
            return new WXInfo(appid);
        }

        public class WXInfo {
            private String app_id;

            private String mch_id;
            private String api_key;
            private String notify_url;
            private String intro;

            public WXInfo(String appid) {
                this.app_id = appid;
            }

            /**
             * 商户号
             */
            public WXInfo mchId(String mch_id) {
                this.mch_id = mch_id;
                return this;
            }

            /**
             * API密钥，在商户平台设置
             */
            public WXInfo apiKey(String api_key) {
                this.api_key = api_key;
                return this;
            }

            /**
             * 自己服务器支付回调地址
             */
            public WXInfo notifyUrl(String notify_url) {
                this.notify_url = notify_url;
                return this;
            }

            /**
             * 支付宝窗口显示的订单信息：默认为   "订单-实际订单号"，'-实际订单号'为固定值
             * 这里可以修改 "订单"这两个字，修改为 "测试订单-实际订单号"
             */
            public WXInfo intro(String i) {
                this.intro = i;
                return this;
            }

            public void init() {
                ParameterConfig.setWxAppId(app_id);
                ParameterConfig.setWxMchId(mch_id);
                ParameterConfig.setWX_notifyUrl(notify_url);
                ParameterConfig.setWxApiKey(api_key);
                if (!TextUtils.isEmpty(intro)) {
                    ParameterConfig.setIntro(intro);
                }

            }
        }
    }

}
