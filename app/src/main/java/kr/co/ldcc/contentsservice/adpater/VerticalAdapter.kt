package kr.co.ldcc.contentsservice.adpater

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.container_recyclerview_item.view.*
import kotlinx.android.synthetic.main.fragment_main.*
import kr.co.ldcc.contentsservice.R
import kr.co.ldcc.contentsservice.etc.DisplayMetric
import kr.co.ldcc.contentsservice.model.vo.ContentVo
import kr.co.ldcc.contentsservice.model.vo.ImageVo
import kr.co.ldcc.contentsservice.model.Type
import kr.co.ldcc.contentsservice.model.vo.VideoVo


class VerticalAdapter(context: Context, layoutVos: ArrayList<Any?>) :
    RecyclerView.Adapter<VerticalAdapter.ViewHolder>() {

    val LAYOUT_SIZE: Int

    init {
        LAYOUT_SIZE = 3 // 동영상, 이미지, 동영상/이미지
    }

    private val subjects: Array<String> = arrayOf("동영상", "이미지", "동영상/이미지")
    private val context: Context
    private var layoutVos: ArrayList<Any?>
    private var videoVos: ArrayList<VideoVo>? = null
    private var imageVos: ArrayList<ImageVo>? = null
    private var contentVos: ArrayList<ContentVo>

    init {
        this.context = context
        this.layoutVos = layoutVos
        this.contentVos = ArrayList()
        combineVideoAndImage()
    }

    private fun combineVideoAndImage() {
        contentVos.clear()

        layoutVos.get(Type.VIDEO.ordinal)?.let {
            videoVos = it as ArrayList<VideoVo>
        }
        layoutVos.get(Type.IMAGE.ordinal)?.let {
            imageVos = it as ArrayList<ImageVo>
        }

        videoVos?.let {
            it.forEach { videoVo ->
                contentVos.add(
                    ContentVo(
                        videoVo,
                        Type.VIDEO
                    )
                )
            }
        } ?: run {
            return
        }

        imageVos?.let {
            it.forEach { imageVo ->
                contentVos.add(
                    ContentVo(
                        imageVo,
                        Type.IMAGE
                    )
                )
            }
        } ?: run {
            return
        }

        contentVos.sortWith(Comparator { o1, o2 ->
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
        layoutVos.set(Type.CONTENT.ordinal, contentVos)
    }


    // 2. 아이템 뷰를 저장하는 뷰홀더 클래스.
    // itemView에 리스너 생성
    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var textViewTitle: TextView
        var recyclerView: RecyclerView
        var buttonGrid: Button
        var buttonLinear: Button

        // init displayMetric
        val displayMetric: DisplayMetric = DisplayMetric(context)
        val height: Int = displayMetric.height
        val width: Int = displayMetric.width

        init {
            // 뷰 객체에 대한 참조. (hold strong reference)
            textViewTitle = itemView.textViewTitle
            recyclerView = itemView.recyclerViewHorizontal
            buttonGrid = itemView.buttonGrid
            buttonLinear = itemView.buttonLinear

            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.layoutParams.height = height / 7

            buttonGrid.setOnClickListener {
                recyclerView.layoutManager = GridLayoutManager(context, 3)
                recyclerView.layoutParams.height = (height / 1.8).toInt()
            }
            buttonLinear.setOnClickListener {
                recyclerView.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                recyclerView.layoutParams.height = height / 7
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
        // init displayMetric
        val displayMetric = DisplayMetric(context)
        val height: Int = displayMetric.height

        holder.textViewTitle.text = subjects[position]
        holder.recyclerView.adapter?.let {
            (it as HorizontalAdapter).layoutVo = layoutVos.get(position)
            holder.recyclerView.layoutParams.height = height / 7
            it.type = when (position) {
                0 -> Type.VIDEO
                1 -> Type.IMAGE
                2 -> Type.CONTENT
                3 -> Type.BOOKMARK
                else -> throw NoSuchFieldException()
            }
            it.notifyDataSetChanged()
        } ?: run {
            holder.recyclerView.adapter =
                HorizontalAdapter(layoutVos.get(position), position, context)
        }
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    override fun getItemCount(): Int {
        return LAYOUT_SIZE
    }

    fun setLayoutVos(layoutVos: ArrayList<Any?>) {
        this.layoutVos = layoutVos
        combineVideoAndImage()
    }
}