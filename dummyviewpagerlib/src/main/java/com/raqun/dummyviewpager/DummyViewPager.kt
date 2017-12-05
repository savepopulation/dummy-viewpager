package com.raqun.dummyviewpager

import android.annotation.TargetApi
import android.content.Context
import android.content.res.TypedArray
import android.os.Build
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
                    val slideShow = it.getBoolean(R.styleable.DummyViewPager_slideShow, DEFAULT_SLIDE_SHOW)
                    val duration = it.getInt(R.styleable.DummyViewPager_duration, DEFAULT_DURATION)
                    if (slideShow) startSliding(duration)
                }
            } finally {
                typedArray.recycle()
            }
        }
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean = canScroll && super.onTouchEvent(ev)

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean = canScroll && super.onInterceptTouchEvent(ev)

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

    fun enableScrolling() {
        canScroll = true
    }

    fun disableScrolling() {
        canScroll = false
    }

    fun startSliding(duration: Int = DEFAULT_DURATION) {
        if (mode == Mode.SLIDESHOW) {
            throw IllegalStateException("ViewPager is already sliding..")
        }

        mode = Mode.SLIDESHOW

        // TODO create a timer and start
    }

    fun stopSliding() {
        if (mode != Mode.SLIDESHOW) {
            throw IllegalStateException("ViewPager is not sliding..")
        }

        mode = Mode.DEFAULT

        // TODO Remove timer
    }

    companion object {
        // DEFAULT VALUES
        private const val DEFAULT_SLIDE_SHOW = false
        private const val DEFAULT_DURATION = 5000
        private const val DEFAULT_CAN_SCROLL = true
        private const val DEFAULT_VELOCITY = 0

        // OTHER
        private const val SCROLLER_FIELD_NAME = "mScroller"
    }

    enum class Mode {
        DEFAULT, SLIDESHOW
    }
}