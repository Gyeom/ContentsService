package kr.co.ldcc.contentsservice.adpater

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.container_recyclerview_item.view.*
import kr.co.ldcc.contentsservice.R
import kr.co.ldcc.contentsservice.model.ContentVo
import kr.co.ldcc.contentsservice.model.ImageVo
import kr.co.ldcc.contentsservice.model.Type
import kr.co.ldcc.contentsservice.model.VideoVo


class VerticalAdapter(context: Context, layoutVos: ArrayList<Any?>) :
    RecyclerView.Adapter<VerticalAdapter.ViewHolder>() {

    private val subjects: Array<String> = arrayOf("동영상", "이미지", "동영상/이미지")
    private var context: Context
    private var layoutVos: ArrayList<Any?>

    init {
        this.context = context
        this.layoutVos = layoutVos
        var anyVos: ArrayList<ContentVo> = ArrayList()
        if (layoutVos.get(0)!=null&&layoutVos.get(1)!=null) {
            Log.d("test",anyVos.size.toString())

            (layoutVos.get(0) as ArrayList<VideoVo>).forEach {
                anyVos.add(ContentVo(it,Type.VIDEO))
            }

            (layoutVos.get(1) as ArrayList<ImageVo>).forEach {
                anyVos.add(ContentVo(it,Type.IMAGE))
            }
//            anyVos.addAll(layoutVos.get(0) as ArrayList<VideoVo>)
//            Log.d("test",anyVos.size.toString())
//
//            anyVos.addAll(layoutVos.get(1) as ArrayList<ImageVo>)
//            Log.d("test",anyVos.size.toString())

            Log.d("test","비디오 :"+layoutVos.get(0)!!.toString())
            Log.d("test",anyVos.size.toString())
            Log.d("test","1"+anyVos.toString())
            anyVos.sortWith(Comparator { o1, o2 ->
                if ((o1.item is VideoVo) && (o2.item is ImageVo)) {
                    ((o1.item as VideoVo).datetime).compareTo((o2.item as ImageVo).datetime)
                } else if ((o1.item is VideoVo) && (o2.item is VideoVo)) {
                    ((o1.item as VideoVo).datetime).compareTo((o2.item as VideoVo).datetime)
                } else if ((o1.item is ImageVo) && (o2.item is VideoVo)) {
                    ((o1.item as ImageVo).datetime).compareTo((o2.item as VideoVo).datetime)
                } else if ((o1.item is ImageVo) && (o2.item is ImageVo)) {
                    ((o1.item as ImageVo).datetime).compareTo((o2.item as ImageVo).datetime)
                } else {
                    throw Exception()
                }
            })

//            anyVos.forEach {
//                when(it.type){
//                    Type.VIDEO -> Log.d("test",(it.item as VideoVo).datetime)
//                    Type.IMAGE -> Log.d("test",(it.item as ImageVo).datetime)
//                }
//            }
            layoutVos.set(2,anyVos)

            Log.d("test","2"+anyVos.toString())
        }
    }

    // 2. 아이템 뷰를 저장하는 뷰홀더 클래스.
    // itemView에 리스너 생성
    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var textViewTitle: TextView
        var recyclerView: RecyclerView
        var buttonGrid: Button
        var buttonLinear: Button
        var displayMetrics : DisplayMetrics = context.applicationContext.resources.displayMetrics;
        var height : Int = displayMetrics.heightPixels;


        init {
            // 뷰 객체에 대한 참조. (hold strong reference)
            textViewTitle = itemView.textViewTitle
            recyclerView = itemView.recyclerViewHorizontal
            buttonGrid = itemView.buttonGrid
            buttonLinear = itemView.buttonLinear
            buttonGrid.setOnClickListener {
                recyclerView.layoutManager = GridLayoutManager(context, 3)
                recyclerView.layoutParams.height=height/3

            }
            buttonLinear.setOnClickListener {
                recyclerView.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                recyclerView.layoutParams.height=height/7

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
        Log.d("test", subjects[position])



        holder.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


        var horizontalAdapter :HorizontalAdapter? = null
        if(horizontalAdapter == null){
            horizontalAdapter = HorizontalAdapter(layoutVos.get(position), position, context)
        }else{
            horizontalAdapter.layoutVo = layoutVos.get(position)
            horizontalAdapter.notifyDataSetChanged()
        }


        holder.recyclerView.adapter = horizontalAdapter
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    override fun getItemCount(): Int {
        return 3;
    }

    fun setLayoutVos(layoutVos: ArrayList<Any?>) {
        this.layoutVos = layoutVos
    }
}


