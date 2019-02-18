package com.zys.paylib.pay;

import android.app.Activity;

import com.zys.paylib.alipay.AlipayUtil;
import com.zys.paylib.wxpay.ParameterConfig;
import com.zys.paylib.wxpay.WXPayInfo;
import com.zys.paylib.wxpay.WXPayUtil;


public class WxPay implements IPay {

    @Override
    public void pay(Activity activity, String paynumbe, double price, AlipayUtil.AlipayCallBack callback) {
        new WXPayUtil(activity, new WXPayInfo(ParameterConfig.getIntro(), price), paynumbe);
    }
}
