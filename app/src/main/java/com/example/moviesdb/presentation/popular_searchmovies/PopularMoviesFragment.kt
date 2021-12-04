package com.example.moviesdb.presentation.popular_searchmovies

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import com.example.moviesdb.R
import com.example.moviesdb.databinding.FragmentPopularMoviesBinding
import com.example.moviesdb.domain.model.Movie
import com.example.moviesdb.presentation.MovieLoadStateAdapter
import com.example.moviesdb.presentation.PopularMoviesAdapter
import com.example.moviesdb.presentation.detail_movie.DetailMovieFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularMoviesFragment : Fragment(R.layout.fragment_popular_movies), PopularMoviesAdapter.OnItemClickListener {

    private val adapter = PopularMoviesAdapter(this)
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
        initSearchView()
    }

    private fun initSearchView() {
        val search = binding.searchView

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null)  {
                    popularMoviesViewModel.onSearchQuery(query)
                    search.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null)  {
                    popularMoviesViewModel.onSearchQuery(newText)
                }
                return true
            }
        })

    }

    private fun initUi() {
        binding.apply {
            //concatinate 2 adapters
            listRecyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
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
        popularMoviesViewModel.moviesMediatorData.observe(viewLifecycleOwner, Observer {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        })
    }

    override fun onItemClick(id: Int) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, DetailMovieFragment.newInstance(id))
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}