package cn.jk.hscardfactory.data.model

import android.graphics.Bitmap
import cn.jk.hscardfactory.MyApplication
import cn.jk.hscardfactory.R
import cn.jk.hscardfactory.data.AppDatabase
import cn.jk.hscardfactory.data.Converter
import cn.jk.hscardfactory.data.Converter.PictureConverter
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel
import java.io.Serializable
import java.util.regex.Pattern


/**
 * Created by Administrator on 2017/4/7.
 */
@Table(database = AppDatabase::class)
class Card : BaseModel(),Serializable {

    @PrimaryKey
    var name: String = "" //卡牌名称
     @Column var isGold: Boolean = false//是否为金卡
    @Column  var cost: Int = 0 //需要的法力水晶
     @Column  var desc: String = MyApplication.context.getString(R.string.app_desc) //卡牌描述
    @Column  var attack: Int = 0 //攻击
    @Column  var health: Int = 0 //生命值Or 耐久
    @Column  var race: String = ""//种族

    @Column(typeConverter = PictureConverter::class)
    var picture: Bitmap?

    @Column  var cardSet: CardSet = CardSet.BASIC_SET//牌池
    @Column  var rarity: CardRarity = CardRarity.common //稀有度
    @Column  var type: CardType = CardType.MINION //为什么品种的卡
    @Column  var playerClass: PlayerClass = PlayerClass.neutral//所属职业

    init {

        name = MyApplication.context.getString(R.string.app_name)
        type = CardType.MINION
        playerClass = PlayerClass.neutral
        cardSet = CardSet.BASIC_SET
        rarity = CardRarity.common
        isGold = false
        cost = 1
        desc = MyApplication.context.getString(R.string.app_desc)
        health = 2
        attack = 2
        race = ""
        val conf = Bitmap.Config.ARGB_8888 // see other conf types
        picture = Bitmap.createBitmap(1, 1, conf)

    }

    fun getModelMana(): Double {
        var newAttack = attack.toDouble()
        if (newAttack == 0.0) {
            newAttack = 0.5
        }
        var mana = Math.sqrt(health * newAttack) - 1


        val chargeStr = MyApplication.context.getString(R.string.charge)
        val shieldStr = MyApplication.context.getString(R.string.shield)
        val stealthStr = MyApplication.context.getString(R.string.stealth)
        val tauntStr = MyApplication.context.getString(R.string.taunt)
        val windfuryStr = MyApplication.context.getString(R.string.windfuryStr)
        val lifeStealStr = MyApplication.context.getString(R.string.lifeSteal)

        val pattern = Pattern.compile("\\<b\\>.+\\</b\\>")

        val matcher = pattern.matcher(desc!!)

        while (matcher.find()) {
            val str = matcher.group()

            if (str.contains(chargeStr)) {
                mana += newAttack * 0.35
            }
            if (str.contains(shieldStr)) {
                mana += Math.sqrt(attack.toDouble())
            }
            if (str.contains(stealthStr)) {
                mana += 1.0
            }
            if (str.contains(tauntStr)) {
                mana += Math.ceil(health / newAttack) * 0.5
            }
            if (str.contains(windfuryStr)) {
                mana += newAttack * 0.25
            }
            if (str.contains(lifeStealStr)) {
                mana += newAttack * 0.5
            }
        }
        return mana
    }
}
