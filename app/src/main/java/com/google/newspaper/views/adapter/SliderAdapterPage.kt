package com.google.newspaper.views.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import com.google.newspaper.R
import com.google.newspaper.databinding.LayoutSliderPageBinding
import com.google.newspaper.model.NewsPaper
import com.google.newspaper.utils.DpToPx

class SliderAdapterPage (private var lista: ArrayList<NewsPaper>, private var activity: Activity) : PagerAdapter() {

    private var listener: OnboardAdapterListener? = null
    private var pagina = 0

    interface OnboardAdapterListener {
        fun finalizar()
        fun setPagina(pagina: Int)
    }

    fun setAdapterListener(listener: OnboardAdapterListener?, pagina: Int) {
        this.listener = listener
        this.pagina = pagina
    }

    override fun getCount(): Int {
        return lista.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val item: NewsPaper = lista[position]
        val binding: LayoutSliderPageBinding = LayoutSliderPageBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            true
        )
        binding.imagem.setImageResource(item.imageId)
        binding.titulo.text = item.titleNews
        //binding.descricao.text = item.news

        setDotClick(binding)
        setDotIndicator(binding, position)
        return binding.root
    }

    private fun setDotClick(binding: LayoutSliderPageBinding) {
        var ponto: View
        for (i in 0 until binding.pontos.childCount) {
            ponto = binding.pontos.getChildAt(i)
            ponto.setOnClickListener {
                listener!!.setPagina(i)
            }
        }
    }
    private fun setDotIndicator(binding: LayoutSliderPageBinding, position: Int) {
        val ponto: View = binding.pontos.getChildAt(position)
        ponto.isActivated = true
        val params = ponto.layoutParams as LinearLayout.LayoutParams
        params.height = DpToPx().dpToPx(8, activity = activity)
        params.width = DpToPx().dpToPx(8, activity = activity)

        ponto.background = ContextCompat.getDrawable(activity, R.drawable.template_circle_dot_dark)
        ponto.layoutParams = params
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }
}