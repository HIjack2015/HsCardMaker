package cn.jk.hscardfactory.main

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.Typeface
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.jk.hscardfactory.R
import cn.jk.hscardfactory.data.model.*
import cn.jk.hscardfactory.utils.Constant
import cn.jk.hscardfactory.utils.ImageUtil
import cn.jk.hscardfactory.utils.MagicTextView
import cn.jk.hscardfactory.utils.PxUtil

/**
 * TODO: document your custom view class.
 */
class HsCardView : ConstraintLayout {


    //这里放上所用的,用到哪个算哪个
    internal var context: MainActivity = getContext() as MainActivity

    internal lateinit var fullCardLyt: ConstraintLayout
    internal lateinit var cardBgImg: ImageView
    internal lateinit var cardNameView: MagicTextView

    internal var rareImg: ImageView? = null
    internal var gemHolder: ImageView? = null
    internal var costTxt: MagicTextView? = null
    internal var cardSetImg: ImageView? = null
    internal var descView: TextView? = null
    internal var healthTxt: TextView? = null
    internal var attackTxt: TextView? = null
    internal var dragonView: ImageView? = null
    internal var raceTxt: TextView? = null
    internal var cardTopImg: ImageView? = null

    internal var raceLyt: ViewGroup? = null


    internal lateinit var rootView: View
    internal lateinit var card: Card
    var layoutId: Int = 0
        internal set
    internal var costMarginLeft = 0
    internal var healthMarginRight = 0
    internal var attackMarginLeft = 0
    lateinit var adjustView: View

    val topRect: Rect
        get() {
            val width = cardTopImg!!.width
            val height = cardTopImg!!.height
            return Rect(0, 0, width, height)
        }

    constructor(context: Context) : super(context)

    constructor(context: MainActivity, layoutId: Int) : super(context) {
        this.layoutId = layoutId
        this.context = context
        card = context.card
        rootView = View.inflate(context, layoutId, this)

        cardBgImg = findViewById(R.id.cardBgImg)
        rareImg = findViewById(R.id.rareImg)
        gemHolder = findViewById(R.id.gem_holder)
        costTxt = findViewById<MagicTextView>(R.id.costTxt)
        cardNameView = findViewById(R.id.cardNameView)
        cardSetImg = findViewById(R.id.cardSetImg)
        descView = findViewById(R.id.descView)
        healthTxt = findViewById(R.id.healthTxt)
        attackTxt = findViewById(R.id.attackTxt)
        dragonView = findViewById(R.id.dragonView)
        raceTxt = findViewById(R.id.raceTxt)
        fullCardLyt = findViewById(R.id.fullCardLyt)

        raceLyt = findViewById(R.id.raceLyt)
        cardTopImg = findViewById(R.id.cardTopImg)

        if (costTxt != null) {
            costMarginLeft = (costTxt!!.layoutParams as ConstraintLayout.LayoutParams).leftMargin
        }
        if (healthTxt != null) {
            healthMarginRight = (healthTxt!!.layoutParams as ConstraintLayout.LayoutParams).rightMargin
        }
        if (attackTxt != null) {
            attackMarginLeft = (attackTxt!!.layoutParams as ConstraintLayout.LayoutParams).leftMargin
        }
        setLongPressLogic()

        val idArr = arrayOf<Int>(R.id.attackTxt, R.id.costTxt, R.id.cardNameView, R.id.cardSetImg, R.id.rareImg, R.id.raceLyt, R.id.healthTxt, R.id.descView)
        val fieldName = arrayOf("attack", "cost", "name", "set", "rarity", "race", "health", "desc")
        idArr.forEach {
            val viewId = it
            val view = findViewById<View>(viewId)
            if (view != null) {
                view.setOnClickListener {
                    val index = idArr.indexOf(viewId)
                    context.focusOn(fieldName[index])
                }

            }

        }

    }

    private fun setLongPressLogic() {
        val adjustEles = arrayOf<View?>(cardNameView, costTxt, attackTxt, healthTxt, descView)
        for (view in adjustEles) {
            if (view == null) {
                return
            } else {

                view.setOnLongClickListener { view ->
                    adjustView = view
                    AdjustEleDialog().show(context.fragmentManager, "adjustView")
                    false
                }
            }
        }
    }


    fun inflateByCard() {


        cardTopImg!!.setImageBitmap(card.picture)
        if (card.name.isEmpty()) {
            card.name = context.getString(R.string.app_name)
        }
        setName()

        setCardBg()
        refreshCardSet()
        setCustomFont()
        setCost()
        refreshCardHealth()
        refreshCardAttack()
        refreshCardDesc()

        refreshCardRarity()


    }

    fun setCardBg() {
        val bgDrawableId = PlayerClassHelper.getBgDrawableId(card.playerClass, card.type!!, card.isGold)
        cardBgImg.setImageResource(bgDrawableId)
    }


    fun setName() {

        cardNameView.visibility = View.VISIBLE

        cardNameView.text = context.card.name
    }

    fun setCost() {
        if (costTxt != null) {
            val cost = context.card.cost
            var marginLeft = costMarginLeft
            if (cost >= 10) {
                marginLeft = marginLeft - PxUtil.getPxByDp(context, 20)
            }
            val layoutParams = costTxt!!.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.leftMargin = marginLeft
            costTxt!!.layoutParams = layoutParams
            costTxt!!.text = cost.toString()
        } else {

        }
    }

    fun setCustomFont() {

        val type = Typeface.createFromAsset(context.assets, Constant.FONT_PATH)
        if (descView != null) {
            descView!!.typeface = Typeface.DEFAULT
        }
        val textViews = arrayOf<TextView?>(cardNameView, costTxt, healthTxt, attackTxt, raceTxt)
        for (textView in textViews) {
            if (textView != null) {
                textView.setTypeface(type, Typeface.BOLD)
            }
        }
    }

    fun refreshCardSet() {
        if (cardSetImg != null) {
            val drawableId = CardSetHelper.getSetDrawableId(card.cardSet, card.type, card.isGold)
            cardSetImg!!.setImageResource(drawableId)
        }
    }

    override fun equals(hsCardView: Any?): Boolean {
        return if (hsCardView is HsCardView) {
            layoutId == hsCardView.layoutId
        } else {
            false
        }

    }

    fun refreshCardRarity() {
        if (rareImg == null) {
            return
        }
        val drawableId = CardRarityHelper.getRarityDrawableId(card.rarity)
        rareImg!!.setImageResource(drawableId)
        if (gemHolder != null) {
            if (card.rarity == CardRarity.free) {
                gemHolder!!.visibility = View.INVISIBLE
            } else {
                gemHolder!!.visibility = View.VISIBLE
            }
        }
        if (dragonView != null) {
            if (card.rarity == CardRarity.legendary) {
                dragonView!!.visibility = View.VISIBLE
            } else {
                dragonView!!.visibility = View.INVISIBLE
            }
        }


    }

    fun refreshCardDesc() {

        if (descView != null) {
            val descView: TextView? = descView as TextView
            if (Build.VERSION.SDK_INT >= 24) {
                descView!!.text = Html.fromHtml(card.desc, Html.FROM_HTML_MODE_LEGACY)
            } else {
                descView!!.text = Html.fromHtml(card.desc)
            }

        }
    }

    fun refreshCardHealth() {

        if (healthTxt != null) {

            val health = context.card.health
            var marginRight = healthMarginRight
            if (health >= 10) {
                marginRight = marginRight - PxUtil.getPxByDp(context, 20)
            }
            val layoutParams = healthTxt!!.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.rightMargin = marginRight
            healthTxt!!.layoutParams = layoutParams
            healthTxt!!.text = health.toString()

        }
    }

    fun refreshCardAttack() {

        if (attackTxt != null) {
            val attackTxt: MagicTextView? = attackTxt as? MagicTextView
            val attack = context.card.attack
            var marginLeft = attackMarginLeft
            if (attack >= 10) {
                marginLeft = marginLeft - PxUtil.getPxByDp(context, 20)
            }
            val layoutParams = attackTxt!!.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.leftMargin = marginLeft
            attackTxt.layoutParams = layoutParams
            attackTxt.text = attack.toString()
        }
    }

    fun setTopImage() {
        cardTopImg!!.setImageBitmap(card.picture)
        cardTopImg!!.scaleType = ImageView.ScaleType.FIT_XY
    }

    fun refreshCardRace() {
        if (raceLyt != null) {
            if (card.race!!.isEmpty()) {
                raceLyt!!.visibility = View.GONE
            } else {
                raceLyt!!.visibility = View.VISIBLE
                raceTxt!!.text = card.race
            }
        }
    }


    fun adjustCardNameView() {


        if (card.type != CardType.HERO_CARD) {
            return
        }

        var textBitmap = ImageUtil.loadBitmapFromView(cardNameView)
        textBitmap = reshape(textBitmap)


        cardNameView.visibility = View.INVISIBLE
    }

    private fun reshape(textBitmap: Bitmap): Bitmap {


        val textBitmapX = textBitmap.width
        val textBitmapY = textBitmap.height
        val xRatio: Double
        val yRatio: Double

        val sin15 = Math.sin(Math.toRadians(30.0))
        val cos15 = Math.sqrt(1 - sin15 * sin15)


        val inR = textBitmapX.toDouble() / 2.0 / sin15
        val outR = inR + textBitmapY
        val negH = inR * cos15

        val newBitmapY = 2 * textBitmapY
        val conf = Bitmap.Config.ARGB_8888 // see other conf types
        val imageB = Bitmap.createBitmap(textBitmapX, newBitmapY, conf) //


        for (x in 0 until textBitmapX) {
            for (y in 0 until textBitmapY) {
                val pixel = textBitmap.getPixel(x, y)
                val xs = Math.abs(x - textBitmapX / 2)
                val addH = (Math.sqrt(outR * outR + xs * xs) - negH).toInt()
                val newY = (y + addH - textBitmapY * 1.6).toInt()
                if (x < 0 || newY < 0 || x >= imageB.width || newY >= imageB.height) {
                    continue
                }

                imageB.setPixel(x, newY, pixel)
            }
        }
        return imageB

    }
}
