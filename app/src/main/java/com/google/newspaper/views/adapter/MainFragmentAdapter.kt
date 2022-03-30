package com.google.newspaper.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.newspaper.R
import com.google.newspaper.model.NewsPaper

class MainFragmentAdapter(private val newsPaperArrays: ArrayList<NewsPaper>): RecyclerView.Adapter<MainFragmentAdapter.ViewHolder>() {

    private lateinit var mListener: OnItemClickLIstener

    interface OnItemClickLIstener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickLIstener){
        mListener = listener
    }

    class ViewHolder(view: View, listener: OnItemClickLIstener): RecyclerView.ViewHolder(view){
        var imgNews: ImageView = view.findViewById(R.id.imgNews)
        var titleNews: TextView = view.findViewById(R.id.titleNews)
        var news: TextView = view.findViewById(R.id.txtNews)
        var newsFav: ImageView = view.findViewById(R.id.newsFav)

        init {
            imgNews = view.findViewById(R.id.imgNews)
            titleNews = view.findViewById(R.id.titleNews)
            news = view.findViewById(R.id.txtNews)
            newsFav = view.findViewById(R.id.newsFav)

            view.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_item_home, parent, false)

        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imgNews.setImageResource(newsPaperArrays[position].imageId)
        holder.titleNews.text = newsPaperArrays[position].titleNews
        holder.news.text = newsPaperArrays[position].news

        if(newsPaperArrays[position].newsFav){
            holder.newsFav.setImageResource(R.drawable.ic_star_fav)
        }
    }

    override fun getItemCount(): Int {
        return newsPaperArrays.size
    }
}