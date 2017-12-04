package com.raqun.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.raqun.dummyviewpager.DummyViewPager

class MainActivity : AppCompatActivity() {

    val items = intArrayOf(1, 2, 3, 4, 5, 6)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<DummyViewPager>(R.id.pager)?.apply {
            velocity = 5000
            //disableScrolling()
            adapter = PagerAdapter()
        }
    }

    inner class PagerAdapter : FragmentStatePagerAdapter(supportFragmentManager) {

        override fun getItem(position: Int): Fragment = PagerFragment.newInstance(position)

        override fun getCount() = items.size
    }

    class PagerFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            return inflater?.inflate(R.layout.pager_item, null, false)
        }

        override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            view?.findViewById<TextView>(R.id.title)?.text = arguments?.getInt("pos", 0).toString()
        }

        companion object {
            fun newInstance(pos: Int) = PagerFragment().apply {
                val args = Bundle()
                args.putInt("pos", pos)
                arguments = args
            }
        }
    }
}
