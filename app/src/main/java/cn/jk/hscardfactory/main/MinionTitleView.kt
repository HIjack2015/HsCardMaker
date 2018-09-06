package cn.jk.hscardfactory.main

import android.content.Context
import android.util.AttributeSet

/**
 * Created by jack on 2018/8/27.
 */
class MinionTitleView(context: Context, attrs: AttributeSet?) : CardTitleView(context, attrs) {
    override fun setOriginPath() {
        originPath =
                "m 10,125 c 0,0 8,14 114,2 " +
                "63,-7 127,-27 237,-41 80," +
                "-20 159,-23 260,-6" +
                " 60,11 95,27 95,27"
    }

}
