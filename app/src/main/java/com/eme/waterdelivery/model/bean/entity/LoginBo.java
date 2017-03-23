package com.eme.waterdelivery.model.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dijiaoliang on 17/3/20.
 */

public class LoginBo implements Parcelable {


    /**
     * cname : 狄娇亮
     * img :
     * ordersSumMonth : 2
     * ordersSumToday : 0
     * ordersSumTotal : 3
     * paidAmount : 370
     * sig : ND8QQ7FVK457RR356Y1EU24
     * storeId : 5
     * storePhone : 1580018974
     * username : djl
     */

    private String cname;
    private String img;
    private int ordersSumMonth;
    private int ordersSumToday;
    private int ordersSumTotal;
    private String paidAmount;
    private String sig;
    private String storeId;
    private String storePhone;
    private String username;

    public LoginBo() {
    }

    protected LoginBo(Parcel in) {
        cname = in.readString();
        img = in.readString();
        ordersSumMonth = in.readInt();
        ordersSumToday = in.readInt();
        ordersSumTotal = in.readInt();
        paidAmount = in.readString();
        sig = in.readString();
        storeId = in.readString();
        storePhone = in.readString();
        username = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cname);
        dest.writeString(img);
        dest.writeInt(ordersSumMonth);
        dest.writeInt(ordersSumToday);
        dest.writeInt(ordersSumTotal);
        dest.writeString(paidAmount);
        dest.writeString(sig);
        dest.writeString(storeId);
        dest.writeString(storePhone);
        dest.writeString(username);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LoginBo> CREATOR = new Creator<LoginBo>() {
        @Override
        public LoginBo createFromParcel(Parcel in) {
            return new LoginBo(in);
        }

        @Override
        public LoginBo[] newArray(int size) {
            return new LoginBo[size];
        }
    };

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getOrdersSumMonth() {
        return ordersSumMonth;
    }

    public void setOrdersSumMonth(int ordersSumMonth) {
        this.ordersSumMonth = ordersSumMonth;
    }

    public int getOrdersSumToday() {
        return ordersSumToday;
    }

    public void setOrdersSumToday(int ordersSumToday) {
        this.ordersSumToday = ordersSumToday;
    }

    public int getOrdersSumTotal() {
        return ordersSumTotal;
    }

    public void setOrdersSumTotal(int ordersSumTotal) {
        this.ordersSumTotal = ordersSumTotal;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
