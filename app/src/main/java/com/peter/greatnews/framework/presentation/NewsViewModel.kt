package com.peter.greatnews.framework.presentation

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.peter.greatnews.business.model.Article
import com.peter.greatnews.business.usecases.abstraction.NewsUseCases
import com.peter.greatnews.framework.datasource.Constants.PAGE_SIZE
import com.peter.greatnews.framework.datasource.NewsPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class NewsViewModel @Inject constructor(private val useCases: NewsUseCases) : ViewModel() {

    private val _selectedArticle = MutableLiveData<Article?>(null)
    val selectedArticle: LiveData<Article?>
        get() = _selectedArticle

    private val _favorites = MutableLiveData<List<Article>>(null)
    val favorites: LiveData<List<Article>>
        get() = _favorites

    private val _navigateToDetails = MutableLiveData<Boolean>(false)
    val navigateToDetails: LiveData<Boolean>
        get() = _navigateToDetails

    val msg = MutableLiveData<String>("")

    val queryValue = MutableLiveData<String>()

    var listData = Pager(PagingConfig(PAGE_SIZE)) {
        NewsPagingSource(useCases, queryValue.value!!)
    }.flow


    fun setQuery(query: String) {
        this.queryValue.value = query
        search()
    }

    init {
        search()
    }

    private fun search() {
        viewModelScope.launch {
            viewModelScope.launch {
                listData = queryValue.asFlow()
                    .debounce(200) // wait  before  search
                    .filter {
                        return@filter it.isNotEmpty() && it.length > 2
                    }
                    .distinctUntilChanged()
                    .flatMapLatest {
                        Pager(
                            config = PagingConfig(
                                pageSize = PAGE_SIZE,
                            ),
                            pagingSourceFactory = { NewsPagingSource(useCases, it) }
                        ).flow
                    }
            }
        }
    }

    fun selectArticle(article: Article) {
        _selectedArticle.value = article
    }

    fun navigateToDetails() {
        _navigateToDetails.value = true
    }

    fun onDoneNavigateToDetails() {
        _navigateToDetails.value = false
    }

    fun saveToFavorites() {
        viewModelScope.launch {
            useCases.saveToFavorite(_selectedArticle.value!!)
                .collect {
                    Log.i("Result", it.toString())
                    msg.value = if (it) "Saved" else "Something happened"
                }
        }
    }

    fun loadFavorites() {
        viewModelScope.launch { useCases.getFavoriteNews().collect { _favorites.value = it } }
    }

}
