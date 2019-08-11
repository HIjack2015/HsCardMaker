package cn.jk.hscardfactory

import android.app.Application
import android.content.Context

import com.blankj.utilcode.util.Utils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.tencent.bugly.crashreport.CrashReport


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
        CrashReport.initCrashReport(getApplicationContext(), "3cb5c85375", false)
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
