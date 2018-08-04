package cn.jk.hscardfactory.main


import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import cn.jk.hscardfactory.R
import cn.jk.hscardfactory.data.model.*
import cn.jk.hscardfactory.utils.KeyBoardUtil
import kotlinx.android.synthetic.main.fragment_info_input.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class InfoInputFragment : Fragment() {

    internal lateinit var context: MainActivity
    var card: Card? = null


    internal lateinit var playerClasses: Array<String>
    internal lateinit var cardSets: Array<String>
    internal lateinit var cardTypes: Array<String>
    internal lateinit var cardRaritys: Array<String>
    internal var have_init = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_info_input, container, false)
        context = activity as MainActivity
        return view
    }

    /**
     * 初始化view的逻辑.
     */
    fun init() {
        card = context.card
        setSpinner()
        setLogic()
        have_init = true

    }


    fun setSpinner() {
        playerClasses = PlayerClass.getLocalArr(context)
        cardSets = CardSet.BASIC_SET.getLocalArr(context)
        cardTypes = CardType.SPELL.getLocalArr(context)
        cardRaritys = CardRarity.free.getLocalArr(context)


        val arrs = arrayOf(playerClasses, cardSets, cardTypes, cardRaritys)
        val spinners = arrayOf<Spinner>(playerClassSpn, cardSetSpn, cardTypeSpn, cardRaritySpn)
        for (i in spinners.indices) {
            val spinner = spinners[i]
            val strArr = arrs[i]
            val adapter = MySpinnerAdapter(context, android.R.layout.simple_spinner_item, strArr)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        cardTypeSpn!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                val cardTypeStr = cardTypes[position]
                val cardType = CardType.SPELL.getEnum(cardTypeStr, context)
                card!!.type = cardType!!
                if (have_init) {
                    context.refreshCardView()
                }
                var showRace = false
                var showHealth = true
                var showAttack = true
                var healthImgId = R.mipmap.health
                var attackImgId = R.mipmap.atack

                var showRarity = true
                var showSet = true
                var showGold = true
                var showCost = true
                var showDesc = true


                when (cardType) {
                    CardType.MINION -> showRace = true
                    CardType.SPELL -> {
                        showHealth = false
                        showAttack = false
                    }
                    CardType.HERO -> {
                        showRarity = false
                        showGold = false
                        showCost = false
                        showSet = false
                        showDesc = false
                        showAttack = false
                    }
                    CardType.HERO_CARD -> {
                        showRarity = false
                        showGold = false
                        showSet = false
                        healthImgId = R.mipmap.armor
                        showAttack = false
                    }
                    CardType.HERO_POWER -> {
                        showSet = false
                        showRarity = false
                        showGold = false
                        showHealth = false
                        showAttack = false
                    }
                    CardType.WEAPON -> {
                        healthImgId = R.mipmap.weapon_hp
                        attackImgId = R.mipmap.weapon_atack
                    }
                }
                val propertys = booleanArrayOf(showRace, showHealth, showAttack, showRarity, showSet, showGold, showCost, showDesc)
                val layouts = arrayOf<ViewGroup>(raceLyt, healthLyt, attackLyt, rarityLyt, setLyt, isGoldLyt, costLyt, descLyt)
                healthLbl!!.setImageResource(healthImgId)
                attackLbl!!.setImageResource(attackImgId)
                for (i in propertys.indices) {
                    val property = propertys[i]
                    val viewGroup = layouts[i]
                    if (property) {
                        viewGroup.visibility = View.VISIBLE
                    } else {
                        viewGroup.visibility = View.GONE
                    }
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {}

        }
        playerClassSpn!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
                val playerClassStr = playerClasses[position]
                val playerClass = PlayerClass.getEnum(playerClassStr, context)
                card!!.playerClass = playerClass!!
                if (have_init) {
                    context.hsCardView.setCardBg()
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }
        cardSetSpn!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, l: Long) {
                val str = cardSets[position]
                val cardSet = CardSet.getEnum(str, context)
                card!!.cardSet = cardSet!!
                if (have_init) {
                    context.hsCardView.refreshCardSet()
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }
        cardRaritySpn!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, l: Long) {
                val str = cardRaritys[position]
                val cardRarity = CardRarity.getEnum(str, context)
                card!!.rarity = cardRarity!!
                if (have_init) {
                    context.hsCardView.refreshCardRarity()
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }

    }

    internal fun setLogic() {
        takePhotoBtn.setOnClickListener { context.pickAndCutPhotoWithPermissionCheck() }
        resetBtn.setOnClickListener { resetInput() }
        boldDescBtn.setOnClickListener { descEdt!!.append("<b></b>") }

        nameEdt!!.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                card!!.name = s.toString()
                context.hsCardView.setName()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
        costEdt!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                if (editable.toString().isEmpty()) {
                    return
                }
                val newCost = Integer.valueOf(editable.toString())
                card!!.cost = newCost
                context.hsCardView.setCost()
            }
        })
        isGoldSwh!!.setOnCheckedChangeListener { compoundButton, checked ->
            card!!.isGold = checked
            context.refreshCardView()
        }
        descEdt!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                card!!.desc = editable.toString()
                context.hsCardView.refreshCardDesc()
            }
        })
        healthEdt!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                if (editable.toString().isEmpty()) {
                    return
                }
                val newHealth = Integer.valueOf(editable.toString())
                card!!.health = newHealth
                context.hsCardView.refreshCardHealth()
            }
        })
        attackEdt!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                if (editable.toString().isEmpty()) {
                    return
                }
                val newAttack = Integer.valueOf(editable.toString())
                card!!.attack = newAttack
                context.hsCardView.refreshCardAttack()
            }
        })
        raceEdt!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                card!!.race = editable.toString()
                context.hsCardView.refreshCardRace()

            }
        })
    }


    fun resetInput() {
        val editTexts = arrayOf<EditText>(nameEdt, costEdt, descEdt, healthEdt, attackEdt, raceEdt)
        for (editText in editTexts) {
            editText.setText("")
        }
        cardRaritySpn!!.setSelection(0)
        cardSetSpn!!.setSelection(0)
        playerClassSpn!!.setSelection(0)
        cardTypeSpn!!.setSelection(0)
        isGoldSwh!!.isChecked = false
        context.refreshCardView()

    }


    fun focusOn(fieldName: String) {
        val fieldNames = arrayOf("attack", "cost", "name", "set", "rarity", "race", "health", "desc")
        val views = arrayOf<View>(attackEdt, costEdt, nameEdt, cardSetSpn, cardRaritySpn, raceEdt, healthEdt, descEdt)
        val index = Arrays.asList(*fieldNames).indexOf(fieldName)
        val view = views[index]
        view.performClick()
        if (view is EditText) {
            view.requestFocus()
            KeyBoardUtil.openKeyBoard(context)
        }
    }
}
