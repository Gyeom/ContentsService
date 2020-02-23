package kr.co.ldcc.contentsservice.activity

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.ldcc.contentsservice.R
import kr.co.ldcc.contentsservice.adpater.ViewPagerAdapter
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pagerAdatper = ViewPagerAdapter(supportFragmentManager, tabLayout.tabCount).apply {
            viewPager.adapter = this
            tabLayout.setupWithViewPager(viewPager)
        }
        getAppKeyHash()
    }

    private fun getAppKeyHash() {
        try {
            val info =
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                var md: MessageDigest
                md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val something = String(Base64.encode(md.digest(), 0))
                Log.e("Hash key", something)
            }
        } catch (e: Exception) { // TODO Auto-generated catch block
            Log.e("name not found", e.toString())
        }
    }
}
