package com.eme.waterdelivery.model.bean.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 订单签收页面的item实体
 *
 * Created by dijiaoliang on 17/5/3.
 */
public class BucketBean implements Parcelable {

    private String goodsId;//商品id

    private int goodsNumber;//商品数量

    private int ticketUsedCount;//使用水票数量

    private int mortgageBucketCount;//押桶数

    private int sellBucketCount;//售桶数

    private int backBucketCount;//回桶数

    private String ticketName;//水票名称

    private String bucketName;//桶名称

    public BucketBean() {
    }

    protected BucketBean(Parcel in) {
        goodsId = in.readString();
        goodsNumber = in.readInt();
        ticketUsedCount = in.readInt();
        mortgageBucketCount = in.readInt();
        sellBucketCount = in.readInt();
        backBucketCount = in.readInt();
        ticketName = in.readString();
        bucketName = in.readString();
    }

    public static final Creator<BucketBean> CREATOR = new Creator<BucketBean>() {
        @Override
        public BucketBean createFromParcel(Parcel in) {
            return new BucketBean(in);
        }

        @Override
        public BucketBean[] newArray(int size) {
            return new BucketBean[size];
        }
    };

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public int getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public int getTicketUsedCount() {
        return ticketUsedCount;
    }

    public void setTicketUsedCount(int ticketUsedCount) {
        this.ticketUsedCount = ticketUsedCount;
    }

    public int getMortgageBucketCount() {
        return mortgageBucketCount;
    }

    public void setMortgageBucketCount(int mortgageBucketCount) {
        this.mortgageBucketCount = mortgageBucketCount;
    }

    public int getSellBucketCount() {
        return sellBucketCount;
    }

    public void setSellBucketCount(int sellBucketCount) {
        this.sellBucketCount = sellBucketCount;
    }

    public int getBackBucketCount() {
        return backBucketCount;
    }

    public void setBackBucketCount(int backBucketCount) {
        this.backBucketCount = backBucketCount;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(goodsId);
        parcel.writeInt(goodsNumber);
        parcel.writeInt(ticketUsedCount);
        parcel.writeInt(mortgageBucketCount);
        parcel.writeInt(sellBucketCount);
        parcel.writeInt(backBucketCount);
        parcel.writeString(ticketName);
        parcel.writeString(bucketName);
    }
}
