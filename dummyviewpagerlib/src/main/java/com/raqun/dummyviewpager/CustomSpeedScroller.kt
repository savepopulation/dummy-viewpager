package com.raqun.dummyviewpager

import android.content.Context
import android.view.animation.Interpolator
import android.widget.Scroller

/**
 * Created by tyln on 4.12.2017.
 */
class CustomSpeedScroller constructor(context: Context,
                                      interpolator: Interpolator,
                                      private val velocity: Int)
    : Scroller(context, interpolator) {

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, velocity)
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
        super.startScroll(startX, startY, dx, dy, velocity)
    }
}