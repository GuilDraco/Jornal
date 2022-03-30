package com.google.newspaper.views.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.newspaper.R
import com.google.newspaper.databinding.FragmentMainBinding
import com.google.newspaper.model.NewsPaper
import com.google.newspaper.model.OnboardSlide
import com.google.newspaper.utils.Preference
import com.google.newspaper.utils.ZoomOutPageTransformer
import com.google.newspaper.views.adapter.SliderAdapterHome
import com.google.newspaper.views.adapter.MainFragmentAdapter

class FragmentMain : Fragment(), ViewPager.OnPageChangeListener, SliderAdapterHome.OnboardAdapterListener {

    private lateinit var binding: FragmentMainBinding
    private var sliderHundler = Handler()
    private var runnable: Runnable? = null

    private lateinit var newsArrayList: ArrayList<NewsPaper>
    private lateinit var slideList: ArrayList<OnboardSlide>
    private val navController by lazy { findNavController() }

    private var pagina = 0

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("pagina", pagina)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mock()

        startAdapterVertical()
        startAdapterHor()
    }

    private fun startAdapterHor() {
        //mockAdapterHorizontal
        slideList = ArrayList()
        slideList.add(
            OnboardSlide(
                R.drawable.resident_evil,
                getString(R.string.title_resident_evil),
                "",
                true
            )
        )
        slideList.add(
            OnboardSlide(
                R.drawable.iphone,
                getString(R.string.title_iphone_se3),
                "",
                true
            )
        )
        slideList.add(
            OnboardSlide(
                R.drawable.anonimos,
                getString(R.string.title_anonimos),
                "",
                true
            )
        )
        slideList.add(
            OnboardSlide(
                R.drawable.gp_tinder,
                getString(R.string.title_gp_tinder),
                "",
                true
            )
        )
        slideList.add(
            OnboardSlide(
                R.drawable.banimento,
                getString(R.string.title_banimento),
                "",
                true
            )
        )

        val onboardAdapter = SliderAdapterHome(lista = slideList, requireActivity())
        onboardAdapter.setAdapterListener(this, pagina)

        binding.slide.adapter = onboardAdapter
        binding.slide.setPageTransformer(true, ZoomOutPageTransformer())

        binding.slide.currentItem = pagina
        binding.slide.addOnPageChangeListener(this)

        startAutoSlider(onboardAdapter.count)
    }

    private fun startAutoSlider(count: Int) {
        runnable = Runnable {
            var pos: Int = binding.slide.currentItem
            pos += 1
            if (pos >= count) pos = 0
            binding.slide.currentItem = pos
            runnable?.let { sliderHundler.postDelayed(it, 10000) }
        }
        sliderHundler.postDelayed(runnable!!, 10000)
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
        for (i in news.indices){
            val newsPaper = NewsPaper(i, imageId[i], getString(newsTitle[i]), getString(news[i]), newsFav[i])
            newsArrayList.add(newsPaper)
        }
    }

    private fun startAdapterVertical(){
        binding.listItem.layoutManager = LinearLayoutManager(requireContext())
        binding.listItem.setHasFixedSize(true)

        val adapter =  MainFragmentAdapter(newsPaperArrays = newsArrayList)
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
            FragmentMainDirections.actionFragmentMainToFragmentNewsPaperPage(
                idPosition,
                imgNewsId,
                newsPaperTitle,
                newsPaper
            )

        navController.navigate(page)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        this.pagina = position
    }
    override fun onPageScrollStateChanged(state: Int) {}
    override fun finalizar() {}

    override fun setPagina(pagina: Int) {
        binding.slide.currentItem = pagina
    }
}