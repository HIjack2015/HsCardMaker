package cn.jk.hscardfactory.data.model

import cn.jk.hscardfactory.R
import cn.jk.hscardfactory.data.model.CardRarity.*
import java.util.*

/**
 * Created by Administrator on 2017/8/6.
 */

object CardRarityHelper {
    internal var cardRaritys = arrayOf<CardRarity>(common, rare, epic, legendary, free)
    internal var rarity_int = intArrayOf(R.mipmap.rar_common, R.mipmap.rar_rare, R.mipmap.rar_epic, R.mipmap.rar_legendary, R.mipmap.rar_basic)

    fun getRarityDrawableId(cardRarity: CardRarity): Int {
        val index = Arrays.asList(*cardRaritys).indexOf(cardRarity)
        return rarity_int[index]

    }
}