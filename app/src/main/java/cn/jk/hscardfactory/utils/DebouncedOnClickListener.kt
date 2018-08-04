package cn.jk.hscardfactory.utils

import android.os.SystemClock
import android.view.View
import java.util.*

/**
 * A Debounced OnClickListener
 * Rejects clicks that are too close together in time.
 * This class is safe to use as an OnClickListener for multiple views, and will debounce each one separately.
 */
abstract class DebouncedOnClickListener
/**
 * The one and only constructor
 *
 * @param minimumIntervalMsec The minimum allowed time between clicks - any click sooner than this after a previous click will be rejected
 */
@JvmOverloads constructor(private val minimumInterval: Long = 1000) : View.OnClickListener {
    private val lastClickMap: MutableMap<View, Long>

    init {
        this.lastClickMap = WeakHashMap()
    }

    /**
     * Implement this in your subclass instead of onClick
     *
     * @param v The view that was clicked
     */
    protected abstract fun onDebouncedClick(v: View)

    override fun onClick(clickedView: View) {
        val previousClickTimestamp = lastClickMap[clickedView]
        val currentTimestamp = SystemClock.uptimeMillis()

        lastClickMap[clickedView] = currentTimestamp
        if (previousClickTimestamp == null || Math.abs(currentTimestamp - previousClickTimestamp) > minimumInterval) {
            onDebouncedClick(clickedView)
        }
    }
}