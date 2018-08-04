package cn.jk.hscardfactory.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Environment
import android.view.View

import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

/**
 * Created by Administrator on 2017/8/1.
 */

object ImageUtil {
    /**
     * 必须是constraintlayout
     *
     * @param v
     * @return
     */
    fun loadBitmapFromView(v: View): Bitmap {
        val b: Bitmap
        b = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)

        val c = Canvas(b)
        v.layout(v.left, v.top, v.right, v.bottom)
        v.draw(c)
        return b
    }

    fun saveImg(context: Context, fileName: String, bitmap: Bitmap, sendBroadCast: Boolean): Boolean {
        val fOut: OutputStream
        val outputFileUri: Uri
        try {
            val root = File(Environment.getExternalStorageDirectory().toString()
                    + File.separator + Constant.PROJECT_NAME + File.separator + "image" + File.separator)
            root.mkdirs()

            val sdImageMainDirectory = File(root, fileName)
            outputFileUri = Uri.fromFile(sdImageMainDirectory)
            fOut = FileOutputStream(sdImageMainDirectory)
        } catch (e: Exception) {

            //   ToastUtil.showShort(context, e.getMessage() + " in save img" + " may no permission");
            return false
        }

        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)
            fOut.flush()
            fOut.close()
        } catch (e: Exception) {
            //      ToastUtil.showShort(context, e.getMessage() + "in saveImg");
            return false
        }

        val imgFile = File(outputFileUri.path)
        if (sendBroadCast) {
            context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imgFile)))
        }
        return true
    }
}
