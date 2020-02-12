package kr.co.ldcc.contentsservice.adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.contents_recyclerview_item.view.*
import kr.co.ldcc.contentsservice.R

class HorizontalAdapter : RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder>() {

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
        holder.itemView.textViewTitle.text = "dsds"
    }

    override fun getItemCount(): Int {
        return 4
    }
}