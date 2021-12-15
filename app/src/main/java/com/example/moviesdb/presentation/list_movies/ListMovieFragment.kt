package com.example.moviesdb.presentation.list_movies

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
import com.example.moviesdb.databinding.FragmentListMovieBinding
import com.example.moviesdb.presentation.adapters.ListMovieAdapter
import com.example.moviesdb.presentation.detail_movie.DetailMovieFragment
import com.example.moviesdb.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListMovieFragment: Fragment(R.layout.fragment_list_movie), ListMovieAdapter.OnItemClickListener {

    private val adapter = ListMovieAdapter(this)
    lateinit var viewModel: ListMovieViewModel
    private var _binding: FragmentListMovieBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val EXTRA_LIST_ID = "EXTRA LIST ID"
        const val EXTRA_LIST_TITLE = "EXTRA LIST TITLE"

        fun newInstance(id: Int, listTitle: String) =
            ListMovieFragment().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_LIST_ID, id)
                    putString(EXTRA_LIST_TITLE, listTitle)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ListMovieViewModel::class.java]

        val listId: Int?
        val listTitle: String
        arguments.let {
            listId = arguments?.getInt(EXTRA_LIST_ID)
            listTitle = arguments?.getString(EXTRA_LIST_TITLE).toString()
        }

        _binding = FragmentListMovieBinding.bind(view)
        binding.listRecyclerView.adapter = adapter

        if (listId != null) {
            initObservers(listId)
        }

        initUI(listTitle)
    }

    private fun initUI(listTitle: String) {
        binding.apply {
            titleList.text = listTitle
        }
    }

    private fun initObservers(listId: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movies.collect {
                    when(it) {
                        is Resource.Success -> {
                            val movies = it.data.filter { movie->
                                movie.idList == listId
                            }
                            adapter.submitList(movies)
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

    override fun onItemCLick(movieId: Int) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, DetailMovieFragment.newInstance(movieId))
            .addToBackStack(null)
            .commit()
    }

    override fun deleteMovie(movieId: Int) {
        viewModel.deleteMovie(movieId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}