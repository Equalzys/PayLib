package com.zys.paylib.pay;

import android.app.Activity;

import com.zys.paylib.alipay.AlipayUtil;


public class AliPay implements IPay {
    @Override
    public void pay(Activity activity, String paynumbe, double price,
                    AlipayUtil.AlipayCallBack callback) {
        AlipayUtil.getInstance().pay(activity, paynumbe, price + "", callback);
    }
}
