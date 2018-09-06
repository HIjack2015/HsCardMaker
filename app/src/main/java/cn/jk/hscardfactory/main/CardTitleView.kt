package cn.jk.hscardfactory.main

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.TextView
import cn.jk.hscardfactory.R
import com.blankj.utilcode.util.ConvertUtils
import com.svgandroid.SVGParser

/**
 * Created by jack on 2018/9/5.
 */
abstract class CardTitleView(context: Context, attrs: AttributeSet?) : TextView(context, attrs) {
    lateinit var innerPaint: Paint
    lateinit var middlePaint: Paint
    lateinit var outPaint: Paint
    lateinit var path: Path
    private var text = "This is a test"
    lateinit var originPath: String
    var viewWidth = 0
    var myTextSize = 60f
    abstract fun setOriginPath()

    init {
        setOriginPath()
        init(attrs)
    }

    private fun init(attributeSet: AttributeSet?) {
        context.theme.obtainStyledAttributes(
                attributeSet,
                R.styleable.SpellTitleView,
                0, 0).apply {

            try {
                text = getString(R.styleable.SpellTitleView_text)
                myTextSize = ConvertUtils.dp2px(getInteger(R.styleable.SpellTitleView_size, 20).toFloat()).toFloat()

            } finally {
                recycle()
            }
        }
        innerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        innerPaint.setColor(0xfff7f7f7.toInt());



        middlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        middlePaint.style = Paint.Style.STROKE
        middlePaint.color = Color.BLACK
        middlePaint.strokeWidth = 8f
        middlePaint.strokeMiter = 6f
        middlePaint.strokeJoin = Paint.Join.ROUND


        outPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        outPaint.style = Paint.Style.STROKE
        outPaint.color = 0x80000000.toInt()
        outPaint.strokeWidth = 10f
        outPaint.strokeMiter = 6f
        outPaint.strokeJoin = Paint.Join.ROUND


        val tf = Typeface.createFromAsset(context.assets, "font/GLEI00M_t.ttf")
        middlePaint.setTypeface(tf)
        innerPaint.setTypeface(tf)
        outPaint.setTypeface(tf)
    }

    fun setPath() {

        val regex = Regex("\\d+")
        val compatPath = regex.replace(originPath) { m ->
            ConvertUtils.dp2px(m.value.toFloat() / 3f).toString()
        }


        // if (!::path.isInitialized) {
        path = SVGParser.parsePath(compatPath)
        //}
    }

    override fun onDraw(canvas: Canvas) {
        setPath()
        outPaint.textSize = myTextSize
        middlePaint.textSize = myTextSize
        innerPaint.textSize = myTextSize;

        if (viewWidth == 0) {
            viewWidth = canvas.width
        }


        var hOffset = 0f;
        val textWidth = middlePaint.measureText(text)
        // TODO 这里数值是不对的,需要有一个对应的函数.但是我暂时还想不出来,这个地方要用到高等数学的微分.和曲线积分.
        if (textWidth < viewWidth) {
            hOffset = (viewWidth - textWidth) / 2
        }

        canvas.drawTextOnPath(text, path, hOffset, 0f, outPaint)
        canvas.drawTextOnPath(text, path, hOffset, 0f, middlePaint)
        canvas.drawTextOnPath(text, path, hOffset, 0f, innerPaint)
    }

    fun setText(text: String) {
        this.text = text
        invalidate()
    }

    /**
     * 测试用的函数,先不要删了
     */
    fun setPath(text: String) {
        path = SVGParser.parsePath(text)
        invalidate();
    }

    override fun setTextSize(size: Float) {
        this.myTextSize = size;
        invalidate()
    }

    override fun getTextSize(): Float {
        return myTextSize
    }

}