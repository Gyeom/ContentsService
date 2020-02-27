package kr.co.ldcc.contentsservice.activity

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import kr.co.ldcc.contentsservice.R
import kr.co.ldcc.contentsservice.adpater.HorizontalAdapter
import kr.co.ldcc.contentsservice.adpater.VerticalAdapter
import kr.co.ldcc.contentsservice.adpater.ViewPagerAdapter
import kr.co.ldcc.contentsservice.model.Type
import kr.co.ldcc.contentsservice.model.viewmodel.SearchViewModel
import kr.co.ldcc.contentsservice.model.vo.ImageVo
import kr.co.ldcc.contentsservice.model.vo.VideoVo
import java.security.MessageDigest
import java.util.zip.Inflater


class MainActivity : AppCompatActivity() {

    private lateinit var searchViewModel: SearchViewModel

    var adapter: VerticalAdapter? = null
    var layoutVos: ArrayList<Any?>

    init {
        layoutVos = ArrayList(arrayListOf(null, null, null))
    }

    companion object {
        lateinit var userId: String
        lateinit var profile: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent: Intent = getIntent()
        initIntent(intent)

        // SharedPreferences를 sFile이름, 기본모드로 설정
        setSharedPreferences()

        // TabLayout & ViewPager
        initPager()

        // init ViewModel
        initViewModel()

        // init Observer
        initObserver()

        // init ButtonListener
        initButtonListener()

        getAppKeyHash()
    }

    private fun initButtonListener() {
        buttonSearch.setOnClickListener {
            searchViewModel.getAllImageVo(editTextSearch.text.toString())
            searchViewModel.getAllVideoVo(editTextSearch.text.toString())
        }
    }

    private fun initObserver() {
        searchViewModel.getAllVideoVo(editTextSearch.text.toString())
            .observe(this, Observer<ArrayList<VideoVo>> {
                it?.let {
                    layoutVos.set(Type.VIDEO.ordinal, it)
                    adapter?.let { adapter ->
                        adapter.setLayoutVos(layoutVos)
                        adapter.notifyDataSetChanged()
                    } ?: run {
                        adapter = VerticalAdapter(this, layoutVos)
                        recyclerViewVertical.adapter = adapter
                    }
                }
            })

        searchViewModel.getAllImageVo(editTextSearch.text.toString())
            .observe(this, Observer<ArrayList<ImageVo>> {
                it?.let {
                    layoutVos.set(Type.IMAGE.ordinal, it)
                    adapter?.let { adapter ->
                        adapter.setLayoutVos(layoutVos)
                        adapter.notifyDataSetChanged()
                    } ?: run {
                        adapter = VerticalAdapter(this, layoutVos)
                        recyclerViewVertical.adapter = adapter
                    }
                }
            })
    }

    private fun initViewModel() {
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
    }

    private fun initPager() {
        ViewPagerAdapter(supportFragmentManager, tabLayout.tabCount).apply {
            viewPager.adapter = this
            tabLayout.setupWithViewPager(viewPager)
        }
    }

    private fun setSharedPreferences() {
        var sharedPreferences: SharedPreferences = getSharedPreferences("sFile", MODE_PRIVATE)
        var editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("userId", userId)
        editor.putString("profile", profile)
        editor.commit()
        editor.apply()
    }

    private fun initIntent(intent: Intent) {
        userId = intent.getStringExtra("userId")
        profile = intent.getStringExtra("profile")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.buttonLogout -> {
                Toast.makeText(applicationContext, "정상적으로 로그아웃되었습니다.", Toast.LENGTH_SHORT)
                    .show()
                UserManagement.getInstance().requestLogout(object : LogoutResponseCallback() {
                    override fun onCompleteLogout() {
                        val intent = Intent(this@MainActivity, LoginActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                    }
                })
            }
        }
        return super.onOptionsItemSelected(item)    }

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
