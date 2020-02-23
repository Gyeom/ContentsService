package kr.co.ldcc.contentsservice.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kr.co.ldcc.contentsservice.R
import kr.co.ldcc.contentsservice.adpater.VerticalAdapter
import kr.co.ldcc.contentsservice.model.ContentVo
import kr.co.ldcc.contentsservice.model.ImageVo
import kr.co.ldcc.contentsservice.model.VideoVo
import kr.co.ldcc.contentsservice.model.ViewModel


class MainFragment : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var viewModel: ViewModel

    companion object {
        fun newInstance(): MainFragment {
//            val args = Bundle()
            val mainFragment = MainFragment()
//            mainFragment.arguments = args
            return mainFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var fragment: View = inflater.inflate(R.layout.fragment_main, container, false)

        linearLayoutManager = LinearLayoutManager(activity).apply {
            fragment.recyclerViewVertical.layoutManager = this
        }

        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        var adapter: VerticalAdapter? = null
        var layoutVos: ArrayList<Any?> = ArrayList(arrayListOf(null, null, null))

        viewModel.getAllVideoVo().observe(this, Observer<ArrayList<VideoVo>> {
            it?.let {
                layoutVos.set(0, it)
                adapter?.let { adapter ->
                    adapter.setLayoutVos(layoutVos)
                    adapter.notifyDataSetChanged()
                } ?: run {
                    adapter = VerticalAdapter(context!!, layoutVos)
                    recyclerViewVertical.adapter = adapter
                }
            }
        })

        viewModel.getAllImageVo().observe(this, Observer<ArrayList<ImageVo>> {
            it?.let {
                layoutVos.set(1, it)
                adapter?.let { adapter ->
                    adapter.setLayoutVos(layoutVos)
                    adapter.notifyDataSetChanged()
                } ?: run {
                    adapter = VerticalAdapter(context!!, layoutVos)
                    recyclerViewVertical.adapter = adapter
                }
            }
        })

        return fragment
    }
}