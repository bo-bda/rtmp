package com.android.rtmp.rtmp.utils;

import android.support.annotation.NonNull;
import android.util.Log;

/*
 * Created by Bo on 22.08.2015.
 */
public class RTMPLog {

    private static boolean isDebug = false;

    public static void setIsDebug(boolean isDebug) {
        RTMPLog.isDebug = isDebug;
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static void e(String tag, String message) {
        if (isDebug)
            Log.e(tag, message);
    }

    public static void e(String tag, Object... objects) {
        if (isDebug && objects != null) {
            String message = null;
            for (Object o : objects)
                message += " " + o.toString();
            Log.e(tag, message);
        }
    }

    public static void e(@NonNull Object tag, String message) {
        if (isDebug)
            Log.e(tag.getClass().getSimpleName(), message);
    }

    public static void e(String tag, @NonNull Object object) {
        if (isDebug)
            Log.e(tag, object.toString());
    }

}
