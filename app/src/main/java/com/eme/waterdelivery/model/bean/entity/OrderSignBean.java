package com.eme.waterdelivery.model.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 订单签收实体
 *
 * Created by dijiaoliang on 17/5/3.
 */
public class OrderSignBean implements Parcelable {

    private String orderId;//订单id
    private String payType;//付款方式
    private String orderAmount;//订单总金额

    public OrderSignBean() {
    }

    public OrderSignBean(Parcel in) {
        orderId = in.readString();
        payType = in.readString();
        orderAmount = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeString(payType);
        dest.writeString(orderAmount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderSignBean> CREATOR = new Creator<OrderSignBean>() {
        @Override
        public OrderSignBean createFromParcel(Parcel in) {
            return new OrderSignBean(in);
        }

        @Override
        public OrderSignBean[] newArray(int size) {
            return new OrderSignBean[size];
        }
    };

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

}
