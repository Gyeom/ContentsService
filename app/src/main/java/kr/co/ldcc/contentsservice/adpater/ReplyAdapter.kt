package kr.co.ldcc.contentsservice.adpater

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.reply_recyclerview_item.view.*
import kr.co.ldcc.contentsservice.R
import kr.co.ldcc.contentsservice.model.vo.ReplyVo

class ReplyAdapter(context: Context, replyVos : ArrayList<ReplyVo>) : RecyclerView.Adapter<ReplyAdapter.ViewHolder>(){


    val context: Context
    var replyVos: ArrayList<ReplyVo>

    init{
        this.context = context
        this.replyVos = replyVos
    }

    inner class ViewHolder internal constructor(itemView : View) : RecyclerView.ViewHolder(itemView){
        var textViewReplyComment : TextView
        var textViewUserId : TextView
        var imageViewProfile : ImageView

        init{
            textViewUserId = itemView.textViewUserId
            textViewReplyComment = itemView.textViewReplyComment
            imageViewProfile = itemView.imageViewProfile
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.reply_recyclerview_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var displayMetrics: DisplayMetrics =
            context.applicationContext.resources.displayMetrics
        var width: Int = displayMetrics.widthPixels
        var height: Int = displayMetrics.heightPixels

        var replyVo : ReplyVo = replyVos.get(position)
        holder.textViewUserId.text = replyVo.userId
        holder.textViewReplyComment.text = replyVo.comment
        Glide.with(holder.imageViewProfile.getContext()).load(replyVo.profile)
            .override(width/8,height/10)
            .into(holder.imageViewProfile) //Glide을 이용해서 이미지뷰에 url에 있는 이미지를 세팅해줌
    }

    override fun getItemCount(): Int {
        return replyVos.size
    }
}