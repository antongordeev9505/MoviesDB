package com.example.moviesdb.presentation.discover.dialog

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviesdb.R
import com.example.moviesdb.databinding.DialogFragmentDiscoverMoviesBinding
import com.example.moviesdb.domain.model.Genres
import com.example.moviesdb.presentation.discover.DiscoverViewModel
import com.example.moviesdb.util.Resource
import com.example.moviesdb.util.showToast
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverMoviesDialogFragment : DialogFragment(R.layout.dialog_fragment_discover_movies) {

    private var _binding: DialogFragmentDiscoverMoviesBinding? = null
    private val binding get() = _binding!!
    lateinit var discoverViewModel: DiscoverViewModel
    private var sortParameter = ""
    private var withGenre = ""
    private var minVoteAmount = 0
    private var minAverageVote = 0

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
        discoverViewModel = ViewModelProvider(requireActivity())[DiscoverViewModel::class.java]

        _binding = DialogFragmentDiscoverMoviesBinding.bind(view)

        initUi()
        initObservers()
    }

    private fun getSortParameter() =
        when (sortParameter) {
            "Vote" -> "vote_average.desc"
            "Descended date" -> "release_date.desc"
            "Ascended date" -> "release_date.asc"
            else -> "popularity.desc"
        }

    private fun initUi() {
        initSearchButton()
        binding.apply {
            sortByChipGroup.setOnCheckedChangeListener { group, checkedId ->
                sortParameter = group.findViewById<Chip>(checkedId)?.text.toString()
            }

            minAmountVoteChipGroup.setOnCheckedChangeListener { group, checkedId ->
                val parameter = group.findViewById<Chip>(checkedId)?.text.toString()
                minVoteAmount = parameter.substring(0, parameter.length - 1).toInt()
            }

            averageVoteChipGroup.setOnCheckedChangeListener { group, checkedId ->
                val parameter = group.findViewById<Chip>(checkedId)?.text.toString()
                minAverageVote = parameter.substring(0, parameter.length - 1).toInt()
            }
        }
    }

    private fun initSearchButton() {
        binding.apply {
            button.setOnClickListener {
                discoverViewModel.getData(
                    releaseYear = releaseYearEditText.text.toString().toIntOrNull(),
                    sortBy = getSortParameter(),
                    withGenre = withGenre,
                    minVoteCount = minVoteAmount,
                    voteAverage = minAverageVote
                )
                dismissAllowingStateLoss()
            }
        }
    }

    private fun initObservers() {
        discoverViewModel.getGenres().observe(viewLifecycleOwner, Observer {
            binding.apply {
                when (it) {
                    is Resource.Success -> {
                        loadingStatus.isVisible = false
                        initSpinner(it.data.genres)
                    }
                    is Resource.Loading -> {
                        loadingStatus.isVisible = true
                    }

                    is Resource.Error -> {
                        showToast("Problems with internet connection")
                    }
                    else -> Unit
                }
            }
        })
    }

    private fun initSpinner(list: List<Genres.Genre>) {
        val nameGenres = list.map {
            it.name
        }.toMutableList()
        nameGenres.add(0, "Any")

        binding.apply {
            val spinnerAdapter = context?.let {
                ArrayAdapter<String>(
                    it,
                    android.R.layout.simple_spinner_item,
                    nameGenres
                )
            }
            spinnerAdapter.also {
                it?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerGenres.adapter = it
            }

            spinnerGenres.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val choice = parent?.getItemAtPosition(position)
                    withGenre = list.singleOrNull {
                        it.name == choice
                    }?.id.toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}