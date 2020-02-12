package kr.co.ldcc.contentsservice.adpater

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.container_recyclerview_item.view.*
import kr.co.ldcc.contentsservice.R

class VerticalAdapter(context: Context) : RecyclerView.Adapter<VerticalAdapter.ViewHolder>() {
    private var context : Context

    init {
        this.context = context
    }
    // 2. 아이템 뷰를 저장하는 뷰홀더 클래스.
    // itemView에 리스너 생성
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var recyclerView : RecyclerView
        var buttonGrid : Button
        var buttonLinear : Button
        init {
            // 뷰 객체에 대한 참조. (hold strong reference)
            title = itemView.textView
            title.text="test1"
            recyclerView = itemView.recyclerViewHorizontal
            buttonGrid = itemView.buttonGrid
            buttonLinear = itemView.buttonLinear
            buttonGrid.setOnClickListener{
                recyclerView.layoutManager = GridLayoutManager(context, 3)
            }
            buttonLinear.setOnClickListener{
                recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false )
            }
        }
    }

    // 1. onCreateViewHolder() - 뷰홀더 객체 생성 및 리턴.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.container_recyclerview_item, parent, false)
        return ViewHolder(view)
    }

    // 3. onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.title.text= "test"
        var horizontalAdapter = HorizontalAdapter()
        holder.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder.recyclerView.adapter = horizontalAdapter
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    override fun getItemCount(): Int {
        return 4
    }

}


