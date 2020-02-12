package kr.co.ldcc.contentsservice.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.ldcc.contentsservice.R
import kr.co.ldcc.contentsservice.adpater.ViewPagerAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pagerAdatper = ViewPagerAdapter(supportFragmentManager, tabLayout.tabCount)
        viewPager.adapter = pagerAdatper
        tabLayout.setupWithViewPager(viewPager)


    }
}
