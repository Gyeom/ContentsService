package kr.co.ldcc.contentsservice.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kr.co.ldcc.contentsservice.R

class BookmarkFragment : Fragment() {
    // TODO: Rename and change types of parameters

    companion object{
        fun newInstance() : BookmarkFragment{
//            val args = Bundle()
            val bookmarkFragment = BookmarkFragment()
//            bookmarkFragment.arguments = args
            return bookmarkFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }

}
