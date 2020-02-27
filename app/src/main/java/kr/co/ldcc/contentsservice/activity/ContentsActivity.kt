package kr.co.ldcc.contentsservice.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_contents.*
import kr.co.ldcc.contentsservice.R
import kr.co.ldcc.contentsservice.adpater.ReplyAdapter
import kr.co.ldcc.contentsservice.etc.DisplayMetric
import kr.co.ldcc.contentsservice.fragment.ReplyDialogFragment
import kr.co.ldcc.contentsservice.model.viewmodel.BookmarkViewModel
import kr.co.ldcc.contentsservice.model.viewmodel.ReplyViewModel
import kr.co.ldcc.contentsservice.model.vo.BookmarkVo
import kr.co.ldcc.contentsservice.model.vo.ReplyVo

class ContentsActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var replyViewModel: ReplyViewModel
    lateinit var bookmarkViewModel: BookmarkViewModel
    lateinit var sharedPreferences: SharedPreferences
    lateinit var thumbnail: String
    lateinit var contentType: String
    lateinit var datetime: String
    lateinit var url: String
    lateinit var title: String
    lateinit var contentId: String
    lateinit var userId: String
    lateinit var profile: String
    lateinit var displayMetric: DisplayMetric


    var replyAdapter: ReplyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contents)

        var intent = getIntent()
        initIntent(intent)

        // init displayMetric
        displayMetric = DisplayMetric(applicationContext)


        // init Listener
        initButtonListener()


        // SharedPreferences
        initSharedPreferences()

        // init ViewModel
        initViewModel()

        // Observer
        initObserver()

        // init Contents Info
        initContentsInfo()
    }

    private fun initButtonListener() {
        buttonBookmark.setOnClickListener(this)
        textViewBookmark.setOnClickListener(this)
        buttonReplyWrite.setOnClickListener(this)
        textViewReplyWrite.setOnClickListener(this)

    }

    private fun initContentsInfo() {
        Glide.with(applicationContext)
            .load(thumbnail)
            .apply(
                RequestOptions().override(
                    displayMetric.width,
                    displayMetric.height / 3
                )
            )
            .into(imageView)
        var linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewReply.layoutManager = linearLayoutManager
    }

    private fun initObserver() {
        bookmarkViewModel.getOne(userId, contentId).observe(this, Observer {
            it?.let {
                buttonBookmark.isSelected = true
            } ?: run {
                buttonBookmark.isSelected = false
            }
        })

        replyViewModel.getAll(contentId)?.let {
            it.observe(this, Observer<List<ReplyVo>> { replyVos ->

                textViewReplyCount.text = replyVos.size.toString()
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

    override fun onClick(v: View) {
        if (v.id == R.id.buttonBookmark || v.id == R.id.textViewBookmark) {
            if (buttonBookmark.isSelected == true) {
                bookmarkViewModel.delete(userId, contentId)
                buttonBookmark.isSelected = false
            } else {
                bookmarkViewModel.insert(
                    BookmarkVo(
                        title,
                        thumbnail,
                        url,
                        datetime,
                        userId,
                        contentId
                    )
                )
                buttonBookmark.isSelected = true
            }
        }

        else if (v.id == R.id.buttonReplyWrite || v.id == R.id.textViewReplyWrite) {
            val replyDialogFragment = ReplyDialogFragment(this, contentId, replyViewModel)
            val manager = supportFragmentManager
            replyDialogFragment.show(manager, "replyDialog")
        }
    }

    private fun initViewModel() {
        bookmarkViewModel = ViewModelProviders.of(this).get(BookmarkViewModel::class.java)
        replyViewModel = ViewModelProviders.of(this).get(ReplyViewModel::class.java)
    }

    private fun initIntent(intent: Intent) {
        thumbnail = intent.getStringExtra("thumbnail")
        contentType = intent.getStringExtra("contentType")
        datetime = intent.getStringExtra("datetime")

        intent.getStringExtra("title")?.let {
            title = it
        } ?: run {
            title = ""
        }
        intent.getStringExtra("url")?.let {
            url = it
        } ?: run {
            url = "url"
        }

        contentId = when (contentType) {
            "Bookmark" -> intent.getStringExtra("contentId")
            "Image" -> thumbnail.substring(thumbnail.lastIndexOf("/") + 1)
            "Video" -> url.substring(url.lastIndexOf("v=") + 2)
            else -> throw NoSuchFieldError()
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