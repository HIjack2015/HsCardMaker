package cn.jk.hscardfactory

import android.app.Application
import android.content.Context

import com.blankj.utilcode.util.Utils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


/**
 * <pre>
 * author : jiakang
 * e-mail : 1079153785@qq.com
 * time   : 2018/04/21
 * desc   :
 * version: 1.0
</pre> *
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
        Logger.addLogAdapter(AndroidLogAdapter())

        Utils.init(this)

        //        // 内存泄露检查工具
        //        if (LeakCanary.isInAnalyzerProcess(this)) {
        //            // This process is dedicated to LeakCanary for heap analysis.
        //            // You should not init your app in this process.
        //            return;
        //        }
        //        LeakCanary.install(this);
    }

    companion object {
        lateinit var context: Context
    }
}
