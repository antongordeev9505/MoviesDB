package com.example.moviesdb.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import com.example.moviesdb.R
import com.example.moviesdb.databinding.FragmentPopularMoviesBinding
import com.example.moviesdb.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMoviesFragment : Fragment(R.layout.fragment_popular_movies) {

    private val adapter = PopularMoviesAdapter()
    lateinit var popularMoviesViewModel: PopularMoviesViewModel
    private var _binding: FragmentPopularMoviesBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popularMoviesViewModel = ViewModelProvider(requireActivity()).get(PopularMoviesViewModel::class.java)

        _binding = FragmentPopularMoviesBinding.bind(view)
        initObserver()
        initUi()
        initLoadState()
    }

    private fun initUi() {
        binding.apply {
            //concatinate 2 adapters
            listRecyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                //declare that we have to forward retry function
                header = MovieLoadStateAdapter{ adapter.retry() }, //retry functionality - retry load of another page
                footer = MovieLoadStateAdapter{ adapter.retry() },
            )
            retryButton.setOnClickListener {
                adapter.retry()
            }
        }
    }

    private fun initLoadState() {
        //show us the loadState (refresh data set)
        adapter.addLoadStateListener { loadState ->
            binding.apply {
                //when the list is refreshing with new data set
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                //loading is finished and the state is not error
                listRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                //if there is not internet connection
                retryButton.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                //empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    //if there si not more date to load
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1) {
                    listRecyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }
    }

    private fun initObserver() {
        popularMoviesViewModel.getPopularMovies().observe(this as LifecycleOwner, Observer {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}