package cn.jk.hscardfactory.main

import android.content.Context
import android.util.AttributeSet

/**
 * Created by jack on 2018/8/27.
 */
class SpellTitleView(context: Context, attrs: AttributeSet?) : CardTitleView(context, attrs) {
    override fun setOriginPath() {
        originPath =
                "m 12,130 c 0,0 300,-127 660,2"
    }

}
