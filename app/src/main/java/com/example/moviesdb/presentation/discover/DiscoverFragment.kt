package com.example.moviesdb.presentation.discover

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import com.example.moviesdb.R
import com.example.moviesdb.databinding.FragmentDiscoverBinding
import com.example.moviesdb.presentation.adapters.MovieLoadStateAdapter
import com.example.moviesdb.presentation.adapters.PopularMoviesAdapter
import com.example.moviesdb.presentation.detail_movie.DetailMovieFragment
import com.example.moviesdb.presentation.discover.dialog.DiscoverMoviesDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverFragment: Fragment(R.layout.fragment_discover), PopularMoviesAdapter.OnItemClickListener{

    private val adapter = PopularMoviesAdapter(this)
    lateinit var discoverViewModel: DiscoverViewModel
    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        discoverViewModel = ViewModelProvider(requireActivity()).get(DiscoverViewModel::class.java)

        _binding = FragmentDiscoverBinding.bind(view)
        initObserver()
        initUi()
        initLoadState()
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

            fab.setOnClickListener {
                showDialog()
            }
        }
    }

    private fun showDialog() {
        val fragmentManager = parentFragmentManager ?: return

        val dialog = DiscoverMoviesDialogFragment()

        dialog.show(fragmentManager, null)
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
        discoverViewModel.discoverMovies("500").observe(viewLifecycleOwner, Observer {
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