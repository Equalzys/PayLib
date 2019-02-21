package com.zys.paylib.pay;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.zys.paylib.PayConfig;
import com.zys.paylib.alipay.AlipayUtil;
import com.zys.paylib.wxpay.ParameterConfig;
import com.zys.paylib.wxpay.WXPayInfo;
import com.zys.paylib.wxpay.WXPayUtil;


public class PayTools {
//    private IPay iPay;

//    public PayTools(IPay iPay) {
//        this.iPay = iPay;
//    }
//
//    public void pay(Activity activity, String paynumbe, double price,
//                    AlipayUtil.AlipayCallBack callback) {
//        this.iPay.pay(activity, paynumbe, price, callback);
//    }

    public static AlipayTool aliPay(Activity activity) {
        return new AlipayTool(activity);
    }

    public static class AlipayTool {
        private Activity activity;
        private String trade_number;
        private String intro;
        private double money;
        private AlipayUtil.AlipayCallBack callBack;


        /**
         * @param activity activity
         */
        public AlipayTool(Activity activity) {
            this.activity = activity;
        }

        /**
         * @param trade_number 三方预支付订单号
         */
        public AlipayTool tradeNumber(String trade_number) {
            this.trade_number = trade_number;
            return this;
        }

        /**
         * @param intro 支付宝窗口显示的订单信息：默认为   "订单-实际订单号"，'-实际订单号'为固定值
         *              这里可以修改 "订单-"这两个字，修改为 "测试订单-实际订单号"
         *              非必填
         */
        public AlipayTool intro(String intro) {
            this.intro = intro;
            return this;
        }

        /**
         * @param money 实付金额>=0
         **/
        public AlipayTool money(double money) {
            this.money = money;
            return this;
        }

        /**
         * @param callBack 支付宝交易结果回调
         */
        public void pay(AlipayUtil.AlipayCallBack callBack) {
            this.callBack = callBack;
            if (null==activity) {
                throw new NullPointerException("Activity为空");
            }
            if (TextUtils.isEmpty(trade_number)) {
                throw new NullPointerException("预支付订单号trade_number为空");
            }
            if (money <= 0) {
                throw new NullPointerException("支付金额不能小于0");
            }
            if (null == callBack) {
                throw new NullPointerException("支付回调callBack为空");
            }
            AlipayUtil.getInstance().pay(activity, trade_number, intro, money + "", callBack);
        }

    }

    public static WXPayTool wxPay(Activity activity) {
        return new WXPayTool(activity);
    }

    public static class WXPayTool {
        private Activity activity;
        private String trade_number;
        private String intro;
        private double money;

        /**
         * @param activity activity
         */
        public WXPayTool(Activity activity) {
            this.activity = activity;
        }

        /**
         * @param trade_number 三方预支付订单号
         */
        public WXPayTool tradeNumber(String trade_number) {
            this.trade_number = trade_number;
            return this;
        }

        /**
         * @param intro 支付宝窗口显示的订单信息：默认为   "订单-实际订单号"，'-实际订单号'为固定值
         *              这里可以修改 "订单-"这两个字，修改为 "测试订单-实际订单号"
         *              非必填
         */
        public WXPayTool intro(String intro) {
            this.intro = intro;
            return this;
        }

        /**
         * @param money 实付金额>=0
         */
        public WXPayTool money(double money) {
            this.money = money;
            return this;
        }

        public void pay() {
            if (null == activity) {
                throw new NullPointerException("Activity为空");
            }
            if (TextUtils.isEmpty(trade_number)) {
                throw new NullPointerException("预支付订单号trade_number为空");
            }
            if (money <= 0) {
                throw new NullPointerException("支付金额不能小于0");
            }
            if (TextUtils.isEmpty(intro)) {
                new WXPayUtil(activity, new WXPayInfo(ParameterConfig.getIntro(), money),
                        trade_number);
            } else {
                new WXPayUtil(activity, new WXPayInfo(intro, money), trade_number);
            }
        }
    }


}
