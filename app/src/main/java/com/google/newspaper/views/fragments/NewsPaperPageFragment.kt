package com.google.newspaper.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.newspaper.R
import com.google.newspaper.databinding.FragmentNewsPaperPageBinding
import com.google.newspaper.model.NewsPaper
import com.google.newspaper.utils.Preference
import com.google.newspaper.views.adapter.FavoritosAdapter
import com.google.newspaper.views.viewModel.PageInfoViewModel
import kotlin.properties.Delegates

class NewsPaperPageFragment : Fragment()/*, ViewPager.OnPageChangeListener, SliderAdapterPage.OnboardAdapterListener*/ {

    private lateinit var newsInfo: NewsPaper
    private lateinit var infoViewModel: PageInfoViewModel
    private lateinit var newsPaper: NewsPaper
    private lateinit var adapter: FavoritosAdapter
    private lateinit var binding: FragmentNewsPaperPageBinding
    private val args: NewsPaperPageFragmentArgs by navArgs()
    private lateinit var newsArrayList: ArrayList<NewsPaper>

    private var idImageView by Delegates.notNull<Int>()
    private lateinit var titlePage: String
    private lateinit var newsPage: String
    private var idPosition by Delegates.notNull<Int>()
    private var idPositionArgs by Delegates.notNull<Int>()

    //private var sliderHundler = Handler()
    //private var runnable: Runnable? = null

    //private var pagina = 0

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("idPosition", idPosition)
        outState.putInt("idImageView", idImageView)
        outState.putString("titlePage", titlePage)
        outState.putString("newsPage", newsPage)
    }

    private fun loadSavedInstance(savedInstanceState: Bundle) {
        idPosition = savedInstanceState.getInt("idPosition")
        idImageView = savedInstanceState.getInt("idImageView")
        titlePage = savedInstanceState.getString("titlePage").toString()
        newsPage = savedInstanceState.getString("newsPage").toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let { loadSavedInstance(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNewsPaperPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mock()
        startAdapterHorizontal()
        setInfoLayout()
        getFav()
        clickFavorito()
    }

    private fun setInfoLayout() {
        idPositionArgs = args.idPosition //iphone = 0, anonimos = 1, gp_tinder = 2, banimento = 3, resident_evil = 4
        newsInfo = NewsPaper(
            idPosition = args.idPosition,
            imageId = args.idImage,
            titleNews = args.titlePage,
            news = args.newsPage,
            newsFav = true
        )
        loadViewModel(newsInfo = newsInfo)
    }

    private fun clickFavorito() {
        binding.constraintLayout.setOnClickListener {
            when(idPositionArgs){
                0 -> Preference(requireActivity()).salveFavoritoIphone(favoritarPage = true)
                1 -> Preference(requireActivity()).salveFavoritoAnonimos(favoritarPage = true)
                2 -> Preference(requireActivity()).salveFavoritoGpTinder(favoritarPage = true)
                3 -> Preference(requireActivity()).salveFavoritoBanimento(favoritarPage = true)
                4 -> Preference(requireActivity()).salveFavoritoResident(favoritarPage = true)
            }
            binding.favorito.setImageResource(R.drawable.ic_star_fav)
            mock()
            adapter.refresh(newsArrayList)
            //binding.txtLike.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_like))
        }
    }

    private fun getFav() {
        val fav = when(idPositionArgs){
            0 -> Preference(requireActivity()).getFavoritoIphone()
            1 -> Preference(requireActivity()).getFavoritoAnonimos()
            2 -> Preference(requireActivity()).getFavoritoGpTinder()
            3 -> Preference(requireActivity()).getFavoritoBanimento()
            4 -> Preference(requireActivity()).getFavoritoResident()
            else -> {false}
        }
        if(fav){
            binding.favorito.setImageResource(R.drawable.ic_star_fav)
        }
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

        /*val onboardAdapter = SliderAdapterPage(lista = newsArrayList, requireActivity())
        onboardAdapter.setAdapterListener(this, pagina)

        binding.listItem.adapter = onboardAdapter
        binding.listItem.setPageTransformer(true, ZoomOutPageTransformer())

        binding.listItem.currentItem = pagina
        binding.listItem.addOnPageChangeListener(this)

        startAutoSlider(onboardAdapter.count)*/
    }

    /*override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        this.pagina = position
    }
    override fun onPageScrollStateChanged(state: Int) {}
    override fun finalizar() {}

    override fun setPagina(pagina: Int) {
        binding.listItem.currentItem = pagina
    }*/

    /*private fun startAutoSlider(count: Int) {
        runnable = Runnable {
            var pos: Int = binding.listItem.currentItem
            pos += 1
            if (pos >= count) pos = 0
            binding.listItem.currentItem = pos
            runnable?.let { sliderHundler.postDelayed(it, 10000) }
        }
        sliderHundler.postDelayed(runnable!!, 10000)
    }*/

    private fun startAdapterHorizontal(){
        binding.listItem.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.listItem.setHasFixedSize(true)

        adapter =  FavoritosAdapter(newsPaperArrays = newsArrayList)
        binding.listItem.adapter = adapter
        adapter.setOnItemClickListener(object : FavoritosAdapter.OnItemClickLIstener{
            override fun onItemClick(position: Int) {

                idPosition = newsArrayList[position].idPosition
                idImageView = newsArrayList[position].imageId
                titlePage = newsArrayList[position].titleNews
                newsPage = newsArrayList[position].news
                val favPaper = newsArrayList[position].newsFav

                newsInfo = NewsPaper(
                    idPosition = idPosition,
                    imageId = idImageView,
                    titleNews = titlePage,
                    news = newsPage,
                    newsFav = favPaper
                )
                loadViewModel(newsInfo = newsInfo)
            }
        })
    }

    private fun loadViewModel(newsInfo: NewsPaper?){
        infoViewModel = ViewModelProvider(requireActivity())[PageInfoViewModel::class.java]
        if (newsInfo != null) {
            infoViewModel.setInfo(newsInfo).observe(viewLifecycleOwner, {
                it.let {
                    binding.imgNews.setImageResource(it.imageId)
                    binding.txtTitleNews.text = it.titleNews
                    binding.txtNewsPage.text = it.news
                }
            })
        }
    }
}