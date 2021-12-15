package com.example.moviesdb.presentation.detail_movie.dialog

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.moviesdb.R
import com.example.moviesdb.databinding.DialogAddMovieToCustomListBinding
import com.example.moviesdb.domain.model.CustomList
import com.example.moviesdb.presentation.detail_movie.DetailMovieViewModel
import com.example.moviesdb.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddMovieToCustomListDialogFragment : DialogFragment(R.layout.dialog_add_movie_to_custom_list) {

    private var _binding: DialogAddMovieToCustomListBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: DetailMovieViewModel
    private var listId: Int? = 0
    private var movieId: Int? = 0

    companion object {
        const val EXTRA_MOVIE_ID_TO_ADD = "EXTRA MOVIE ID TO ADD"

        fun newInstance(id: Int) =
            AddMovieToCustomListDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_MOVIE_ID_TO_ADD, id)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FragmentDialogTheme)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[DetailMovieViewModel::class.java]
        _binding = DialogAddMovieToCustomListBinding.bind(view)

        arguments.let {
            movieId = arguments?.getInt(EXTRA_MOVIE_ID_TO_ADD)
        }

        initObservers()
        initListeners()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.lists.collect {
                    when(it) {
                        is Resource.Success -> {
                            initSpinner(it.data)
                        }
                        else -> Unit
                    }
                }
            }
        }
    }

    private fun initSpinner(lists: List<CustomList>) {
        val titles = lists.map {
            it.title
        }
        binding.apply {
            val spinnerAdapter = context?.let {
                ArrayAdapter<String>(
                    it,
                    android.R.layout.simple_spinner_item,
                    titles
                )
            }
            spinnerAdapter.also {
                it?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerCustomLists.adapter = it
            }

            spinnerCustomLists.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val choice = parent?.getItemAtPosition(position)
                    listId = lists.singleOrNull {
                        it.title == choice
                    }?.idList
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
    }

    private fun initListeners() {
        binding.apply {
            addMovieButton.setOnClickListener {
                movieId?.let { movieId ->
                    listId?.let { listId ->
                        viewModel.insertMovieToList(movieId, listId).observe(viewLifecycleOwner, {
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
                    }
                }
                dismissAllowingStateLoss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}