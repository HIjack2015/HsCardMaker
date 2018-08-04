package cn.jk.hscardfactory.data.model

import android.content.Context
import cn.jk.hscardfactory.R
import java.util.*

/**
 * Created by Administrator on 2017/8/1.
 */

enum class PlayerClass {
    neutral, druid, hunter, mage, rogue, paladin, priest, warrior, shaman, warlock, death, demon;


    companion object {
        internal var classIdMap = HashMap<PlayerClass, Int>()
        private val allClass = arrayOf(neutral, druid, hunter, mage, rogue, paladin, priest, warrior, shaman, warlock, death, demon)

        init {
            classIdMap[neutral] = R.string.neutral
            classIdMap[druid] = R.string.druid
            classIdMap[hunter] = R.string.hunter
            classIdMap[mage] = R.string.mage
            classIdMap[rogue] = R.string.rogue
            classIdMap[paladin] = R.string.paladin
            classIdMap[priest] = R.string.priest
            classIdMap[warrior] = R.string.warrior
            classIdMap[shaman] = R.string.shaman
            classIdMap[warlock] = R.string.warlock
            classIdMap[death] = R.string.deathKnight
            classIdMap[demon] = R.string.demonHunter
        }

        fun getEnum(localStr: String, context: Context): PlayerClass? {
            val playerClasses = PlayerClass.values()
            for (i in playerClasses.indices) {
                val playerClass = playerClasses[i]
                val resId = classIdMap[playerClass]
                val name = context.getString(resId!!)
                if (name == localStr) {
                    return playerClass
                }
            }
            return null
        }

        /**
         * 获取用来显示的数组
         *
         * @param context
         * @return 用来显示给用户的数组.
         */
        fun getLocalArr(context: Context): Array<String> {
            val playerClasses = PlayerClass.values()
            val playerClassStrs = Array<String>(playerClasses.size, { i -> "" })

            for (i in playerClasses.indices) {
                val playerClass = playerClasses[i]
                val resId = classIdMap[playerClass]
                playerClassStrs[i] = context.getString(resId!!)
            }
            return playerClassStrs
        }

        fun getIndexOf(playerClass: PlayerClass): Int {
            return Arrays.asList(*allClass).indexOf(playerClass)
        }
    }
}