package com.uc.week4retrofit.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uc.week4retrofit.model.*
import com.uc.week4retrofit.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MoviesViewModel @Inject constructor
    (private val repository: MoviesRepository): ViewModel(){

        val _nowPlaying: MutableLiveData<ArrayList<Result>> by lazy{
            MutableLiveData<ArrayList<Result>>()
        }

    val nowPlaying: LiveData<ArrayList<Result>>
    get() = _nowPlaying

    fun getNowPLaying(apiKey: String, language: String, page: Int) = viewModelScope.launch {
        repository.getNowPlayingData(apiKey, language, page).let {
            response ->
            if(response.isSuccessful){
                _nowPlaying.postValue(response.body()?.results as ArrayList<Result>?)
            }else{
                Log.e("Get Data", "Failed!")
            }
        }
    }
    val _movieDetails: MutableLiveData<MovieDetails> by lazy{
        MutableLiveData<MovieDetails>()
    }

    val movieDetails: LiveData<MovieDetails>
        get() = _movieDetails

    fun getMovieDetails(apiKey: String, id: Int) = viewModelScope.launch {
        repository.getMovieDetailsData(apiKey, id).let {
                response ->
            if(response.isSuccessful){
                _movieDetails.postValue(response.body() as MovieDetails)
            }else{
                Log.e("Get Movie Details Data", "Failed!")
            }
        }
    }


    val _movieGenre: MutableLiveData<List<Genre>> by lazy{
        MutableLiveData<List<Genre>>()
    }

    val movieGenre: LiveData<List<Genre>>
        get() = _movieGenre

    fun getGenre(apiKey: String, id: Int) = viewModelScope.launch {
        repository.getMovieDetailsData(apiKey, id).let {
                response ->
            if(response.isSuccessful){
                _movieGenre.postValue(response.body() as List<Genre>)
            }else{
                Log.e("Get Movie Genres", "Failed!")
            }
        }
    }
    val _movieLanguage: MutableLiveData<List<SpokenLanguage>> by lazy{
        MutableLiveData<List<SpokenLanguage>>()
    }

    val movieLanguage: LiveData<List<SpokenLanguage>>
        get() = _movieLanguage

    fun getLanguages(apiKey: String, id: Int) = viewModelScope.launch {
        repository.getMovieDetailsData(apiKey, id).let {
                response ->
            if(response.isSuccessful){
                _movieLanguage.postValue(response.body() as List<SpokenLanguage>)
            }else{
                Log.e("Get Movie Genres", "Failed!")
            }
        }
    }

    val _movieProduction: MutableLiveData<List<ProductionCompany>> by lazy{
        MutableLiveData<List<ProductionCompany>>()
    }

    val movieProductionCompany: LiveData<List<ProductionCompany>>
        get() = _movieProduction

    fun getCompany(apiKey: String, id: Int) = viewModelScope.launch {
        repository.getMovieDetailsData(apiKey, id).let {
                response ->
            if(response.isSuccessful){
                _movieProduction.postValue(response.body() as List<ProductionCompany>)
            }else{
                Log.e("Get Movie ", "Failed!")
            }
        }
    }
}