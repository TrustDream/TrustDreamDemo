package com.trust.demo.main;

/**
 * Created by Trust on 2017/7/27.
 */

public class UIBean {
    private int imgId;
    private String msg;

    public UIBean(int imgId, String msg) {
        this.imgId = imgId;
        this.msg = msg;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
