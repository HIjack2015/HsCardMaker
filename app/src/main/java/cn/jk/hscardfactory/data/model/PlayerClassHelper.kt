package cn.jk.hscardfactory.data.model


/**
 * Created by Administrator on 2017/8/4.
 */
import cn.jk.hscardfactory.R

object PlayerClassHelper {
    internal var gold_hero_card_bg = intArrayOf(R.mipmap.gold_hero_card_neutral, R.mipmap.gold_hero_card_druid, R.mipmap.gold_hero_card_hunter, R.mipmap.gold_hero_card_mage, R.mipmap.gold_hero_card_rogue, R.mipmap.gold_hero_card_paladin, R.mipmap.gold_hero_card_priest, R.mipmap.gold_hero_card_warrior, R.mipmap.gold_hero_card_shaman, R.mipmap.gold_hero_card_warlock, R.mipmap.gold_hero_card_neutral, R.mipmap.gold_hero_card_neutral)
    internal var hero_card_bg = intArrayOf(R.mipmap.hero_card_neutral, R.mipmap.hero_card_druid, R.mipmap.hero_card_hunter, R.mipmap.hero_card_mage, R.mipmap.hero_card_rogue, R.mipmap.hero_card_paladin, R.mipmap.hero_card_priest, R.mipmap.hero_card_warrior, R.mipmap.hero_card_shaman, R.mipmap.hero_card_warlock, R.mipmap.hero_card_neutral, R.mipmap.hero_card_neutral)
    internal var hero_bg = intArrayOf(R.mipmap.head_neutral, R.mipmap.head_druid, R.mipmap.head_hunter, R.mipmap.head_mage, R.mipmap.head_rogue, R.mipmap.head_paladin, R.mipmap.head_priest, R.mipmap.head_warrior, R.mipmap.head_shaman, R.mipmap.head_warlock, R.mipmap.head_death, R.mipmap.head_demon)
    internal var minion_bg = intArrayOf(R.mipmap.minion_neutral, R.mipmap.minion_druid, R.mipmap.minion_hunter, R.mipmap.minion_mage, R.mipmap.minion_rogue, R.mipmap.minion_paladin, R.mipmap.minion_priest, R.mipmap.minion_warrior, R.mipmap.minion_shaman, R.mipmap.minion_warlock, R.mipmap.minion_death, R.mipmap.minion_demon)
    internal var minion_gold_bg = intArrayOf(R.mipmap.gold_minion_neutral, R.mipmap.gold_minion_druid, R.mipmap.gold_minion_hunter, R.mipmap.gold_minion_mage, R.mipmap.gold_minion_rogue, R.mipmap.gold_minion_paladin, R.mipmap.gold_minion_priest, R.mipmap.gold_minion_warrior, R.mipmap.gold_minion_shaman, R.mipmap.gold_minion_warlock, R.mipmap.gold_minion_death, R.mipmap.gold_minion_demon)
    internal var spell_bg = intArrayOf(R.mipmap.spell_neutral, R.mipmap.spell_druid, R.mipmap.spell_hunter, R.mipmap.spell_mage, R.mipmap.spell_rogue, R.mipmap.spell_paladin, R.mipmap.spell_priest, R.mipmap.spell_warrior, R.mipmap.spell_shaman, R.mipmap.spell_warlock, R.mipmap.spell_death, R.mipmap.spell_demon)
    internal var gold_spell_bg = intArrayOf(R.mipmap.gold_spell_neutral, R.mipmap.gold_spell_druid, R.mipmap.gold_spell_hunter, R.mipmap.gold_spell_mage, R.mipmap.gold_spell_rogue, R.mipmap.gold_spell_paladin, R.mipmap.gold_spell_priest, R.mipmap.gold_spell_warrior, R.mipmap.gold_spell_shaman, R.mipmap.gold_spell_warlock, R.mipmap.gold_spell_death, R.mipmap.gold_spell_demon)
    internal var hero_power_bg = intArrayOf(R.mipmap.power_neutral, R.mipmap.power_druid, R.mipmap.power_hunter, R.mipmap.power_mage, R.mipmap.power_rogue, R.mipmap.power_paladin, R.mipmap.power_priest, R.mipmap.power_warrior, R.mipmap.power_shaman, R.mipmap.power_warlock, R.mipmap.power_death, R.mipmap.power_demon)

    internal var weapon_bg = intArrayOf(R.mipmap.card_weapon_neutral, R.mipmap.card_weapon_druid, R.mipmap.card_weapon_hunter, R.mipmap.card_weapon_mage, R.mipmap.card_weapon_rogue, R.mipmap.card_weapon_paladin, R.mipmap.card_weapon_priest, R.mipmap.card_weapon_warrior, R.mipmap.card_weapon_shaman, R.mipmap.card_weapon_warlock, R.mipmap.card_weapon_death, R.mipmap.card_weapon_monk)


    fun getBgDrawableId(playerClass: PlayerClass, cardType: CardType, isGold: Boolean): Int {
        val indexOfClass = PlayerClass.getIndexOf(playerClass)
        var mipmapId = 0
        when (cardType) {
            CardType.SPELL -> if (isGold) {
                mipmapId = gold_spell_bg[indexOfClass]
            } else {
                mipmapId = spell_bg[indexOfClass]
            }
            CardType.MINION -> if (isGold) {
                mipmapId = minion_gold_bg[indexOfClass]
            } else {
                mipmapId = minion_bg[indexOfClass]
            }
            CardType.WEAPON -> if (isGold) {
                mipmapId = R.mipmap.weapon_gold
            } else {
                mipmapId = weapon_bg[indexOfClass]
            }
            CardType.HERO -> mipmapId = hero_bg[indexOfClass]
            CardType.HERO_CARD -> if (isGold) {
                mipmapId = gold_hero_card_bg[indexOfClass]
            } else {
                mipmapId = hero_card_bg[indexOfClass]
            }
            CardType.HERO_POWER -> mipmapId = hero_power_bg[indexOfClass]
        }
        return mipmapId
    }


}
