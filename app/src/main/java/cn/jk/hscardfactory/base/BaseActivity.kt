package cn.jk.hscardfactory.base

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import cn.jk.hscardfactory.utils.DebouncedOnClickListener
import com.orhanobut.logger.Logger
import org.greenrobot.eventbus.EventBus


/**
 * <pre>
 * author : jiakang
 * e-mail : 1079153785@qq.com
 * time   : 2018/04/21
 * desc   :
 * version: 1.0
</pre> *
 */
abstract class BaseActivity : AppCompatActivity() {
    private var mContentViewId = 0

    protected var mContext: Context = this

    internal var mProgressBar: ProgressBar? = null         //这里有一个诡异的bug就是在某些机型上mProgressBar会在onResume后自动出现.
    internal var mProgressBarVisible = false

    internal var mBackBtn: View? = null

    /**
     * 函数体这样写 return mContentViewId=;
     */
    protected abstract val contentViewId: Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContentViewId = contentViewId

        if (mContentViewId == 0) {
            Logger.wtf("你需要先设置contentViewId")
            return
        }

        setContentView(mContentViewId)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        initLayout()
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        }


        if (mBackBtn != null) {
            mBackBtn!!.setOnClickListener(object : DebouncedOnClickListener() {
                public override fun onDebouncedClick(v: View) {
                    onBackPressed()
                }
            })
        }
    }

    /**
     * 对界面进行初始化操作。
     */
    protected abstract fun initLayout()

    override fun onDestroy() {
        super.onDestroy()
        //     EventBus.getDefault().unregister(this);
    }

    protected fun setFullScreen() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.setFlags(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION, View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

    }




    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    public override fun onResume() {
        super.onResume()
        if (mProgressBar != null) {
            if (mProgressBarVisible) {
                mProgressBar!!.visibility = View.VISIBLE
            } else {
                mProgressBar!!.visibility = View.GONE
            }
        }

    }

    protected fun registerEventBus() {
        EventBus.getDefault().register(this)
    }
}
