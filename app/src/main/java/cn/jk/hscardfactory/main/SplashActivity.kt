package cn.jk.hscardfactory.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * 这个activity可以让启动app时显示一张图片,而不是显示白屏.
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
