package com.example.moviesdb.presentation.detail_movie

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviesdb.R
import com.example.moviesdb.databinding.FragmentDetailMovieBinding
import com.example.moviesdb.presentation.adapters.*
import com.example.moviesdb.presentation.main.MainViewModel
import com.example.moviesdb.util.Resource
import com.example.moviesdb.util.useGlide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieFragment : Fragment(R.layout.fragment_detail_movie) {

    lateinit var viewModel: DetailMovieViewModel

    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!
    private val adapter = GenreAdapter()
    private val castAdapter = CastAdapter()
    private val recommendationsAdapter = RecommendationsAdapter()
    private val imagesAdapter = ImagesAdapter()
    private var movieExistInWatchList = false

    companion object {
        const val EXTRA_MOVIE_ID = "EXTRA MOVIE ID"
        const val POSTER_IMAGE_PATH_PREFIX = "https://image.tmdb.org/t/p/w500"

        fun newInstance(id: Int) =
            DetailMovieFragment().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_MOVIE_ID, id)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(DetailMovieViewModel::class.java)

        _binding = FragmentDetailMovieBinding.bind(view)

        val movieId: Int?
        arguments.let {
            movieId = arguments?.getInt(EXTRA_MOVIE_ID)
        }

        initUi(movieId)
        if (movieId != null) {
            initObservers(movieId)
        }
    }

    private fun initObservers(movieId: Int) {
        viewModel.getMovieDetails(movieId).observe(viewLifecycleOwner, Observer {

            when(it) {
                is Resource.Success -> {
                    val detailMovie = it.data
                    view?.let { view ->
                        binding.apply {
                            collapsingToolbar.title = detailMovie.original_title
                            if (detailMovie.tagline.isNullOrEmpty()) {
                                tagline.isVisible = false
                            }
                            tagline.text = detailMovie.tagline
                            overview.text = detailMovie.overview


                            val voteAverage = detailMovie.vote_average
                            if (voteAverage == 0.0 || voteAverage == null) {
                                cardViewVote.isVisible = false
                            } else {
                                when {
                                    voteAverage < 5.0 -> cardViewVote.strokeColor = resources.getColor(R.color.red)
                                    voteAverage > 7.0 -> cardViewVote.strokeColor = resources.getColor(R.color.green)
                                    else -> cardViewVote.strokeColor = resources.getColor(R.color.gray)
                                }
                                val voteText = "${detailMovie.vote_average}/10"
                                vote.text = voteText
                                voteCount.text = detailMovie.vote_count.toString()
                            }




                            if (detailMovie.budget == 0 || detailMovie.budget == null) {
                                cardViewBudget.isVisible = false
                            } else {
                                val number = detailMovie.budget/1000000
                                if (number < 1) {
                                    val budgetText = "<1kk$"
                                    budget.text = budgetText
                                } else {
                                    val budgetText = "${number}kk$"
                                    budget.text = budgetText
                                }
                            }


                            if (detailMovie.revenue == 0 || detailMovie.revenue == null) {
                                cardViewRevenue.isVisible = false
                            } else {
                                val number = detailMovie.revenue/1000000
                                if (number < 1) {
                                    val revenueText = "<1kk$"
                                    revenue.text = revenueText
                                } else {
                                    val revenueText = "${number}kk$"
                                    revenue.text = revenueText
                                }
                            }

                            if (detailMovie.release_date == null) {
                                cardViewRevenue.isVisible = false
                            } else {
                                val date = detailMovie.release_date
                                releaseDate.text = date
                            }



                            useGlide(
                                view.context,
                                "${POSTER_IMAGE_PATH_PREFIX}${detailMovie.backdrop_path}",
                                imageToolbar
                            )
                        }

                    }

                    view?.let { view ->
                        useGlide(
                            view.context,
                            "${POSTER_IMAGE_PATH_PREFIX}${detailMovie.poster_path}",
                            binding.poster
                        )
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
            binding.apply {
                when (it) {
                    is Resource.Success -> {
                        val list = it.data
                        if (list.isNullOrEmpty()) {
                            recommendationsGroup.isVisible = false
                        } else {
                            recommendationsAdapter.submitList(list)
                        }
                    }
                    is Resource.Loading -> {
                    }

                    is Resource.Error -> {
                    }
                    else -> Unit
                }
            }
        })

        viewModel.getImagesByMovie(movieId).observe(viewLifecycleOwner, Observer {
            binding.apply {
                when (it) {
                    is Resource.Success -> {
                        val list = it.data.backdrops
                        if (list.isNullOrEmpty()) {
                            imagesGroup.isVisible = false
                        } else {
                            imagesAdapter.submitList(list)
                        }
                    }
                    is Resource.Loading -> {
                    }

                    is Resource.Error -> {
                    }
                    else -> Unit
                }
            }
        })

        viewModel.getCastByMovie(movieId).observe(viewLifecycleOwner, Observer {

            binding.apply {
                when (it) {
                    is Resource.Success -> {
                        val cast = it.data.cast
                        val crew = it.data.crew
                        if (cast.isNullOrEmpty()) {
                            castGroup.isVisible = false
                        } else {
                            castAdapter.submitList(cast)

                            crew.map { crewDirector->
                                val text = "Director: ${crewDirector.name}"
                                director.text = text
                            }
                        }
                    }
                    is Resource.Loading -> {
                    }

                    is Resource.Error -> {
                    }
                    else -> Unit
                }
            }
        })

        viewModel.checkMovie(movieId).observe(viewLifecycleOwner, {
            binding.apply {
                when (it) {
                    is Resource.Success -> {
                        if (it.data) {
                            movieExistInWatchList = true
                            addToWatchList.text = resources.getString(R.string.remove_from_list)
                            addToWatchList.setBackgroundColor(resources.getColor(R.color.green))
                        } else {
                            movieExistInWatchList = false
                            addToWatchList.text = resources.getString(R.string.add_to_watch_list)
                            addToWatchList.setBackgroundColor(resources.getColor(R.color.black))
                        }
                    }
                    is Resource.Error -> {
                        Log.d("problem", "problem")
                    }
                    else -> Unit
                }
            }
        })
    }

    private fun initUi(movieId: Int?) {
        binding.apply {
            recyclerviewGenre.adapter = adapter
            recyclerviewCast.adapter = castAdapter
            recyclerviewRecommendations.adapter = recommendationsAdapter
            recyclerviewImages.adapter = imagesAdapter

            addToWatchList.setOnClickListener {
                movieId?.let {
                    if (!movieExistInWatchList) {
                        viewModel.insertMovieToList(movieId).observe(viewLifecycleOwner, {
                            when(it) {
                                is Resource.Success -> {
                                    Toast.makeText(context, it.data, Toast.LENGTH_SHORT).show()
                                }
                                is Resource.Error -> {
                                    Toast.makeText(context, it.exception, Toast.LENGTH_SHORT).show()
                                }
                                else -> Unit
                            }
                        })
                    } else {
                        viewModel.deleteMovie(movieId)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}