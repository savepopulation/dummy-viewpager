package com.raqun.dummyviewpager

import android.content.Context
import android.content.res.TypedArray
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import java.util.jar.Attributes

/**
 * Created by tyln on 30/11/2017.
 */
class DummyViewPager(context: Context)
    : ViewPager(context) {

    var slideShow: Boolean = DEFAULT_SLIDE_SHOW
    var duration: Int = DEFAULT_DURATION
    var canScroll: Boolean = DEFAULT_CAN_SCROLL
    var velocity: Int = DEFAULT_VELOCITY

    init {
        initComponent(null)
    }

    constructor(context: Context, attributeSet: AttributeSet?) : this(context) {
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.DummyViewPager)
        initComponent(attrs)
    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : this(context) {
        val attrs = context.theme.obtainStyledAttributes(attributeSet,
                R.styleable.DummyViewPager, defStyle, 0)
        initComponent(attrs)
    }

    private fun initComponent(attrs: TypedArray?) {
        attrs?.run {
            try {
                slideShow = getBoolean(R.styleable.DummyViewPager_slideShow, DEFAULT_SLIDE_SHOW)
                duration = getInt(R.styleable.DummyViewPager_duration, DEFAULT_DURATION)
                canScroll = getBoolean(R.styleable.DummyViewPager_canScroll, DEFAULT_CAN_SCROLL)
                velocity = getInt(R.styleable.DummyViewPager_velocity, DEFAULT_VELOCITY)
            } finally {
                recycle()
            }
        }
    }

    companion object {
        const val DEFAULT_SLIDE_SHOW = false
        const val DEFAULT_DURATION = 5000
        const val DEFAULT_CAN_SCROLL = true
        const val DEFAULT_VELOCITY = 300
    }
}