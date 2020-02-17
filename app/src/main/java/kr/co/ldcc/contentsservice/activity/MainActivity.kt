package kr.co.ldcc.contentsservice.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.ldcc.contentsservice.R
import kr.co.ldcc.contentsservice.adpater.ViewPagerAdapter
import kr.co.ldcc.contentsservice.databinding.ActivityMainBinding
import kr.co.ldcc.contentsservice.model.ViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pagerAdatper = ViewPagerAdapter(supportFragmentManager, tabLayout.tabCount)
        viewPager.adapter = pagerAdatper
        tabLayout.setupWithViewPager(viewPager)
    }
}
