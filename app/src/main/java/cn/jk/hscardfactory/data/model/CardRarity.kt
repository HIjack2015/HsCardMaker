package cn.jk.hscardfactory.data.model

import android.content.Context
import cn.jk.hscardfactory.R
import java.util.*

/**
 * Created by Administrator on 2017/5/1.
 */

enum class CardRarity {
    common, rare, epic, legendary, free;


    fun getLocalArr(context: Context): Array<String> {
        val CardRaritys = CardRarity.values()
        val CardRarityStrs = Array(CardRaritys.size, { i -> "" })

        for (i in CardRaritys.indices) {
            val CardRarity = CardRaritys[i]
            val resId = enumIdMap[CardRarity]!!
            CardRarityStrs[i] = context.getString(resId)
        }
        return CardRarityStrs
    }

    companion object {
        //    public static final String common="普通";
        //    public static final String rare = "稀有";
        //    public static final String epic = "史诗";
        //    public static final String legendary = "传说";
        //    public static final String free = "基本";
        internal var enumIdMap = HashMap<CardRarity, Int>()

        init {
            enumIdMap[free] = R.string.free
            enumIdMap[common] = R.string.common
            enumIdMap[rare] = R.string.rare
            enumIdMap[epic] = R.string.epic
            enumIdMap[legendary] = R.string.legendary

        }

        fun getEnum(localStr: String, context: Context): CardRarity? {
            val cardRaritys = CardRarity.values()
            for (i in cardRaritys.indices) {
                val cardRarity = cardRaritys[i]
                val resId = enumIdMap[cardRarity]!!
                val name = context.getString(resId)
                if (name == localStr) {
                    return cardRarity
                }
            }
            return null
        }
    }


}
