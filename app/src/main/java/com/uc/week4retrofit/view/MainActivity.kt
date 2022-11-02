package com.uc.week4retrofit.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.uc.week4retrofit.R
import com.uc.week4retrofit.adapter.NowPlayingAdapter
import com.uc.week4retrofit.databinding.ActivityMainBinding
import com.uc.week4retrofit.helper.Const
import com.uc.week4retrofit.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NowPlayingAdapter
    private lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        viewModel.getNowPLaying(Const.API_KEY, "en-US", 1)
        viewModel.nowPlaying.observe(this, Observer { response->

            if (response != null){
                binding.progressBar.visibility = View.INVISIBLE
                binding.rvMain.visibility = View.VISIBLE
            }
            binding.rvMain.layoutManager = LinearLayoutManager(this)
            adapter = NowPlayingAdapter(response)
            binding.rvMain.adapter = adapter
        })
    }
}