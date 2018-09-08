package cn.jk.hscardfactory.main

import android.content.Context
import android.util.AttributeSet
import com.blankj.utilcode.util.ConvertUtils
import com.svgandroid.SVGParser

/**
 * Created by jack on 2018/9/8.
 */
class StraightLineTitleView(context: Context, attrs: AttributeSet?) : CardTitleView(context, attrs) {
    override fun setOriginPath() {

    }

    override fun setPath(viewWidth: Int, viewHeight: Int, textHeightPx: Int, text: String) {

        var newHeight = viewHeight - ConvertUtils.dp2px(4f)
        val containEnglish = Regex(".*[a-zA-Z].*").matches(text)
        if (containEnglish) {
            newHeight = viewHeight - textHeightPx / 3
        }
        val compatPath = String.format("m 0,%d L %d,%d", newHeight, viewWidth, newHeight)
        path = SVGParser.parsePath(compatPath)
    }

}