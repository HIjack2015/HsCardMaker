package cn.jk.hscardfactory.data.model

import android.content.Context
import cn.jk.hscardfactory.R
import java.util.*


/**
 * Created by Administrator on 2017/8/2.
 */

enum class CardSet {
    BOOM,
    WOOD,
    KOBOL,
    BASIC_SET, CLASSIC, HORNOR, NAXX, GVG, TORNEIO, EXPLORADORES, ROCHA_NEGRA, OLD_GODS,
    KARAZHAN, GADGETZAN, UNGORO, FROZEN_THRONE;


    fun getLocalArr(context: Context): Array<String> {
        val CardSets = CardSet.values()
        val CardSetStrs = Array<String>(CardSets.size, { i -> "" })

        for (i in CardSets.indices) {
            val CardSet = CardSets[i]
            val resId = enumIdMap[CardSet]
            CardSetStrs[i] = context.getString(resId!!)
        }
        return CardSetStrs
    }

    companion object {
        internal var enumIdMap = HashMap<CardSet, Int>()

        init {
            enumIdMap[BASIC_SET] = R.string.BASIC_SET
            enumIdMap[CLASSIC] = R.string.PADRAO
            enumIdMap[HORNOR] = R.string.HORNOR_SET
            enumIdMap[NAXX] = R.string.NAXX
            enumIdMap[GVG] = R.string.GVG
            enumIdMap[TORNEIO] = R.string.TORNEIO
            enumIdMap[EXPLORADORES] = R.string.EXPLORADORES
            enumIdMap[ROCHA_NEGRA] = R.string.ROCHA_NEGRA
            enumIdMap[OLD_GODS] = R.string.OLD_GODS
            enumIdMap[KARAZHAN] = R.string.KARAZHAN
            enumIdMap[GADGETZAN] = R.string.GADGETZAN
            enumIdMap[UNGORO] = R.string.UNGORO
            enumIdMap[FROZEN_THRONE] = R.string.FROZEN_THRONE
            enumIdMap[BOOM] = R.string.BOOM
            enumIdMap[WOOD] = R.string.WOOD
            enumIdMap[KOBOL] = R.string.KOBOL


        }

        fun getEnum(localStr: String, context: Context): CardSet? {
            val CardSets = CardSet.values()
            for (i in CardSets.indices) {
                val CardSet = CardSets[i]
                val resId = enumIdMap[CardSet]
                val name = context.getString(resId!!)
                if (name == localStr) {
                    return CardSet
                }
            }
            return null
        }
    }


}
