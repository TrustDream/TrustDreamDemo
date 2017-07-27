package com.trust.demo.tool;

import android.util.Log;

/**
 * Created by TrustTinker on 2017/4/10.
 */
public class L {
    private static String TAG = "Lhh_debug";
    private static boolean debug = true;

    public static void d(String msg){
        if(debug){
            Log.d(TAG, msg);
        }
    }

    public static void err(String msg){
        if(debug){
            Log.e(TAG, msg );
        }
    }
}
