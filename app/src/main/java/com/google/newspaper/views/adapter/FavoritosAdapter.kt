package com.google.newspaper.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.newspaper.R
import com.google.newspaper.model.NewsPaper

class FavoritosAdapter (private val newsPaperArrays: ArrayList<NewsPaper>): RecyclerView.Adapter<FavoritosAdapter.ViewHolder>() {
    private lateinit var mListener: OnItemClickLIstener

    interface OnItemClickLIstener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickLIstener){
        mListener = listener
    }

    fun refresh(refreshFav: ArrayList<NewsPaper>) {
        notifyItemRangeRemoved(0, newsPaperArrays.size)
        newsPaperArrays.clear()
        newsPaperArrays.addAll(refreshFav)
        notifyItemRangeInserted(0, newsPaperArrays.size)
    }

    class ViewHolder(view: View, listener: OnItemClickLIstener): RecyclerView.ViewHolder(view){
        var imgNews: ImageView = view.findViewById(R.id.imgNews)
        var titleNews: TextView = view.findViewById(R.id.titleNews)
        //var news: TextView = view.findViewById(R.id.txtNews)

        init {
            imgNews = view.findViewById(R.id.imgNews)
            titleNews = view.findViewById(R.id.titleNews)
            //news = view.findViewById(R.id.txtNews)

            view.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_item_page, parent, false)

        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imgNews.setImageResource(newsPaperArrays[position].imageId)
        holder.titleNews.text = newsPaperArrays[position].titleNews
        //holder.news.text = newsPaperArrays[position].news
    }

    override fun getItemCount(): Int {
        return newsPaperArrays.size
    }
}