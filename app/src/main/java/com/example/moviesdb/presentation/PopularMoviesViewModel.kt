package com.example.moviesdb.presentation

import android.util.Log
import androidx.lifecycle.*
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.domain.use_case.GetPopularMovies
import com.example.moviesdb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies
) : ViewModel()
{

    //это не в пределах курса, но так мы можем отправлять данные тоже, подписываясь на них из UI
    //(Dispatchers.Default)  - можно без этого
//  fun forecastPublic(): LiveData<List<ForecastViewState>> = liveData {
//    emitSource(
//      weatherRepository.getForecasts().map {
//        homeViewStateMapper.mapForecastsToViewState(it) }
//        .asLiveData()
//    )
//  }
//    private val _movies = MutableLiveData<Resource<List<Movie>>>()
//    val movies: LiveData<Resource<List<Movie>>> = _movies
//
//    init {
//        viewModelScope.launch {
//            val moviesLD = getPopularMovies.invoke().asLiveData()
//            _movies.value = moviesLD.value
//        }
//    }

    fun getMovies(): LiveData<Resource<List<Movie>>> = liveData {
        Log.d("proverka", "viewModel get info")

        emitSource(
            getPopularMovies.invoke().asLiveData()
        )
    }
}