package com.raqun.dummyviewpager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.raqun.dummyviewpager.sample.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val customDummyViewPager = DummyViewPager(this)
        val dummyViewPager = findViewById<DummyViewPager>(R.id.dummy_viewpager)

    }
}
