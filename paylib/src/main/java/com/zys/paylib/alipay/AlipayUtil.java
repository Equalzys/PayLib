package com.zys.paylib.alipay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.zys.paylib.wxpay.ParameterConfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Random;


public class AlipayUtil {
    String TAG = "AlipayUtil";
    private static AlipayUtil mUtil;

    private static String APPID = "";
    //商户私钥，pkcs8格式
    private static String RSA_PRIVATE = "";
    //测试回调地址
    private static String NOTIFY_URL = "";
    private static String intro = "订单-";
    boolean rsa2 = true;
    private static final int SDK_PAY_FLAG = 1;
    private String OutTradeNo; //商户唯一支付订单号
    private static final int SDK_AUTH_FLAG = 2;


    public static AlipayUtil getInstance() {
        if (mUtil == null) {
            mUtil = new AlipayUtil();
        }
        return mUtil;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    // 同步返回需要验证的信息
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    Log.d(TAG, "resultInfo=" + resultInfo + "---resultStatus=" + resultStatus);
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        //支付成功
                        if (callback != null) {
                            callback.success(OutTradeNo, resultInfo);
                        }
                    } else {
                        //支付失败
                        if (callback != null) {
                            callback.fail(resultInfo);
                        }
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        Log.d(TAG, "授权成功");
                    } else {
                        Log.d(TAG, "授权失败");
                        if (callback != null) {
                            callback.fail("9000-授权失败");
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };


    public void pay(final Activity activity, String body, String price, AlipayCallBack callback) {

        pay(activity, "", body, price, callback);
    }

    public void pay(final Activity activity, String subject_start, String body, String price,
                    AlipayCallBack callback) {

        if (TextUtils.isEmpty(APPID) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(NOTIFY_URL)) {
            new AlertDialog.Builder(activity).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE|" +
                    " NOTIFY_URL\n请调用PayConfig进行初始化配置")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialoginterface, int i) {
                        }
                    }).show();
            return;
        }
        this.OutTradeNo = body;
        this.callback = callback;
        if (TextUtils.isEmpty(subject_start)) {
            subject_start = intro;
        }
        Map<String, String> params = OrderInfoUtil.buildOrderParamMap(APPID, body, price,
                subject_start + body, NOTIFY_URL, rsa2);
        String orderParam = OrderInfoUtil.buildOrderParam(params);

        String privateKey = RSA_PRIVATE;
        String sign = OrderInfoUtil.getSign(params, privateKey, rsa2);
        Log.d(TAG, "签名：" + sign);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
//                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }


    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);
        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE, false);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }

    private AlipayCallBack callback;

    public interface AlipayCallBack {
        /**
         * 支付成功
         */
        void success(String ordernumber, String resultInfo);

        /**
         * 支付失败
         */
        void fail(String resultInfo);

    }

    public static String getAPPID() {
        return APPID;
    }

    public static void setAPPID(String APPID) {
        AlipayUtil.APPID = APPID;
    }

    public static String getRsaPrivate() {
        return RSA_PRIVATE;
    }

    public static void setRsaPrivate(String rsaPrivate) {
        RSA_PRIVATE = rsaPrivate;
    }

    public static String getNotifyUrl() {
        return NOTIFY_URL;
    }

    public static void setNotifyUrl(String notifyUrl) {
        NOTIFY_URL = notifyUrl;
    }

    public static String getIntro() {
        return intro;
    }

    public static void setIntro(String i) {
        intro = i;
    }
}
