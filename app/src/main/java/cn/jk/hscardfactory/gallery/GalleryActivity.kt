package cn.jk.hscardfactory.gallery

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import cn.jk.hscardfactory.base.BaseActivity
import cn.jk.hscardfactory.data.model.Card
import com.raizlabs.android.dbflow.kotlinextensions.list
import com.raizlabs.android.dbflow.kotlinextensions.select
import kotlinx.android.synthetic.main.activity_gallery.*
import android.widget.Toast
import android.view.View
import android.view.ViewGroup
import cn.jk.hscardfactory.R
import cn.jk.hscardfactory.main.MainActivity
import cn.jk.hscardfactory.utils.Constant
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class GalleryActivity : BaseActivity() {
    override val contentViewId: Int
        get() = cn.jk.hscardfactory.R.layout.activity_gallery

    override fun initLayout() {
        val cardList= select.from(Card::class.java).list

        cardRcy.setHasFixedSize(true)
        cardRcy.layoutManager=LinearLayoutManager(this)
        var adapter=CardAdapter(cardList)
        cardRcy.adapter=adapter
        adapter.emptyView=layoutInflater.inflate(R.layout.layout_no_data,cardRcy.parent as ViewGroup,false)
    }


}
