package com.example.moviesdb.presentation.discover.dialog

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.moviesdb.R
import com.example.moviesdb.databinding.DialogFragmentDiscoverMoviesBinding
import com.example.moviesdb.presentation.discover.DiscoverViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverMoviesDialogFragment : DialogFragment(R.layout.dialog_fragment_discover_movies) {

    private var _binding: DialogFragmentDiscoverMoviesBinding? = null
    private val binding get() = _binding!!
    lateinit var discoverViewModel: DiscoverViewModel


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
        discoverViewModel = ViewModelProvider(requireActivity()).get(DiscoverViewModel::class.java)

        _binding = DialogFragmentDiscoverMoviesBinding.bind(view)

        binding.apply {
            button.setOnClickListener {
                Log.d("dwdwdsss", editText2.text.toString())
                discoverViewModel.getData(
                    editText.text.toString(),
                    editText2.text.toString()
                )
                dismissAllowingStateLoss()
            }
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}