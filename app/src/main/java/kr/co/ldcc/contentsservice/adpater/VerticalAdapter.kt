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

class VerticalAdapter(context: Context, layoutVos: ArrayList<Any?>) : RecyclerView.Adapter<VerticalAdapter.ViewHolder>() {

    private val subjects : Array<String> = arrayOf("동영상","이미지","동영상/이미")
    private var context : Context
    private var layoutVos : ArrayList<Any?>

    init {
        this.context = context
        this.layoutVos = layoutVos
    }

    // 2. 아이템 뷰를 저장하는 뷰홀더 클래스.
    // itemView에 리스너 생성
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewTitle: TextView
        var recyclerView : RecyclerView
        var buttonGrid : Button
        var buttonLinear : Button
        init {
            // 뷰 객체에 대한 참조. (hold strong reference)
            textViewTitle = itemView.textViewTitle
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
        holder.textViewTitle.text = subjects[position]
        Log.d("test",subjects[position])

        var horizontalAdapter = HorizontalAdapter(layoutVos.get(position),position)


        holder.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder.recyclerView.adapter = horizontalAdapter
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    override fun getItemCount(): Int {
        Log.d("test","vertical size"+layoutVos.size)
        if(layoutVos==null) return 0
        return layoutVos.size
    }
    fun setLayoutVos(layoutVos : ArrayList<Any?>){
        this.layoutVos = layoutVos
    }
}


