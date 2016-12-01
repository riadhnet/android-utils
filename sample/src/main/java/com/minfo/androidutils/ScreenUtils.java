package com.minfo.androidutils;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;

import java.lang.reflect.Field;

public class ScreenUtils {


    @SuppressWarnings("deprecation")
    public static Drawable getDrawableResources(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return ContextCompat.getDrawable(context, id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }

    @SuppressWarnings("deprecation")
    public static int getColor(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }


    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    /**
     * The specs tell that
     * <ol>
     * <li>Navigation Drawer should be at most 5*56dp wide on phones and 5*64dp wide on tablets.</li>
     * <li>Navigation Drawer should have right margin of 56dp on phones and 64dp on tablets.</li>
     * </ol>
     * yet the minimum margin is hardcoded to be 64dp instead of 56dp. This fixes it.
     */
    public static void fixMinDrawerMargin(DrawerLayout drawerLayout) {
        try {
            Field f = DrawerLayout.class.getDeclaredField("mMinDrawerMargin");
            f.setAccessible(true);
            f.set(drawerLayout, 0);

            drawerLayout.requestLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SuppressWarnings("deprecation")
    public static boolean isAirplaneModeOn(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        } else {
            return Settings.Global.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        }
    }


}
