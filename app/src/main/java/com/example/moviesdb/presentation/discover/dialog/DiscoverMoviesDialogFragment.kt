package com.example.moviesdb.presentation.discover.dialog

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.moviesdb.R
import com.example.moviesdb.databinding.DialogFragmentDiscoverMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverMoviesDialogFragment : DialogFragment(R.layout.dialog_fragment_discover_movies) {

    private var _binding: DialogFragmentDiscoverMoviesBinding? = null
    private val binding get() = _binding!!

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

        _binding = DialogFragmentDiscoverMoviesBinding.bind(view)

        binding.button.setOnClickListener {
            showToast()
        }
    }

    private fun showToast() {
        Toast.makeText(activity, "Button is clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}