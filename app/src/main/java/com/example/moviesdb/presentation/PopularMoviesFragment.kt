package com.example.moviesdb.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
        Log.d("proverka", "fragment launched")


        _binding = FragmentPopularMoviesBinding.bind(view)
        initObserver()

        binding.apply {
            listRecyclerView.adapter = adapter
        }

    }

    private fun initObserver() {
        popularMoviesViewModel.getMovies().observe(this as LifecycleOwner, Observer {
            adapter.submitList(it.data)
            when(it) {
                is Resource.Success -> {
                    Log.d("proverka", "Success")

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}