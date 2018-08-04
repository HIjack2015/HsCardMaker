package cn.jk.hscardfactory.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

import cn.jk.hscardfactory.base.BaseActivity

/**
 * Created by Administrator on 2017/8/8.
 */

object KeyBoardUtil {
    fun closeKeyBoard(activity: BaseActivity) {
        val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)

    }

    fun openKeyBoard(activity: BaseActivity) {
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val focusView = activity.currentFocus
        if (focusView != null) {
            inputMethodManager.toggleSoftInputFromWindow(focusView.applicationWindowToken,
                    InputMethodManager.SHOW_FORCED, 0)
        }

    }
}
