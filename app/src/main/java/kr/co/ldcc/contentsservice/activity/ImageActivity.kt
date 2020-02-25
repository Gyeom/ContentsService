package kr.co.ldcc.contentsservice.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_image.*
import kr.co.ldcc.contentsservice.R
import kr.co.ldcc.contentsservice.adpater.ReplyAdapter
import kr.co.ldcc.contentsservice.model.viewmodel.ReplyViewModel
import kr.co.ldcc.contentsservice.model.vo.ReplyVo

class ImageActivity : AppCompatActivity() {
    lateinit var replyViewModel: ReplyViewModel
    lateinit var sharedPreferences: SharedPreferences

    var userId: String = "사용자 아이디"
    var profile: String = "사용자 프로필"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        val intent: Intent = getIntent()
        var thumbnail: String = intent.getStringExtra("thumbnail")
        var displayMetrics: DisplayMetrics = applicationContext.resources.displayMetrics;
        var width: Int = displayMetrics.widthPixels;
        var height: Int = displayMetrics.heightPixels;
        var contentId = thumbnail.substring(thumbnail.lastIndexOf("/") + 1)

        // SharedPreferences 불러오
        initSharedPreferences()

        val buttonReplyWrite = findViewById(R.id.buttonReplyWrite) as Button
        replyViewModel = ViewModelProviders.of(this).get(ReplyViewModel::class.java)

        Glide.with(applicationContext)
            .load(thumbnail)
            .apply(RequestOptions().override(width, height / 3))
            .into(imageView)
        var linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewReply.layoutManager = linearLayoutManager
        var replyAdapter: ReplyAdapter? = null

        buttonReplyWrite.setOnClickListener {
            Log.d("test", "click")
            replyViewModel.insert(ReplyVo(userId, profile, "test", contentId))
        }

        replyViewModel.getAll(contentId)?.let {
            it.observe(this, Observer<List<ReplyVo>> { replyVos ->
                Log.d("test", replyVos.toString())
                Log.d("test", contentId)
//                Log.d("test", replyVos.get(0).userId + "111")
//
//                Log.d("test", replyVos.get(0).profile + "112")
                replyAdapter?.let { replyAdapter ->
                    replyAdapter.replyVos = ArrayList(replyVos)
                    replyAdapter.notifyDataSetChanged()
                } ?: run {
                    replyAdapter = ReplyAdapter(this, ArrayList(replyVos))
                    recyclerViewReply.adapter = replyAdapter
                }
            })
        }
    }

    private fun initSharedPreferences() {
        sharedPreferences = getSharedPreferences("sFile", MODE_PRIVATE)

        sharedPreferences.getString("userId", "")?.let {
            userId = it
        }
        sharedPreferences.getString("profile", "")?.let {
            profile = it
        }
    }
}