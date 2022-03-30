package com.google.newspaper.views.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.newspaper.model.NewsPaper
import kotlinx.coroutines.launch

class PageInfoViewModel: ViewModel() {
    private val info: MutableLiveData<NewsPaper> by lazy {
        MutableLiveData<NewsPaper>()
    }

    fun setInfo(newsInfo: NewsPaper): LiveData<NewsPaper> {
        return getNewsInfo(newsInfo)
    }

    private fun getNewsInfo(newsInfo: NewsPaper): MutableLiveData<NewsPaper> {
        viewModelScope.launch {
            info.postValue(newsInfo)
        }
        return info
    }
}