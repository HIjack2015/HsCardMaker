package cn.jk.hscardfactory.main

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import cn.jk.hscardfactory.utils.Constant

/**
 * Created by Administrator on 2017/8/2.
 */

class MySpinnerAdapter(context: Context, resource: Int, items: Array<String>) : ArrayAdapter<String>(context, resource, items) {
    // Initialise custom font, for example:
    internal var font = Typeface.createFromAsset(getContext().assets,
            Constant.FONT_PATH)

    // Affects default (closed) state of the spinner
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent) as TextView
        view.typeface = font
        return view
    }

    // Affects opened state of the spinner
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent) as TextView
        view.typeface = font
        return view
    }
}
