package com.example.moviesdb.presentation.detail_movie

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.moviesdb.R
import com.example.moviesdb.databinding.FragmentDetailMovieBinding
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieFragment : Fragment(R.layout.fragment_detail_movie) {

    lateinit var viewModel: DetailMovieViewModel

    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val EXTRA_MOVIE = "EXTRA MOVIE"
        const val POSTER_IMAGE_PATH_PREFIX = "https://image.tmdb.org/t/p/w300"

        fun newInstance(movie: Movie) =
            DetailMovieFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_MOVIE, movie)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(DetailMovieViewModel::class.java)

        _binding = FragmentDetailMovieBinding.bind(view)

        val movie: Movie?
        arguments.let {
            movie = arguments?.getParcelable<Movie>(EXTRA_MOVIE)
        }

        initUi(movie)
        if (movie != null) {
            initObservers(movie.id)
        }
    }

    private fun initObservers(movieId: Int) {
        viewModel.getRecommendation(movieId).observe(viewLifecycleOwner, Observer {

            when(it) {
                is Resource.Success -> {
                    Log.d("proverka", "Success")
                    it.data.map { movie ->
                        Log.d("proverka", movie.toString())
                    }
                }

                is Resource.Loading -> {
                    Log.d("proverka", "Loading")
                }

                is Resource.Error -> {
                    Log.d("proverka", "Error")
                }

                else -> Unit
            }
        })

        viewModel.getImagesByMovie(movieId).observe(viewLifecycleOwner, Observer {

            when(it) {
                is Resource.Success -> {
                    Log.d("proverka", "Success")
                    it.data.map { image ->
                        Log.d("proverka", image.toString())
                    }
                }

                is Resource.Loading -> {
                    Log.d("proverka", "Loading")
                }

                is Resource.Error -> {
                    Log.d("proverka", "Error")
                }

                else -> Unit
            }

        })
    }

    private fun initUi(movie: Movie?) {
        movie?.let {
            binding.apply {
                title.text = movie.original_title

                view?.let {
                    Glide.with(it.context)
                        .load("${POSTER_IMAGE_PATH_PREFIX}${movie.poster_path}")
                        .centerCrop()
                        .placeholder(R.drawable.ic_baseline_image_24)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(R.drawable.ic_baseline_error_24)
                        .into(poster)
                }
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}