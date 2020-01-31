package com.trinarr.phonegameconcept.UI;

import android.util.Log;

public class LogManager {
    public static boolean isLogEnabled = true;

    private static final String LOG_TAG = "TEST";

    public static void log(String message, Class<?> source) {
        if(isLogEnabled) {
            String sourceName = source.getName();
            String sourceNameCut = sourceName.substring(sourceName.lastIndexOf(".")+1);

            if(message == null) {
                Log.i(LOG_TAG, sourceNameCut.toUpperCase()+": null");
            }
            else {
                Log.i(LOG_TAG, sourceNameCut.toUpperCase()+": "+message);
            }
        }
    }

    public static void throwException(String errorMessage) {
        if(isLogEnabled) {
            throw new RuntimeException(errorMessage);
        }
    }
}
