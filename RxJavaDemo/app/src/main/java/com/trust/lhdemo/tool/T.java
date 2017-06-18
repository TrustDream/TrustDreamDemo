package com.trust.lhdemo.tool;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by TrustTinker on 2017/4/12.
 */
public class T {
    private static Toast toast;

    public static void showToast(Context context,
                                 String content) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
