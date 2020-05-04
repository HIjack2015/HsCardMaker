package cn.jk.hscardfactory.main

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.view.View
import cn.jk.hscardfactory.MyApplication.Companion.context
import cn.jk.hscardfactory.R
import cn.jk.hscardfactory.base.BaseActivity
import cn.jk.hscardfactory.data.model.Card
import cn.jk.hscardfactory.data.model.CardType
import cn.jk.hscardfactory.data.model.Card_Table
import cn.jk.hscardfactory.utils.*
import cn.jk.hscardfactory.view.HsCardView
import com.google.android.gms.ads.AdRequest
import com.orhanobut.logger.Logger
import com.raizlabs.android.dbflow.kotlinextensions.list
import com.raizlabs.android.dbflow.kotlinextensions.save
import com.raizlabs.android.dbflow.kotlinextensions.select
import com.yalantis.ucrop.UCrop
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import java.io.File
import java.util.*

@RuntimePermissions
class MainActivity : BaseActivity() {
    override fun initLayout() {
        loadBannerAd()
    }
    private fun loadBannerAd() {
        AdMobUtil.initAd(this)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
    override val contentViewId: Int
        get() = R.layout.activity_main


    internal lateinit var infoInputFragment: InfoInputFragment
    lateinit var card: Card
        private set
    var position_or_size_change = false

    lateinit var hsCardView: HsCardView
    /**
     * @return 获取需要需调整的view.
     */
    val adjustView: View
        get() = hsCardView.adjustView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(intent.extras!=null) {
            val cardTitle=intent.extras.getString(Constant.CARD)
           card= select.from(Card::class.java).where(Card_Table.name.eq(cardTitle)).list.get(0)
        } else {
            card = Card()
        }
        FontUtil.overrideFonts(mContext, rootDrawerLyt, Constant.FONT_PATH)
        infoInputFragment = supportFragmentManager.findFragmentById(R.id.infoInputFrag) as InfoInputFragment
        infoInputFragment.init()

        refreshCardView()
        hsCardView!!.setCustomFont()
        rootDrawerLyt!!.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(view: View, v: Float) {

            }

            override fun onDrawerOpened(view: View) {

            }

            override fun onDrawerClosed(view: View) {
                KeyBoardUtil.closeKeyBoard(mContext as BaseActivity)
            }

            override fun onDrawerStateChanged(i: Int) {

            }
        })
        calModelBtn!!.setOnClickListener {
            val modelMana = card!!.getModelMana()
            val modelMessage = String.format(mContext.getString(R.string.model_mana), modelMana, card.cost)
            toast(modelMessage)
        }
        openInputFragBtn.setOnClickListener { rootDrawerLyt!!.openDrawer(GravityCompat.START) }
        openOptionBtn.setOnClickListener { rootDrawerLyt!!.openDrawer(GravityCompat.END) }
        saveBtn.setOnClickListener { onSaveBtnClickedWithPermissionCheck() }
        shareBtn.setOnClickListener { onShareBtnClickedWithPermissionCheck() }
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    fun onSaveBtnClicked() {

        if (card.name.isEmpty()) {
            toast(getString(R.string.need_name))
            return
        }
        toast(R.string.making)
        hsCardView.setBackgroundColor(Color.WHITE)
        val cardBitmap = ImageUtil.loadBitmapFromView(hsCardView)
        hsCardView.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
        val fileName = card.name + Date().time + ".png"
        Thread(Runnable {
            val saveSuccess = ImageUtil.saveImg(mContext, fileName, cardBitmap, true)

            if (saveSuccess) {
                card.save()
                runOnUiThread { toast(R.string.save_card_success) }

            }
        }).start()

    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    fun onShareBtnClicked() {

        val cardNameIsEmpty = card.name.isEmpty()
        if (cardNameIsEmpty) {
            toast(R.string.need_name)
            return
        } else {
            toast(R.string.making)
        }

        hsCardView.setBackgroundColor(Color.WHITE)
        val cardBitmap = ImageUtil.loadBitmapFromView(hsCardView)
        hsCardView.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))

        val root = File(Environment.getExternalStorageDirectory().toString()
                + File.separator + Constant.PROJECT_NAME + File.separator + "image" + File.separator)
        val fileName = card.name + Date().time + ".png"
        Thread(Runnable {
            val saveSuccess = ImageUtil.saveImg(mContext, fileName, cardBitmap, false)
            if (!saveSuccess) {
                return@Runnable
            }
            card.save()
            val imageFile = File(root, fileName)
            val imageUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName(), imageFile);
            val intent = Intent(Intent.ACTION_SEND)
            //text
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_message))
            //image
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_message))
            intent.putExtra(Intent.EXTRA_STREAM, imageUri)
            intent.type = "image/*"
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(Intent.createChooser(intent, "send"))
        }).start()
    }


    /**
     * 根据card更新cardView
     */
    fun refreshCardView() {
        if (card.type.equals(CardType.MINION)) {
            calModelBtn!!.visibility = View.VISIBLE
        } else {
            calModelBtn!!.visibility = View.INVISIBLE
        }
        val layoutId = CardType.getLayout(card)
        val newHsCardView = HsCardView(this, layoutId)

        if (!::hsCardView.isInitialized || !hsCardView!!.equals(newHsCardView) || position_or_size_change) {
            cardViewParentLyt!!.removeAllViews()
            cardViewParentLyt!!.addView(newHsCardView)
            hsCardView = newHsCardView
            hsCardView.inflateByCard()
            position_or_size_change = false
        }

    }


    fun setBitmap(selectedBitmap: Bitmap) {
        card.picture = (selectedBitmap)
        hsCardView!!.setTopImage()
    }


    private val GALLERY_ACTIVITY_CODE: Int = 100

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    fun pickAndCutPhoto() {


        val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(i, GALLERY_ACTIVITY_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated function
        onRequestPermissionsResult(requestCode, grantResults)
    }

    fun focusOn(fieldName: String) {
        rootDrawerLyt!!.openDrawer(GravityCompat.START)
        infoInputFragment.focusOn(fieldName)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_ACTIVITY_CODE && resultCode == RESULT_OK) {
            val imageUri = data!!.getData()
            val tempUri = Uri.fromFile(File(cacheDir, "cutImg"))
            val topRect = hsCardView.topRect

            UCrop.of(imageUri!!, tempUri)
                    .withAspectRatio(topRect.width().toFloat(), topRect.height().toFloat())
                    .withMaxResultSize(topRect.width(), topRect.height())
                    .start(this)
        } else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) run {
            val resultUri = UCrop.getOutput(data!!)
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, resultUri)
                setBitmap(bitmap)
            } catch (e: Exception) {
                Logger.i(e.message!!)
            }
        }
    }

}