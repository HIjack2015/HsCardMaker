package cn.jk.hscardfactory.gallery


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import cn.jk.hscardfactory.R
import cn.jk.hscardfactory.data.model.Card
import cn.jk.hscardfactory.main.MainActivity
import cn.jk.hscardfactory.utils.Constant
import com.blankj.utilcode.util.SnackbarUtils.getView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.BaseQuickAdapter
import org.jetbrains.anko.startActivity


/**
 * Created by jack on 2019/8/11.
 */

open class CardAdapter(cardList:List<Card>) : BaseQuickAdapter<Card, BaseViewHolder>(R.layout.layout_card_brief,cardList) {

    override fun convert(helper: BaseViewHolder, item: Card) {
        helper.setText(R.id.cardNameTxt,item.name).setText(R.id.cardTypeTxt,item.type.getLocalStr(mContext)).
                setText(R.id.cardDescTxt,item.desc).setImageBitmap(R.id.pictureImg,item.picture)

        helper.getView<View>(R.id.cardView).setOnClickListener {
            mContext.startActivity<MainActivity>(Constant.CARD to item.name)
        }
    }


}