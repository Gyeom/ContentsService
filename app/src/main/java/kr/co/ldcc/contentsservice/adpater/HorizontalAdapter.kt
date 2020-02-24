package kr.co.ldcc.contentsservice.adpater

import android.content.Context
import android.content.Intent
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.contents_recyclerview_item.view.*
import kr.co.ldcc.contentsservice.R
import kr.co.ldcc.contentsservice.activity.ImageActivity
import kr.co.ldcc.contentsservice.activity.VideoActivity
import kr.co.ldcc.contentsservice.model.ContentVo
import kr.co.ldcc.contentsservice.model.ImageVo
import kr.co.ldcc.contentsservice.model.Type
import kr.co.ldcc.contentsservice.model.VideoVo

class HorizontalAdapter(
    layoutVo: Any?,
    position: Int,
    context: Context
) : RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder>() {

    var layoutVo: Any?
    var type: Type
    var context: Context

    init {
        this.layoutVo = layoutVo
        this.context = context
        this.type = Type.values().get(position)
    }

    inner class HorizontalViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private var imageViewContent: ImageView
        private var textViewTitle: TextView

        init {
            imageViewContent = itemView.imageViewContents
            textViewTitle = itemView.textViewTitle

            itemView.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_UP) itemView.parent.parent.requestDisallowInterceptTouchEvent(
                    false
                ) else {
                    itemView.parent.parent.requestDisallowInterceptTouchEvent(true)
                }
                false
            }

            itemView.setOnClickListener {
                var position: Int = adapterPosition
                var thumbnail: String
                if (position != RecyclerView.NO_POSITION) {
                    if (type.equals(Type.IMAGE)) {
                        thumbnail = (layoutVo as ArrayList<ImageVo>).get(position).thumbnail_url
                        val intent = Intent(context, ImageActivity::class.java)
                        intent.putExtra("thumbnail", thumbnail)
                        it.context.startActivity(intent)
                    } else if (type.equals(Type.VIDEO)) {
                        thumbnail = (layoutVo as ArrayList<VideoVo>).get(position).thumbnail
                        val intent = Intent(context, VideoActivity::class.java)
                        intent.putExtra("thumbnail", thumbnail)
                        it.context.startActivity(intent)
                    } else {
                        if ((layoutVo as ArrayList<ContentVo>).get(position).type.equals(Type.VIDEO)) {
                            thumbnail =
                                ((layoutVo as ArrayList<ContentVo>).get(position).item as VideoVo).thumbnail
                            val intent = Intent(context, VideoActivity::class.java)
                            intent.putExtra("thumbnail", thumbnail)
                            it.context.startActivity(intent)
                        } else {
                            thumbnail =
                                ((layoutVo as ArrayList<ContentVo>).get(position).item as ImageVo).thumbnail_url
                            val intent = Intent(context, ImageActivity::class.java)
                            intent.putExtra("thumbnail", thumbnail)
                            it.context.startActivity(intent)
                        }
                    }
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
        val context = parent.context
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.contents_recyclerview_item, parent, false)
        return HorizontalViewHolder(view)
    }

    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) {
        var displayMetrics: DisplayMetrics =
            context.applicationContext.resources.displayMetrics;
        var width: Int = displayMetrics.widthPixels;
        var height: Int = displayMetrics.heightPixels;

        if (type == Type.VIDEO) {
            holder.itemView.textViewTitle.text =
                (layoutVo as ArrayList<VideoVo>).get(position).title
            Glide.with(context).load((layoutVo as ArrayList<VideoVo>).get(position).thumbnail)
                .apply(RequestOptions().override(width / 3, height / 8))
                .into(holder.itemView.imageViewContents)

        } else if (type == Type.IMAGE) {
            holder.itemView.textViewTitle.text = ""
            Glide.with(context)
                .load((layoutVo as ArrayList<ImageVo>).get(position).thumbnail_url)
                .apply(RequestOptions().override((width / 2.8).toInt(), height / 8))
                .into(holder.itemView.imageViewContents)
        } else {
            if ((layoutVo as ArrayList<ContentVo>).get(position).type == Type.VIDEO) {
                holder.itemView.textViewTitle.text =
                    ((layoutVo as ArrayList<ContentVo>).get(position).item as VideoVo).title
                Glide.with(context)
                    .load(((layoutVo as ArrayList<ContentVo>).get(position).item as VideoVo).thumbnail)
                    .apply(RequestOptions().override(width / 3, height / 8))
                    .into(holder.itemView.imageViewContents)
            } else {
                holder.itemView.textViewTitle.text = ""
                Glide.with(context)
                    .load(((layoutVo as ArrayList<ContentVo>).get(position).item as ImageVo).thumbnail_url)
                    .apply(RequestOptions().override((width / 2.8).toInt(), height / 8))
                    .into(holder.itemView.imageViewContents)
            }
        }
    }

    override fun getItemCount(): Int {
        layoutVo ?: run {
            return 0
        }
        when (type) {
            Type.VIDEO -> return (layoutVo as ArrayList<VideoVo>).size
            Type.IMAGE -> return (layoutVo as ArrayList<ImageVo>).size
            else -> return (layoutVo as ArrayList<ContentVo>).size
        }
    }
}