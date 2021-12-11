package com.example.moviesdb.presentation.discover.dialog

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviesdb.R
import com.example.moviesdb.databinding.DialogFragmentDiscoverMoviesBinding
import com.example.moviesdb.presentation.adapters.AllGenresAdapter
import com.example.moviesdb.presentation.discover.DiscoverViewModel
import com.example.moviesdb.util.Resource
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverMoviesDialogFragment : DialogFragment(R.layout.dialog_fragment_discover_movies) {

    private var _binding: DialogFragmentDiscoverMoviesBinding? = null
    private val binding get() = _binding!!
    lateinit var discoverViewModel: DiscoverViewModel
    private val adapter = AllGenresAdapter()
    private var sortParameter = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FragmentDialogTheme)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT)
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
            recyclerviewGenre.adapter = adapter

            binding.sortByChipGroup.setOnCheckedChangeListener { group, checkedId ->
                sortParameter = group.findViewById<Chip>(checkedId)?.text.toString()
            }
        }
    }

    private fun initSearchButton() {
        binding.apply {
            button.setOnClickListener {
                discoverViewModel.getData(
                    withCast = editText.text.toString(),
                    sortBy = getSortParameter()
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
                        adapter.submitList(it.data.genres)

                    }
                    is Resource.Loading -> {
                    }

                    is Resource.Error -> {
                    }
                    else -> Unit
                }
            }
        })
    }

    private fun showToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}