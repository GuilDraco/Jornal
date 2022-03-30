package com.google.newspaper.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.newspaper.R
import com.google.newspaper.databinding.FragmentFavoritosBinding
import com.google.newspaper.model.NewsPaper
import com.google.newspaper.utils.Preference
import com.google.newspaper.views.adapter.MainFragmentAdapter

class FavoritosFragment : Fragment() {

    private lateinit var binding: FragmentFavoritosBinding
    private lateinit var newsArrayList: ArrayList<NewsPaper>
    private lateinit var newsPaper: NewsPaper
    private lateinit var adapter: MainFragmentAdapter
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoritosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mock()
        startAdapterHorizontal()
    }

    private fun mock(){
        //mockAdapterVertical
        val imageId = intArrayOf(
            R.drawable.iphone, R.drawable.anonimos, R.drawable.gp_tinder, R.drawable.banimento, R.drawable.resident_evil
        )
        val newsTitle = arrayOf(
            R.string.title_iphone_se3, R.string.title_anonimos, R.string.title_gp_tinder, R.string.title_banimento, R.string.title_resident_evil
        )
        val news = arrayOf(
            R.string.info_news_apple, R.string.info_news_anonimos, R.string.info_news_gp_tinder, R.string.info_news_banimento, R.string.info_news_resident_evil
        )
        val newsFav = arrayOf(
            Preference(requireActivity()).getFavoritoIphone(), Preference(requireActivity()).getFavoritoAnonimos(),
            Preference(requireActivity()).getFavoritoGpTinder(), Preference(requireActivity()).getFavoritoBanimento(),
            Preference(requireActivity()).getFavoritoResident()
        )
        newsArrayList = ArrayList()
        newsArrayList.clear()
        for (i in news.indices){
            newsPaper = NewsPaper(i, imageId[i], getString(newsTitle[i]), getString(news[i]), newsFav[i])
            if(newsPaper.newsFav){
                newsArrayList.add(newsPaper)
            }
        }
        if(newsArrayList.isEmpty()){
            binding.infoListEmpty.visibility = View.VISIBLE
        }else{
            binding.infoListEmpty.visibility = View.GONE
        }
    }

    private fun startAdapterHorizontal(){
        binding.listItem.layoutManager = LinearLayoutManager(requireContext())
        binding.listItem.setHasFixedSize(true)

        adapter =  MainFragmentAdapter(newsPaperArrays = newsArrayList)
        binding.listItem.adapter = adapter
        adapter.setOnItemClickListener(object : MainFragmentAdapter.OnItemClickLIstener{
            override fun onItemClick(position: Int) {
                val idPosition = newsArrayList[position].idPosition
                val imgNewsId = newsArrayList[position].imageId
                val newsPaperTitle = newsArrayList[position].titleNews
                val newsPaper = newsArrayList[position].news

                gotoNewsPage(idPosition, imgNewsId, newsPaperTitle, newsPaper)
            }
        })
    }

    private fun gotoNewsPage(idPosition: Int, imgNewsId: Int, newsPaperTitle: String, newsPaper: String) {
        val page =
            FavoritosFragmentDirections.actionFavoritosFragmentToFragmentNewsPaperPage(
                idPosition,
                imgNewsId,
                newsPaperTitle,
                newsPaper
            )

        navController.navigate(page)
    }
}