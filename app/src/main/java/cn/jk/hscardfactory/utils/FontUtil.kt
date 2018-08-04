package cn.jk.hscardfactory.utils

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.blankj.utilcode.util.ToastUtils

/**
 * Created by Administrator on 2017/8/2.
 */

object FontUtil {
    fun overrideFonts(context: Context, v: View, fontPath: String) {
        try {
            if (v is ViewGroup) {
                for (i in 0 until v.childCount) {
                    val child = v.getChildAt(i)
                    overrideFonts(context, child, fontPath)
                }
            } else if (v is TextView) {
                v.typeface = Typeface.createFromAsset(context.assets, fontPath)
            }
        } catch (e: Exception) {
            ToastUtils.showShort(e.message + "in Font Util")

        }

    }
}
