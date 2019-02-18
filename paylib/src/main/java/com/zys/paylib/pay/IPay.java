package com.zys.paylib.pay;

import android.app.Activity;

import com.zys.paylib.alipay.AlipayUtil;


public interface IPay {
    void pay(Activity activity, String paynumbe, double price, AlipayUtil.AlipayCallBack callback);
}
