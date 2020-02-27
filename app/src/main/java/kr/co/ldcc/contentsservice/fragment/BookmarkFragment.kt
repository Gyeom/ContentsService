package kr.co.ldcc.contentsservice.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.container_recyclerview_item.view.*
import kotlinx.android.synthetic.main.fragment_bookmark.*
import kotlinx.android.synthetic.main.fragment_bookmark.view.*
import kr.co.ldcc.contentsservice.R
import kr.co.ldcc.contentsservice.adpater.HorizontalAdapter
import kr.co.ldcc.contentsservice.model.viewmodel.BookmarkViewModel
import kr.co.ldcc.contentsservice.model.vo.BookmarkVo

class BookmarkFragment : Fragment() {

    lateinit var bookmarkViewModel: BookmarkViewModel
    lateinit var sharedPreferences: SharedPreferences
    lateinit var bookmarkFragmentView: View
    lateinit var userId: String
    lateinit var profile: String

    var adapter: HorizontalAdapter? = null

    companion object {
        fun newInstance(): BookmarkFragment {
//            val args = Bundle()
            val bookmarkFragment = BookmarkFragment()
//            bookmarkFragment.arguments = args
            return bookmarkFragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // initViewModel
        initViewModel()

        // inflate fragment_bookmark.xml
        initView(inflater, container)

        // initSharedPreferences
        initSharedPreferences()

        // initObserver
        initObserver()

        return bookmarkFragmentView
    }

    private fun initViewModel() {
        bookmarkViewModel = ViewModelProviders.of(this).get(BookmarkViewModel::class.java)
    }

    private fun initObserver() {
        bookmarkViewModel.getAll(userId)?.let {
            it.observe(this, Observer<List<BookmarkVo>> { bookmarkVos ->
                adapter?.let {
                    it.layoutVo = bookmarkVos
                    it.notifyDataSetChanged()
                    textViewBookmarkCount.text = bookmarkVos.size.toString()
                } ?: run {
                    adapter = HorizontalAdapter(bookmarkVos, 3, context!!)
                    bookmarkFragmentView.recyclerViewBookmark.adapter = adapter
                }
            })
        }
    }

    private fun initView(inflater: LayoutInflater, container: ViewGroup?) {
        bookmarkFragmentView = inflater.inflate(R.layout.fragment_bookmark, container, false)
        val gridLayoutManager = GridLayoutManager(context, 3)
        bookmarkFragmentView.recyclerViewBookmark.layoutManager = gridLayoutManager
    }

    override fun onResume() {
        super.onResume()
        bookmarkViewModel.getAll(userId)?.let {
            it.observe(this, Observer<List<BookmarkVo>> { bookmarkVos ->
                textViewBookmarkCount.text = bookmarkVos.size.toString()
                adapter?.let {
                    it.layoutVo = bookmarkVos
                    it.notifyDataSetChanged()
                } ?: run {
                    adapter = HorizontalAdapter(bookmarkVos, 3, context!!)
                    bookmarkFragmentView.recyclerViewBookmark.adapter = adapter
                }
            })
        }
    }

    private fun initSharedPreferences() {
        activity?.let {
            sharedPreferences = it.getSharedPreferences("sFile", AppCompatActivity.MODE_PRIVATE)
            sharedPreferences.getString("userId", "")?.let {
                userId = it
            }
            sharedPreferences.getString("profile", "")?.let {
                profile = it
            }
        }
    }
}
