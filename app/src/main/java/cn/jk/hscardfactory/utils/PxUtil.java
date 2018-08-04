package cn.jk.hscardfactory.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by Administrator on 2017/8/8.
 */

public class PxUtil {
    public static int getPxByDp(Context context, int dp) {
        Resources r = context.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()
        );
        return px;
    }

}
