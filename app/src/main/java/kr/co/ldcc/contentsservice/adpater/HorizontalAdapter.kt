package kr.co.ldcc.contentsservice.adpater

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.contents_recyclerview_item.view.*
import kr.co.ldcc.contentsservice.R
import kr.co.ldcc.contentsservice.model.ImageVo
import kr.co.ldcc.contentsservice.model.VideoVo

class HorizontalAdapter(layoutVo: Any?, position: Int) : RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder>() {

    var layoutVo : Any?
    enum class Type{
        Video, Image
    }
    var type : Type

    init {
        this.layoutVo = layoutVo
        if(position==0){
            this.type=Type.Video
        }else{
            this.type=Type.Image
        }
    }

    inner class HorizontalViewHolder internal  constructor(itemView : View) : RecyclerView.ViewHolder(itemView){
        private var imageViewContent : ImageView
        private var textViewTitle : TextView
        init {
            imageViewContent = itemView.imageViewContents
            textViewTitle = itemView.textViewTitle
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
        val context = parent.context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.contents_recyclerview_item, parent, false)
        return HorizontalViewHolder(view)
    }

    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) {
        if(type==Type.Video) {
            holder.itemView.textViewTitle.text = (layoutVo as ArrayList<VideoVo>).get(position).title
//            holder.itemView.imageViewContents.
        }else{
        }


    }

    override fun getItemCount(): Int {
        if(layoutVo == null) return 0
        if(type==Type.Video) {
            Log.d("Test",("video size")+((layoutVo as ArrayList<VideoVo>).size))
            return (layoutVo as ArrayList<VideoVo>).size
        }else{
            return (layoutVo as ArrayList<ImageVo>).size
        }
    }
}