package cn.jk.hscardfactory.data.model

import cn.jk.hscardfactory.R
import cn.jk.hscardfactory.data.model.CardSet.*
import java.util.*

/**
 * Created by Administrator on 2017/8/4.
 */

object CardSetHelper {
    internal var cardSets = arrayOf<CardSet>(BOOM, WOOD, KOBOL, BASIC_SET, CLASSIC, HORNOR, NAXX, GVG, TORNEIO, EXPLORADORES, ROCHA_NEGRA, OLD_GODS, KARAZHAN, GADGETZAN, UNGORO, FROZEN_THRONE)
    internal var minion_card_set = intArrayOf(R.mipmap.col_boom, R.mipmap.col_wood, R.mipmap.col_kobol, R.mipmap.col_none, R.mipmap.tp_minion_col_standard, R.mipmap.tp_minion_col_honor, R.mipmap.tp_minion_col_naxx,
            R.mipmap.tp_minion_col_gvg, R.mipmap.tp_minion_col_grant, R.mipmap.tp_minion_col_explorer, R.mipmap.tp_minion_col_brock,
            R.mipmap.tp_minion_col_gods, R.mipmap.tp_minion_col_karazhan, R.mipmap.tp_minion_col_gadgetzan, R.mipmap.tp_minion_col_ungoro, R.mipmap.tp_minion_col_ctg)
    internal var gold_minion_card_set = intArrayOf(R.mipmap.gold_col_boom, R.mipmap.gold_col_wood, R.mipmap.gold_col_kobol,R.mipmap.col_none, R.mipmap.gold_tp_minion_col_standard,
            R.drawable.gold_tp_minion_col_honor, R.mipmap.gold_tp_minion_col_naxx, R.mipmap.gold_tp_minion_col_gvg,
            R.mipmap.gold_tp_minion_col_grant, R.mipmap.gold_tp_minion_col_explorer, R.mipmap.gold_tp_minion_col_brock,
            R.mipmap.gold_tp_minion_col_gods, R.mipmap.gold_tp_minion_col_karazhan, R.mipmap.gold_tp_minion_col_gadgetzan,
            R.mipmap.gold_tp_minion_col_ungoro, R.mipmap.gold_tp_minion_col_ctg)
    internal var spell_card_set = intArrayOf(R.mipmap.col_boom, R.mipmap.col_wood, R.mipmap.col_kobol,R.mipmap.col_none, R.mipmap.tp_spell_col_standard,
            R.mipmap.tp_spell_col_honor, R.mipmap.tp_spell_col_naxx, R.mipmap.tp_spell_col_gvg, R.mipmap.tp_spell_col_grant, R.mipmap.tp_spell_col_explorer,
            R.mipmap.tp_spell_col_brock, R.mipmap.tp_spell_col_gods, R.mipmap.tp_spell_col_karazhan,
            R.mipmap.tp_spell_col_gadgetzan, R.mipmap.tp_spell_col_ungoro, R.mipmap.tp_spell_col_ctg)
    internal var gold_spell_card_set = intArrayOf(R.mipmap.gold_col_boom, R.mipmap.gold_col_wood, R.mipmap.gold_col_kobol,R.mipmap.col_none, R.mipmap.gold_tp_spell_col_standard, R.drawable.gold_tp_spell_col_honor,
            R.mipmap.gold_tp_spell_col_naxx, R.mipmap.gold_tp_spell_col_gvg, R.mipmap.gold_tp_spell_col_grant, R.mipmap.gold_tp_spell_col_explorer,
            R.mipmap.gold_tp_spell_col_brock, R.mipmap.gold_tp_spell_col_gods, R.mipmap.gold_tp_spell_col_karazhan, R.mipmap.gold_tp_spell_col_gadgetzan,
            R.mipmap.gold_tp_spell_col_ungoro, R.mipmap.gold_tp_spell_col_ctg)
    internal var weapon_card_set = intArrayOf(R.mipmap.gold_col_boom, R.mipmap.gold_col_wood, R.mipmap.gold_col_kobol,R.mipmap.col_none, R.mipmap.tp_weapon_col_standard, R.drawable.tp_weapon_col_honor,
            R.mipmap.tp_weapon_col_naxx, R.mipmap.tp_weapon_col_gvg, R.mipmap.tp_weapon_col_grant, R.mipmap.tp_weapon_col_explorer,
            R.mipmap.tp_weapon_col_brock, R.mipmap.tp_weapon_col_gods, R.mipmap.tp_weapon_col_karazhan, R.mipmap.tp_weapon_col_gadgetzan,
            R.drawable.tp_weapon_col_ungoro, R.drawable.tp_weapon_col_ctg)
    internal var gold_weapon_card_set = intArrayOf(R.mipmap.gold_col_boom, R.mipmap.gold_col_wood, R.mipmap.gold_col_kobol,R.mipmap.col_none, R.mipmap.gold_tp_weapon_col_standard, R.drawable.gold_tp_weapon_col_honor,
            R.mipmap.gold_tp_weapon_col_naxx, R.mipmap.gold_tp_weapon_col_gvg, R.mipmap.gold_tp_weapon_col_grant, R.mipmap.gold_tp_weapon_col_explorer,
            R.mipmap.gold_tp_weapon_col_brock, R.mipmap.gold_tp_weapon_col_gods, R.mipmap.gold_tp_weapon_col_karazhan, R.mipmap.gold_tp_weapon_col_gadgetzan,
            R.mipmap.gold_tp_weapon_col_ungoro, R.mipmap.gold_tp_weapon_col_ctg)

    //TODO 根据cardSet 和 cardType返回相应的mipmapId
    fun getSetDrawableId(cardSet: CardSet, cardType: CardType, isGold: Boolean): Int {
        val index = Arrays.asList(*cardSets).indexOf(cardSet)
        var mipmapId = 0
        if (isGold) {
            when (cardType) {
                CardType.MINION -> mipmapId = gold_minion_card_set[index]
                CardType.HERO_CARD, CardType.SPELL -> mipmapId = gold_spell_card_set[index]

                CardType.WEAPON -> mipmapId = gold_weapon_card_set[index]
            }
        } else {
            when (cardType) {
                CardType.MINION -> mipmapId = minion_card_set[index]
                CardType.HERO_CARD, CardType.SPELL -> mipmapId = spell_card_set[index]

                CardType.WEAPON -> mipmapId = weapon_card_set[index]
            }
        }
        return mipmapId

    }
}
