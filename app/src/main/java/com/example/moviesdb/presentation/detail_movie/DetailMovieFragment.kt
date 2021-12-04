package com.example.moviesdb.presentation.detail_movie

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.moviesdb.R
import com.example.moviesdb.databinding.FragmentDetailMovieBinding
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.presentation.GenreAdapter
import com.example.moviesdb.presentation.PopularMoviesAdapter
import com.example.moviesdb.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieFragment : Fragment(R.layout.fragment_detail_movie) {

    lateinit var viewModel: DetailMovieViewModel

    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!
    private val adapter = GenreAdapter()


    companion object {
        const val EXTRA_MOVIE = "EXTRA MOVIE"
        const val POSTER_IMAGE_PATH_PREFIX = "https://image.tmdb.org/t/p/w500"

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
        binding.apply {
            recyclerviewGenre.adapter = adapter
        }

        initUi(movie)
        if (movie != null) {
            initObservers(movie.id)
        }
    }

    private fun initObservers(movieId: Int) {
        viewModel.getMovieDetails(movieId).observe(viewLifecycleOwner, Observer {

            when(it) {
                is Resource.Success -> {
                    val detailMovie = it.data
                    view?.let { view ->
                        Glide.with(view.context)
                            .load("${POSTER_IMAGE_PATH_PREFIX}${detailMovie.backdrop_path}")
                            .placeholder(R.drawable.ic_baseline_image_24)
                            .error(R.drawable.ic_baseline_error_24)
                            .into(binding.imageToolbar)
                    }

                    view?.let { view ->
                        Glide.with(view.context)
                            .load("${POSTER_IMAGE_PATH_PREFIX}${detailMovie.poster_path}")
                            .placeholder(R.drawable.ic_baseline_image_24)
                            .error(R.drawable.ic_baseline_error_24)
                            .into(binding.poster)
                    }


                    detailMovie.genres.let { genre->
                        adapter.submitList(genre)
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
//                    Log.d("proverkad", (it.data[0] == null).toString())
                    if (it.data.isNotEmpty()){
                        val image = it.data[0].file_path
//                        view?.let { view ->
//                            Glide.with(view.context)
//                                .load("${POSTER_IMAGE_PATH_PREFIX}${image}")
//                                .placeholder(R.drawable.ic_baseline_image_24)
//                                .error(R.drawable.ic_baseline_error_24)
//                                .into(binding.imageToolbar)
//                        }
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

        viewModel.getCastByMovie(movieId).observe(viewLifecycleOwner, Observer {

            when(it) {
                is Resource.Success -> {
                    Log.d("proverkacast", "Success")
                    it.data.cast.map { cast ->
                        Log.d("proverkacast", cast.toString())
                    }
                    it.data.crew.map { crew ->
                        Log.d("proverkacast", crew.toString())
                    }
                }

                is Resource.Loading -> {
                    Log.d("proverkacast", "Loading")
                }

                is Resource.Error -> {
                    Log.d("proverkacast", "Error")
                }

                else -> Unit
            }

        })
    }

    private fun initUi(movie: Movie?) {
        movie?.let {
            binding.apply {
                collapsingToolbar.title = movie.original_title

//                view?.let {
//                    Glide.with(it.context)
//                        .load("${POSTER_IMAGE_PATH_PREFIX}${movie.poster_path}")
//                        .placeholder(R.drawable.ic_baseline_image_24)
//                        .error(R.drawable.ic_baseline_error_24)
//                        .into(imageToolbar)
//                }
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}