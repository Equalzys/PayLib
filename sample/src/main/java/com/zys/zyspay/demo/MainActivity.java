package com.zys.zyspay.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zys.paylib.alipay.AlipayUtil;
import com.zys.paylib.pay.AliPay;
import com.zys.paylib.pay.PayTools;
import com.zys.paylib.pay.WxPay;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    private void toAliPay() {
        String tradenumber = "这里是实际交易订单号-服务器返回";
        double money=0.01;//需要支付的金额
        PayTools payTools = new PayTools(new AliPay());
        payTools.pay(this, tradenumber, money, new AlipayUtil.AlipayCallBack() {
            @Override
            public void success(String tradenumber, String resultInfo) {

            }

            @Override
            public void fail(String resultInfo) {

            }

            @Override
            public void authFail() {

            }
        });

    }

    private void toWXPay() {
        String tradenumber = "这里是实际交易订单号-服务器返回";
        double money=0.01;//需要支付的金额
        PayTools payTools = new PayTools(new WxPay());
        //支付回调在WXPayEntryActivity
        payTools.pay(this, tradenumber, money, null);
    }
}
