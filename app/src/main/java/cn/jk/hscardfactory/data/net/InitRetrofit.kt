package cn.jk.hscardfactory.data.net

import cn.jk.hscardfactory.MyApplication
import cn.jk.hscardfactory.config.ServerConfig
import cn.jk.hscardfactory.data.cache.CachedUser
import com.blankj.utilcode.util.NetworkUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.orhanobut.logger.Logger
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

/**
 *
 */
object InitRetrofit {


    private val HEAD_INTERCEPTOR = Interceptor { chain ->
        val originalRequest = chain.request()
        val compressedRequest = originalRequest.newBuilder()

                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "*/*")
                .build()
        chain.proceed(compressedRequest)
    }
    private val REWRITE_CACHE_CONTROL_INTERCEPTOR = Interceptor { chain ->
        val originalResponse = chain.proceed(chain.request())

        if (NetworkUtils.isConnected()) {
            Logger.i("cache10" + originalResponse.isSuccessful + originalResponse.body())
            val maxAge = 0 // 为了调试方便,不要加入缓存.
            originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=$maxAge")
                    .build()

        } else {
            Logger.i("cache10000" + originalResponse.isSuccessful + originalResponse.body())
            val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
            originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .build()
        }
    }
    private val REQUEST_CACHE_INTERCEPTOR = Interceptor { chain ->
        var request = chain.request()
        request.cacheControl().noCache()
        if (NetworkUtils.isConnected()) {
            val maxAge = 0 // 为了调试方便,不要加入缓存.
            request = request.newBuilder().addHeader("Cache-Control", "public, max-age=$maxAge").build()
        } else {
            val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
            request = request.newBuilder().addHeader("Cache-Control",
                    "public, only-if-cached, max-stale=$maxStale").build()

        }
        chain.proceed(request)
    }
    private val COMMON_ARGU_INTERCEPTOR = Interceptor { chain ->
        var request = chain.request()
        val url = request.url().newBuilder().addQueryParameter("userId", CachedUser.userId).build()
        request = request.newBuilder().url(url).build()
        chain.proceed(request)
    }

    var gson: Gson? = null
    private var client: OkHttpClient? = null
    private var cacheClient: OkHttpClient? = null
    private var api: Api = newApi(getClient())
    private var cacheApi: Api? = null

    val apiInstance: Api
        get() {
            return api
        }

    private val commonBuilder: OkHttpClient.Builder
        get() = OkHttpClient.Builder()
                .addInterceptor(HEAD_INTERCEPTOR)
                .addInterceptor(COMMON_ARGU_INTERCEPTOR)

    fun getCacheApi(): Api {
        if (cacheApi == null) {
            cacheApi = newApi(getCacheClient())
        }

        return cacheApi as Api

    }

    private fun newApi(client: OkHttpClient): Api {
        if (gson == null) {
            gson = GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH-mm")
                    .create()
        }
        return Retrofit.Builder()
                .baseUrl(ServerConfig.serverUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson!!))
                .build().create(Api::class.java)

    }

    private fun getClient(): OkHttpClient {

        if (client != null) {
            return client as OkHttpClient
        }

        client = commonBuilder.build()
        return client as OkHttpClient
    }

    private fun getCacheClient(): OkHttpClient {

        if (cacheClient != null) {
            return cacheClient as OkHttpClient
        }

        val httpCacheDirectory = File(MyApplication.context!!.getCacheDir(), "tuseResponse")
        val cacheSize = 30 * 1024 * 1024 // 10 MiB

        cacheClient = commonBuilder.addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .addInterceptor(REQUEST_CACHE_INTERCEPTOR)
                .cache(Cache(httpCacheDirectory, cacheSize.toLong()))
                .build()

        return cacheClient as OkHttpClient
    }
}
