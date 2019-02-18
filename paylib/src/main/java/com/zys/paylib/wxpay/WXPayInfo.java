package com.zys.paylib.wxpay;

import java.io.Serializable;


public class WXPayInfo implements Serializable {
    private String productname;
    private double totalamount;

    public WXPayInfo(String productname, double totalamount) {
        this.productname = productname;
        this.totalamount = totalamount;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public double getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(double totalamount) {
        this.totalamount = totalamount;
    }
}
