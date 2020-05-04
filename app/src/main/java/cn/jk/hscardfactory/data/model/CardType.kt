package cn.jk.hscardfactory.data.model

import android.content.Context
import cn.jk.hscardfactory.R
import java.util.*

/**
 * Created by Administrator on 2017/5/31.
 */

enum class CardType {
    MINION, SPELL, WEAPON, HERO, HERO_CARD, HERO_POWER,CHESS;

    fun getEnum(localStr: String, context: Context): CardType? {
        val cardTypes = CardType.values()
        for (i in cardTypes.indices) {
            val cardType = cardTypes[i]
            val name = context.getString(typeNameMap[cardType]!!)
            if (name == localStr) {
                return cardType
            }
        }
        return null
    }

    fun getLocalArr(context: Context): Array<String> {
        val cardType = CardType.values()
        val localArr = Array(cardType.size, { i -> "" })
        for (i in cardType.indices) {
            val resId = typeNameMap[cardType[i]]
            localArr[i] = context.getString(resId!!)
        }
        return localArr
    }
    fun getLocalStr(context: Context):String {
        val resId = typeNameMap[this]
        return context.getString(resId!!)
    }

    companion object {
        internal var typeNameMap = HashMap<CardType, Int>()
        internal var typeLayoutMap = HashMap<CardType, Int>()
        internal var typeGoldLayoutMap = HashMap<CardType, Int>()


        init {
            typeNameMap[WEAPON] = R.string.WEAPON
            typeNameMap[CHESS] = R.string.CHESS
            typeNameMap[HERO] = R.string.HERO
            typeNameMap[HERO_CARD] = R.string.HERO_CARD
            typeNameMap[SPELL] = R.string.SPELL
            typeNameMap[HERO_POWER] = R.string.HERO_POWER
            typeNameMap[MINION] = R.string.MINION

            typeLayoutMap[WEAPON] = R.layout.hs_weapon_card_view
            typeLayoutMap[HERO] = R.layout.hs_hero_view
            typeLayoutMap[HERO_CARD] = R.layout.hs_hero_card_view
            typeLayoutMap[SPELL] = R.layout.hs_spell_card_view
            typeLayoutMap[HERO_POWER] = R.layout.hs_hero_power_view
            typeLayoutMap[MINION] = R.layout.hs_minion_card_view
            typeLayoutMap[CHESS] = R.layout.hs_chess_card_view


            typeGoldLayoutMap[WEAPON] = R.layout.hs_weapon_card_gold_view
            typeGoldLayoutMap[HERO] = R.layout.hs_hero_gold_view
            typeGoldLayoutMap[HERO_CARD] = R.layout.hs_hero_card_gold_view
            typeGoldLayoutMap[SPELL] = R.layout.hs_spell_card_gold_view
            typeGoldLayoutMap[HERO_POWER] = R.layout.hs_hero_power_view
            typeGoldLayoutMap[MINION] = R.layout.hs_minion_card_gold_view
            typeGoldLayoutMap[CHESS] = R.layout.hs_chess_card_gold_view
        }

        fun getLayout(card: Card): Int {
            return if (card.isGold) {
                typeGoldLayoutMap[card.type]!!
            } else {
                typeLayoutMap[card.type]!!
            }


        }
    }

}
