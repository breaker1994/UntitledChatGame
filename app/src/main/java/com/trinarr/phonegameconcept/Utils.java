package com.trinarr.phonegameconcept;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class Utils {
    public static Drawable getAvatarDrawable(int type, Context context) {
        switch (type) {
            default:
                return context.getResources().getDrawable(R.drawable.avatar_cat, context.getTheme());
            case 1:
                return context.getResources().getDrawable(R.drawable.avatar_news, context.getTheme());
            case 2:
                return context.getResources().getDrawable(R.drawable.avatar_sister, context.getTheme());
        }
    }
}
