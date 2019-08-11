package cn.jk.hscardfactory.main


import android.app.DialogFragment
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.jk.hscardfactory.R
import cn.jk.hscardfactory.utils.PxUtil
import cn.jk.hscardfactory.view.CardTitleView
import kotlinx.android.synthetic.main.dialog_adjust_ele.*

/**
 * Created by Administrator on 2017/8/11.
 */

class AdjustEleDialog : DialogFragment() {

    lateinit internal var context: MainActivity
    lateinit internal var viewToAdjust: View


    lateinit internal var layoutParams: ConstraintLayout.LayoutParams
    internal var moveThread: Thread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Dialog)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val v = inflater.inflate(R.layout.dialog_adjust_ele, container, false)
        context = activity as MainActivity

        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        viewToAdjust = context.adjustView
        layoutParams = viewToAdjust.layoutParams as ConstraintLayout.LayoutParams
        if (viewToAdjust !is TextView) {
            addBtn!!.visibility = View.INVISIBLE
            subBtn!!.visibility = View.INVISIBLE
        }
        setClickListener()
    }

    private fun setClickListener() {

        val views = arrayOf<View>(leftBtn, rightBtn, upBtn, downBtn, addBtn, subBtn)
        for (view in views) {
            view.setOnTouchListener { view, motionEvent ->
                when (motionEvent.action) {
                    MotionEvent.ACTION_DOWN -> {
                        moveThread = Thread(Runnable {
                            while (!moveThread!!.isInterrupted) {
                                context.runOnUiThread { click(view) }
                                try {
                                    Thread.sleep(130)
                                } catch (e: Exception) {
                                    return@Runnable
                                }

                            }
                        })
                        context.position_or_size_change = true
                        moveThread!!.start()
                    }
                    MotionEvent.ACTION_UP -> if (moveThread != null) {
                        moveThread!!.interrupt()
                    }
                }
                false
            }
        }
    }

    private fun click(view: View) {
        val movePx = PxUtil.getPxByDp(context, 1)
        when (view.id) {
            R.id.subBtn -> {
                var textView = viewToAdjust as TextView
                var size = textView.textSize
                if (viewToAdjust is CardTitleView) {
                    (viewToAdjust as CardTitleView).setTextSize(size - 2)
                } else {
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size - 2)
                }
            }
            R.id.addBtn -> {
                var textView = viewToAdjust as TextView
                var size = textView.getTextSize()

                if (viewToAdjust is CardTitleView) {
                    (viewToAdjust as CardTitleView).setTextSize(size + 2)
                } else {
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size + 2)
                }
            }
            R.id.upBtn -> {
                layoutParams.bottomMargin += movePx
                layoutParams.topMargin -= movePx
            }
            R.id.downBtn -> {
                layoutParams.topMargin += movePx
                layoutParams.bottomMargin -= movePx
            }
            R.id.leftBtn -> {
                layoutParams.rightMargin += movePx
                layoutParams.leftMargin -= movePx
            }
            R.id.rightBtn -> {
                layoutParams.rightMargin -= movePx
                layoutParams.leftMargin += movePx
            }
        }
        viewToAdjust.layoutParams = layoutParams
    }


}
