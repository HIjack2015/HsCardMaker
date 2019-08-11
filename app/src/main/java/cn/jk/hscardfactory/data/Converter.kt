package cn.jk.hscardfactory.data

import android.content.ContextWrapper
import android.graphics.Bitmap
import cn.jk.hscardfactory.data.model.CardSet
import com.blankj.utilcode.util.FileUtils
import com.raizlabs.android.dbflow.converter.TypeConverter
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.graphics.BitmapFactory
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import java.io.File
import cn.jk.hscardfactory.MyApplication
import cn.jk.hscardfactory.utils.ImageUtil
import com.blankj.utilcode.util.ImageUtils
import java.io.ByteArrayOutputStream
import java.security.MessageDigest


/**
 * Created by jack on 2019/8/11.
 */
class Converter{
    class PictureConverter : TypeConverter<String, Bitmap>() {

        //暂且这样吧.
        override fun getDBValue(model: Bitmap): String {
            val baos = ByteArrayOutputStream()
            model!!.compress(Bitmap.CompressFormat.PNG, 100, baos) //bm is the bitmap object
            val bitmapBytes = baos.toByteArray()
            val md = MessageDigest.getInstance("MD5")
            val digested = md.digest(bitmapBytes)
            val digestStr= digested.joinToString("") {
                String.format("%02x", it)
            }
            val path= MyApplication.context.filesDir.toString()+"/"+digestStr;
            if(!File(path).exists()) {
                ImageUtil.saveImg(MyApplication.context,path,model!!)
            }
            return path
        }

        override fun getModelValue(filePath: String?): Bitmap {

            val image = File(filePath)
            val bmOptions = BitmapFactory.Options()
            var bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions)
            return bitmap
        }

    }

}