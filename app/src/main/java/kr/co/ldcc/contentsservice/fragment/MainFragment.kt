package kr.co.ldcc.contentsservice.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main.view.*
import kr.co.ldcc.contentsservice.R
import kr.co.ldcc.contentsservice.adpater.VerticalAdapter


class MainFragment : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager

    companion object{
        fun newInstance() : MainFragment
        {
//            val args = Bundle()
            val mainFragment = MainFragment()
//            mainFragment.arguments = args
            return mainFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var fragment : View = inflater.inflate(R.layout.fragment_main, container, false)

        linearLayoutManager = LinearLayoutManager(activity)
        fragment.recyclerViewVertical.layoutManager = linearLayoutManager
        fragment.recyclerViewVertical.adapter = VerticalAdapter(context!!)

        return fragment
    }
}
