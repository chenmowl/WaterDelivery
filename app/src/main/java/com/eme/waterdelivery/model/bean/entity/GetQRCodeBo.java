package com.eme.waterdelivery.model.bean.entity;

/**
 * 拉取微信支付二维码（getQRCode）
 *
 * Created by dijiaoliang on 17/4/28.
 */
public class GetQRCodeBo {


    private String qrCode;//二维码地址

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
