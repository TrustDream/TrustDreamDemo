package com.trust.lhdemo.tinker.tool;

import com.trust.lhdemo.BuildConfig;

/**
 * Created by Trust on 2017/6/18.
 */

public class BuildInfo {
    /**
     * they are not final, so they won't change with the BuildConfig values!
     */
    public static boolean DEBUG        = BuildConfig.DEBUG;
    public static String VERSION_NAME = BuildConfig.VERSION_NAME;
    public static int     VERSION_CODE = BuildConfig.VERSION_CODE;

    public static String MESSAGE       = BuildConfig.MESSAGE;
    public static String TINKER_ID     = BuildConfig.TINKER_ID;
    public static String PLATFORM      = BuildConfig.PLATFORM;

}
