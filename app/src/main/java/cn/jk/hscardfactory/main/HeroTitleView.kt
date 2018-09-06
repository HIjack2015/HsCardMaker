package cn.jk.hscardfactory.main

import android.content.Context
import android.util.AttributeSet

/**
 * Created by jack on 2018/8/27.
 */
class HeroTitleView(context: Context, attrs: AttributeSet?) : CardTitleView(context, attrs) {
    override fun setOriginPath() {
        originPath =
                "m 12,170 c 0,0 324,-190 660,-8"
    }

}
