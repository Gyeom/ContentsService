package kr.co.ldcc.contentsservice.fragment

import android.content.Context
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kr.co.ldcc.contentsservice.R
import kr.co.ldcc.contentsservice.adpater.VerticalAdapter
import kr.co.ldcc.contentsservice.databinding.ActivityMainBinding
import kr.co.ldcc.contentsservice.model.LayoutVo
import kr.co.ldcc.contentsservice.model.ViewModel


class MainFragment : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var viewModel: ViewModel
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

        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        viewModel.getAll().observe(this, Observer<LayoutVo> { v ->

        })

        container!!.buttonSearch.setOnClickListener(View.OnClickListener {

        })

        return fragment
    }
}