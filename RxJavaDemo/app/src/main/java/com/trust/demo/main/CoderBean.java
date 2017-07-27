package com.trust.demo.main;

/**
 * Created by Trust on 2017/7/27.
 */

public class CoderBean {
    private int imgId;
    private String msg;
    private String time;

    public CoderBean(int imgId, String msg, String time) {
        this.imgId = imgId;
        this.msg = msg;
        this.time = time;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
