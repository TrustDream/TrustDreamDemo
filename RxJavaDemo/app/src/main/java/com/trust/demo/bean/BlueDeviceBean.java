package com.trust.demo.bean;

/**
 * Created by TrustTinker on 2017/4/18.
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
