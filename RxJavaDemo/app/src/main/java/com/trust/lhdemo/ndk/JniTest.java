package com.trust.lhdemo.ndk;

/**
 * Created by Trust on 2017/4/7.
 */
public class JniTest {
    static {
        System.loadLibrary("helloJNI");
    }
    public native String getString();

    public native void updateFile(String path);

    public native int[] updateIntArray(int []data);

}
