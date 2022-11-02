package com.uc.week4retrofit.view

import CompanyAdapter
import GenreAdapter
import LanguageAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.uc.week4retrofit.databinding.MovieDetailBinding
import com.uc.week4retrofit.helper.Const
import com.uc.week4retrofit.model.Genre
import com.uc.week4retrofit.model.ProductionCompany
import com.uc.week4retrofit.model.SpokenLanguage
import com.uc.week4retrofit.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetail : AppCompatActivity() {
    private  lateinit var binding: MovieDetailBinding
    private lateinit var nowPlayingViewModel: MoviesViewModel
    private lateinit var genre: GenreAdapter
    private lateinit var company: CompanyAdapter
    private lateinit var bahasa: LanguageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvGenre.visibility = View.INVISIBLE
        binding.rvLanguage.visibility = View.INVISIBLE
        binding.rvCompany.visibility = View.INVISIBLE
        binding.rvOverview.visibility = View.INVISIBLE
        binding.rvJudul.visibility = View.INVISIBLE
        binding.genre.visibility = View.INVISIBLE
        binding.company.visibility = View.INVISIBLE
        binding.bahasa.visibility = View.INVISIBLE
        binding.overview.visibility = View.INVISIBLE
        binding.poster.visibility = View.INVISIBLE

        val movieId = intent.getIntExtra("movie_id", 0)
        Toast.makeText(applicationContext, "Movie ID: $movieId", Toast.LENGTH_SHORT).show()

        nowPlayingViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        nowPlayingViewModel.getMovieDetails(Const.API_KEY, movieId)
        nowPlayingViewModel.movieDetails.observe(this, Observer { response ->


            if (response != null) {
                binding.progressBar2.visibility = View.INVISIBLE
                binding.rvGenre.visibility = View.VISIBLE
                binding.rvLanguage.visibility = View.VISIBLE
                binding.rvCompany.visibility = View.VISIBLE
                binding.rvOverview.visibility = View.VISIBLE
                binding.rvJudul.visibility = View.VISIBLE
                binding.genre.visibility = View.VISIBLE
                binding.company.visibility = View.VISIBLE
                binding.bahasa.visibility = View.VISIBLE
                binding.overview.visibility = View.VISIBLE
                binding.poster.visibility = View.VISIBLE
            }


            binding.rvJudul.apply {
                text = response.title
            }

            binding.rvOverview.apply {
                text = response.overview
            }

            binding.rvGenre.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            genre = GenreAdapter(response.genres)
            binding.rvGenre.adapter = genre

            binding.rvLanguage.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            bahasa = LanguageAdapter(response.spoken_languages)
            binding.rvLanguage.adapter = bahasa

            binding.rvCompany.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            company = CompanyAdapter(response.production_companies)
            binding.rvCompany.adapter = company

            Glide.with(applicationContext).load(Const.IMG_URL + response.backdrop_path)
                .into(binding.poster)
        })


    }
}