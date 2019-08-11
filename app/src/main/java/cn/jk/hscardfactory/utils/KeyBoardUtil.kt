package cn.jk.hscardfactory.utils

import android.app.Activity
import android.content.Context
import android.support.v4.content.ContextCompat.getSystemService
import android.view.View
import android.view.inputmethod.InputMethodManager

import cn.jk.hscardfactory.base.BaseActivity

/**
 * Created by Administrator on 2017/8/8.
 */

object KeyBoardUtil {
    fun closeKeyBoard(activity: BaseActivity) {
        var view = activity.currentFocus

        if (view==null) {
            view=activity.findViewById(android.R.id.content)
        }
        view?.let { v ->
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.let { it.hideSoftInputFromWindow(v.windowToken, 0) }
        }
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
