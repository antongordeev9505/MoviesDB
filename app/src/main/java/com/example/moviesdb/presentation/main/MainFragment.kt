package com.example.moviesdb.presentation.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.moviesdb.R
import com.example.moviesdb.databinding.FragmentMainBinding
import com.example.moviesdb.presentation.adapters.CustomListsAdapter
import com.example.moviesdb.presentation.adapters.SpacingItemDecoration
import com.example.moviesdb.presentation.adapters.WatchListAdapter
import com.example.moviesdb.presentation.detail_movie.DetailMovieFragment
import com.example.moviesdb.presentation.list_movies.ListMovieFragment
import com.example.moviesdb.presentation.main.dialog.AddCustomListDialogFragment
import com.example.moviesdb.util.Resource
import com.example.moviesdb.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main),
    WatchListAdapter.OnItemClickListener,
    CustomListsAdapter.OnItemClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: MainViewModel
    private val adapter = WatchListAdapter(this)
    private val customListAdapter = CustomListsAdapter(this)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        _binding = FragmentMainBinding.bind(view)
        initRV()
        initObservers()
        initListeners()
    }

    private fun initRV() {
        binding.recyclerviewWatchList.adapter = adapter
        binding.recyclerviewCustomLists.adapter = customListAdapter

        resources.getDimensionPixelSize(R.dimen.dimen_8dp).let {
            SpacingItemDecoration(it).let { spacing ->
                binding.recyclerviewCustomLists.addItemDecoration(spacing)
            }
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movies.collect {
                    when(it) {
                        is Resource.Success -> {
                            val movies = it.data.filter { movie->
                                movie.idList == 0
                            }
                            adapter.submitList(movies)
                        }
                        is Resource.Error -> {
                            showToast(it.exception)
                        }
                        else -> Unit
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.lists.collect {
                    when(it) {
                        is Resource.Success -> {
                            customListAdapter.submitList(it.data)
                        }
                        is Resource.Error -> {
                            showToast(it.exception)
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun initListeners() {
        binding.apply {
            fab.setOnClickListener {
                showDialog()
            }
        }
    }

    private fun showDialog() {
        val fragmentManager = parentFragmentManager

        val dialog = AddCustomListDialogFragment()
        dialog.show(fragmentManager, null)
    }

    override fun onItemClick(id: Int) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, DetailMovieFragment.newInstance(id))
            .addToBackStack(null)
            .commit()
    }

    override fun deleteMovie(id: Int) {
        viewModel.deleteMovie(id)
    }

    override fun onItemListClick(listId: Int, listTitle: String) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ListMovieFragment.newInstance(listId, listTitle))
            .addToBackStack(null)
            .commit()
    }

    override fun deleteList(listId: Int) {
        viewModel.deleteList(listId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}