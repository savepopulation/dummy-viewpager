package com.raqun.dummyviewpager

import android.annotation.TargetApi
import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.os.Handler
import android.support.v4.content.res.TypedArrayUtils.getBoolean
import android.support.v4.content.res.TypedArrayUtils.getInt
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.LinearInterpolator
import java.lang.reflect.Field
import java.util.jar.Attributes

/**
 * Created by tyln on 30/11/2017.
 */
class DummyViewPager @JvmOverloads constructor(context: Context,
                                               attrs: AttributeSet? = null,
                                               defStyle: Int = 0,
                                               defStyleRes: Int = 0)
    : ViewPager(context, attrs) {

    /**
     * ViewPager Mode
     * DEFAULT, SLIDESHOW
     */
    private var mode: Mode = Mode.DEFAULT

    /**
     * ViewPager can scroll or not
     */
    private var canScroll: Boolean = DEFAULT_CAN_SCROLL

    /**
     * ViewPager Page Change Duration
     */
    private var duration: Int = DEFAULT_DURATION

    /**
     * Slider Handler
     */
    private var slideHandler: Handler? = null

    /**
     * Page Slider
     */
    private val pageSlider = object : Runnable {
        override fun run() {
            if (isLastItem()) {
                currentItem = 0
            } else {
                currentItem += 1
            }
            slideHandler?.postDelayed(this, duration.toLong())
        }
    }

    /**
     * ViewPageR scroll speed
     */
    var velocity: Int = DEFAULT_VELOCITY
        set(value) {
            field = value
            initVelocity()
        }

    init {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DummyViewPager, defStyle, defStyleRes)
            try {
                typedArray.let {
                    canScroll = it.getBoolean(R.styleable.DummyViewPager_canScroll, DEFAULT_CAN_SCROLL)
                    velocity = it.getInt(R.styleable.DummyViewPager_velocity, DEFAULT_VELOCITY)
                    duration = it.getInt(R.styleable.DummyViewPager_duration, DEFAULT_DURATION)
                }
            } finally {
                typedArray.recycle()
            }
        }
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean = canScroll && super.onTouchEvent(ev)

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean = canScroll && super.onInterceptTouchEvent(ev)

    fun enableScrolling() {
        canScroll = true
    }

    fun disableScrolling() {
        canScroll = false
    }

    fun startSliding(duration: Int) {
        checkPagerForSliding()
        if (duration <= MIN_DURATION) {
            throw IllegalArgumentException("Duration cannot be less than min duration!")
        }

        this.duration = duration
        slide()
    }

    fun startSliding() {
        checkPagerForSliding()
        slide()
    }

    fun stopSliding() {
        if (mode != Mode.SLIDESHOW) {
            throw IllegalStateException("ViewPager is not sliding..")
        }

        mode = Mode.DEFAULT
        slideHandler?.removeCallbacks(pageSlider)
        slideHandler = null
    }

    private fun isLastItem() = currentItem == adapter?.count!! - 1

    private fun slide() {
        mode = Mode.SLIDESHOW
        slideHandler = Handler().also {
            it.postDelayed(pageSlider, duration.toLong())
        }
    }

    private fun checkPagerForSliding() {
        if (mode == Mode.SLIDESHOW) {
            throw IllegalStateException("ViewPager is already sliding..")
        }

        if (adapter == null || adapter.count == 0) {
            throw IllegalArgumentException("Nothing to slide!")
        }
    }

    private fun initVelocity() {
        if (velocity > 0) {
            addCustomSpeedScroller()
        }
    }

    private fun addCustomSpeedScroller() {
        try {
            ViewPager::class.java.getDeclaredField(SCROLLER_FIELD_NAME)?.apply {
                isAccessible = true
            }.also {
                it?.set(this, CustomSpeedScroller(context, LinearInterpolator(), velocity))
            }
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    companion object {
        // DEFAULT VALUES
        private const val DEFAULT_DURATION = 5000
        private const val DEFAULT_CAN_SCROLL = true
        private const val DEFAULT_VELOCITY = 0

        // MIN VALUES
        private const val MIN_DURATION = 100

        // OTHER
        private const val SCROLLER_FIELD_NAME = "mScroller"
    }

    enum class Mode {
        DEFAULT, SLIDESHOW
    }
}