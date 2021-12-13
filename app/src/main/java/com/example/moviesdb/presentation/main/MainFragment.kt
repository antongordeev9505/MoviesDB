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
import com.example.moviesdb.presentation.adapters.WatchListAdapter
import com.example.moviesdb.presentation.detail_movie.DetailMovieFragment
import com.example.moviesdb.presentation.main.dialog.AddCustomListDialogFragment
import com.example.moviesdb.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main), WatchListAdapter.OnItemClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: MainViewModel
    private val adapter = WatchListAdapter(this)
    private val customListAdapter = CustomListsAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        _binding = FragmentMainBinding.bind(view)
        binding.recyclerviewWatchList.adapter = adapter
        binding.recyclerviewCustomLists.adapter = customListAdapter

        initObservers()
        initListeners()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movies.collect {
                    when(it) {
                        is Resource.Success -> {
                            adapter.submitList(it.data)
                        }
                        is Resource.Error -> {
                            Toast.makeText(context, it.exception, Toast.LENGTH_SHORT).show()
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
                            Log.d("proverkaobserve", it.data.toString())
                            customListAdapter.submitList(it.data)
                        }
                        is Resource.Error -> {
                            Toast.makeText(context, it.exception, Toast.LENGTH_SHORT).show()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}