package com.eme.waterdelivery.model.bean.entity;

/**
 * 售票列表bean
 *
 * Created by dijiaoliang on 17/5/4.
 */
public class WaterTicketBean {

    private String id;//水票id
    private int number;//水票数量
    private String price;//面值
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
