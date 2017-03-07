package com.eme.waterdelivery.model.bean;

/**
 * Created by dijiaoliang on 17/3/7.
 */

public class DelayBean {

    public DelayBean() {
    }

    public DelayBean(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
