package cn.jk.hscardfactory.base

import android.os.Bundle
import android.view.View
import cn.jk.hscardfactory.R

import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.SnackbarUtils

/**
 * <pre>
 * author : jiakang
 * e-mail : 1079153785@qq.com
 * time   : 2018/04/21
 * desc   :
 * version: 1.0
</pre> *
 *
 *
 * init layout 中对layout进行初始化.
 * presenter 暂定联网获取数据.
 */
abstract class BaseMvpActivity<T : BasePresenter> : BaseActivity(), BaseView {
    protected var mPresenter: T? = null


    private var snackBarParentView: View? = null

    override val isNetworkConnected: Boolean
        get() = NetworkUtils.isConnected()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setPresenter()
        mPresenter!!.start()
    }

    protected abstract fun setPresenter()


    override fun onDestroy() {
        mPresenter!!.stop()
        super.onDestroy()
    }

    override fun onError(resId: Int) {
        onError(getString(resId))
    }

    override fun showLoading() {
        if (mProgressBar != null) {
            mProgressBar!!.visibility = View.VISIBLE
            mProgressBarVisible = true
        }

    }

    override fun hideLoading() {
        if (mProgressBar != null) {
            mProgressBar!!.visibility = View.INVISIBLE
            mProgressBarVisible = false
        }

    }


    override fun onError(message: String?) {
        var notNullMessage: String
       if (message == null) {
            notNullMessage = mContext.getString(R.string.empty_error)
        } else {
            notNullMessage = message
        }

        if (snackBarParentView == null) {
            snackBarParentView = window.decorView.rootView
        }
        SnackbarUtils.with(snackBarParentView!!).setMessage(notNullMessage).showError()
        hideLoading()
    }

    override fun showMessage(message: String) {
        if (snackBarParentView == null) {
            snackBarParentView = window.decorView.rootView
        }
        SnackbarUtils.with(snackBarParentView!!).setMessage(message).showSuccess()
    }

    override fun openActivityOnTokenExpire() {

    }

    override fun showMessage(resId: Int) {
        showMessage(getString(resId))
    }

    override fun hideKeyboard() {

    }

    protected fun setSnackBarParentView(view: View) {
        snackBarParentView = view
    }
}