# PayLib
[![](https://jitpack.io/v/Equalzys/PayLib.svg)](https://jitpack.io/#Equalzys/PayLib)

支付宝支付，微信支付封装,使用简单方便
只用自己服务器返回 支付宝或微信的预支付订单号 即可完成支付

第一步：在项目根目录的 build.gradle 里添加jitpack库
```java
        allprojects {
		    repositories {
			    ...
			maven { url 'https://jitpack.io' }
		    }
	    }
```
  
第二步：在app的 build.gradle 里添加依赖
```java
        dependencies {
	        implementation 'com.github.Equalzys:PayLib:1.0.1'
	    }
```

第三步：初始化库（建议在application里初始化）
```java
        //用到支付宝的时候配置
        PayConfig.alipay()
                .appId("2017******3659")
                .rsa(RSA_PRIVATE)
                .notifyUrl("http://www.xx**xx.com/api/trade/notify/alipay")
                .intro("支付宝订单-")
                .init();

        //用到微信的时候配置
        PayConfig.wxpay()
                .appId("wx62*******87")
                .mchId("15******1")
                .apiKey("Rd88G******I0ci406")
                .notifyUrl("http://www.xx**xx.com/api/trade/notify/wxpay")
                .intro("微信订单-")
                .init();
```

第四步：在支付的地方调用pay，ps:微信支付需要在包名下添加 wxapi.WXPayEntryActivity
```java
        //支付宝支付
        String tradenumber = "这里是实际交易订单号-服务器返回";
        double money=0.01;//需要支付的金额
        PayTools payTools = new PayTools(new AliPay());
        payTools.pay(this, tradenumber, money, new AlipayUtil.AlipayCallBack() {
            @Override
            public void success(String tradenumber, String resultInfo) {
                //支付成功后操作
            }

            @Override
            public void fail(String resultInfo) {
              //支付失败后操作
            }

            @Override
            public void authFail() {
              //授权失败后操作
            }
        });
        
	
        //微信支付
        String tradenumber = "这里是实际交易订单号-服务器返回";
        double money=0.01;//需要支付的金额
        PayTools payTools = new PayTools(new WxPay());
        //支付回调在WXPayEntryActivity
        payTools.pay(this, tradenumber, money, null);

```
