package com.trust.rxjavademo.ndk;

/**
 * Created by Trust on 2017/4/7.
 */
public class JniTest {
    static {
        System.loadLibrary("jary");
    }
    public native String getString();

}
