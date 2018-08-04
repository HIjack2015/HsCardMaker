package cn.jk.hscardfactory.base

import cn.jk.hscardfactory.R
import cn.jk.hscardfactory.data.model.Result
import cn.jk.hscardfactory.data.net.Api
import cn.jk.hscardfactory.data.net.InitRetrofit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import javax.net.ssl.HttpsURLConnection

/**
 * <pre>
 * author : jiakang
 * e-mail : 1079153785@qq.com
 * time   : 2018/05/10
 * desc   :
 * version: 1.0
</pre> *
 */
abstract class BasePresenterImpl<V : BaseView> protected constructor(protected val mView: V) : BasePresenter {
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override val netWorkErrorHandler: Consumer<Throwable>
        get() = Consumer { error ->
            if (error is IOException) {
                mView.onError(R.string.net_work_error)
            } else if (error is HttpException) {
                when (error.code()) {
                    HttpsURLConnection.HTTP_UNAUTHORIZED, HttpsURLConnection.HTTP_FORBIDDEN -> {
                        mView.openActivityOnTokenExpire()
                        return@Consumer
                    }
                    else -> mView.onError(error.message)
                }

            } else {
                mView.onError(error.message)
            }
        }

    override fun start() {

    }


    override fun stop() {
        mCompositeDisposable.clear()
    }

    protected abstract inner class NetLogic<T> : TypeToken<T> { // 这里的继承是为了拿到type.

        protected var mApi: Api
        protected var mObservable: Observable<Result>

        protected abstract val observable: Observable<Result>

        protected constructor() {

            mApi = InitRetrofit.apiInstance
            mObservable = observable

        }


        protected constructor(cache: Boolean) {

            if (cache) {
                mApi = InitRetrofit.getCacheApi()
            } else {
                mApi = InitRetrofit.apiInstance
            }
            mObservable = observable
        }

        fun start() {


            val disposable = mObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer<Result> { result ->
                if (!result.isSuccess) {
                    mView.onError(result.msg)
                } else {

                    val json = Gson().toJson(result.data)
                    var data = InitRetrofit.gson!!.fromJson<T>(json, type)
                    dealResult(data)
                }
            }, netWorkErrorHandler)
            mCompositeDisposable.add(disposable)
        }

        protected abstract fun dealResult(data: T)


    }
}
