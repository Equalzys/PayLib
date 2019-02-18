package com.zys.paylib.pay;

import android.app.Activity;

import com.zys.paylib.alipay.AlipayUtil;


public class PayTools {
    private IPay iPay;

    public PayTools(IPay iPay) {
        this.iPay = iPay;
    }
    public void pay(Activity activity, String paynumbe, double price, AlipayUtil.AlipayCallBack callback) {
        this.iPay.pay(activity, paynumbe, price, callback);
    }

}
