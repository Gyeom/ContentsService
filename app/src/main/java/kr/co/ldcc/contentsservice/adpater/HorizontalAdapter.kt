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
import kotlinx.android.synthetic.main.container_recyclerview_item.view.*
import kotlinx.android.synthetic.main.contents_recyclerview_item.view.*
import kotlinx.android.synthetic.main.contents_recyclerview_item.view.textViewTitle
import kr.co.ldcc.contentsservice.R
import kr.co.ldcc.contentsservice.activity.ContentsActivity
import kr.co.ldcc.contentsservice.etc.DisplayMetric
import kr.co.ldcc.contentsservice.model.vo.ContentVo
import kr.co.ldcc.contentsservice.model.Type
import kr.co.ldcc.contentsservice.model.vo.BookmarkVo
import kr.co.ldcc.contentsservice.model.vo.VideoVo
import kr.co.ldcc.contentsservice.model.vo.ImageVo

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
                var title: String
                var datetime: String
                val intent = Intent(context, ContentsActivity::class.java)

                if (position != RecyclerView.NO_POSITION) {
                    if (type.equals(Type.IMAGE)) {
                        thumbnail = (layoutVo as ArrayList<ImageVo>).get(position).thumbnail_url
                        datetime = (layoutVo as ArrayList<ImageVo>).get(position).datetime
                        title = ""
                        intent.putExtra("contentType", "Image")
                    } else if (type.equals(Type.VIDEO)) {
                        thumbnail = (layoutVo as ArrayList<VideoVo>).get(position).thumbnail
                        title = (layoutVo as ArrayList<VideoVo>).get(position).title
                        datetime = (layoutVo as ArrayList<VideoVo>).get(position).datetime
                        intent.putExtra("contentType", "Video")
                        intent.putExtra("url", (layoutVo as ArrayList<VideoVo>).get(position).url)
                    } else if (type.equals(Type.CONTENT)) {
                        if ((layoutVo as ArrayList<ContentVo>).get(position).type.equals(Type.VIDEO)) {
                            thumbnail =
                                ((layoutVo as ArrayList<ContentVo>).get(position).item as VideoVo).thumbnail
                            title =
                                ((layoutVo as ArrayList<ContentVo>).get(position).item as VideoVo).title
                            datetime =
                                ((layoutVo as ArrayList<ContentVo>).get(position).item as VideoVo).datetime
                            intent.putExtra("contentType", "Video")
                            intent.putExtra(
                                "url",
                                (layoutVo as ArrayList<VideoVo>).get(position).url
                            )

                        } else {
                            title = ""
                            thumbnail =
                                ((layoutVo as ArrayList<ContentVo>).get(position).item as ImageVo).thumbnail_url
                            datetime =
                                ((layoutVo as ArrayList<ContentVo>).get(position).item as ImageVo).datetime
                            intent.putExtra("contentType", "Image")
                        }
                    } else {
                        datetime = (layoutVo as ArrayList<BookmarkVo>).get(position).datetime
                        thumbnail = (layoutVo as ArrayList<BookmarkVo>).get(position).thumbnail
                        title = (layoutVo as ArrayList<BookmarkVo>).get(position).title
                        intent.putExtra("contentType", "Bookmark")
                        intent.putExtra(
                            "contentId",
                            (layoutVo as ArrayList<BookmarkVo>).get(position).contentId
                        )
                    }

                    intent.putExtra("datetime", datetime)
                    intent.putExtra("thumbnail", thumbnail)
                    intent.putExtra("title", title)
                    context.startActivity(intent)
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
        // init displayMetric
        val displayMetric = DisplayMetric(context)
        val height: Int = displayMetric.height
        val width: Int = displayMetric.width

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

        } else if (type == Type.CONTENT) {
            Log.d("Test", position.toString())
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

        } else if (type == Type.BOOKMARK) {
            holder.itemView.textViewTitle.text =
                (layoutVo as ArrayList<BookmarkVo>).get(position).title
            Glide.with(context).load((layoutVo as ArrayList<BookmarkVo>).get(position).thumbnail)
                .apply(RequestOptions().override(width / 3, height / 8))
                .into(holder.itemView.imageViewContents)
        }
    }


    override fun getItemCount(): Int {
        layoutVo ?: run {
            return 0
        }
        when (type) {
            Type.VIDEO -> return (layoutVo as ArrayList<VideoVo>).size
            Type.IMAGE -> return (layoutVo as ArrayList<ImageVo>).size
            Type.CONTENT -> return (layoutVo as ArrayList<ContentVo>).size
            else -> return (layoutVo as ArrayList<BookmarkVo>).size
        }
    }
}