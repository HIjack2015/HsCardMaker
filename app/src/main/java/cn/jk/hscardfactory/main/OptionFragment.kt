package cn.jk.hscardfactory.main


import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.jk.hscardfactory.R
import cn.jk.hscardfactory.gallery.GalleryActivity
import org.jetbrains.anko.startActivity

/**
 * A simple [Fragment] subclass.
 */
class OptionFragment : PreferenceFragment() {

    lateinit internal var context: MainActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.setting)
        context = activity as MainActivity

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        view!!.setBackgroundColor(ContextCompat.getColor(context, R.color.white80))
        setLogic()
        return view
    }

    private fun setLogic() {
        val copyRightPref = findPreference(context.getString(R.string.copyRightNoticePref))
        copyRightPref.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            showMessage(context.getString(R.string.copyright_notice_content))
            false
        }
        val useTipPref = findPreference(context.getString(R.string.useTipPref))
        useTipPref.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            showMessage(context.getString(R.string.use_tip_content))
            false
        }
        val galleryPref = findPreference(context.getString(R.string.gallery))
        galleryPref.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            startActivity<GalleryActivity>()
            false
        }
        val feedBackPref = findPreference(context.getString(R.string.feedback))
        feedBackPref.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            showMessage(context.getString(R.string.feedback_content))
            false
        }
    }

    fun showMessage(message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.confirm)) { dialog, id ->
                    //do things
                }
        val alert = builder.create()
        alert.show()
    }
}// Required empty public constructor
