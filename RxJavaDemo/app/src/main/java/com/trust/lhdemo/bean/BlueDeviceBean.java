package com.trust.lhdemo.bean;

/**
 * Created by Trust on 2017/4/18.
 */
public class BlueDeviceBean {
    private String name,address;

    public BlueDeviceBean(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
