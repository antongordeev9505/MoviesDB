package com.example.moviesdb.presentation.main.dialog

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.moviesdb.R
import com.example.moviesdb.databinding.DialogAddCustomListBinding
import com.example.moviesdb.presentation.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCustomListDialogFragment : DialogFragment(R.layout.dialog_add_custom_list) {

    private var _binding: DialogAddCustomListBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: MainViewModel

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
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        _binding = DialogAddCustomListBinding.bind(view)
        initListeners()
    }

    private fun initListeners() {
        binding.apply {
            addCustomListBtn.setOnClickListener {
                if (!titleListInput.text.isNullOrEmpty()) {
                    Log.d("proverka", titleListInput.text.toString())
                    val text = titleListInput.text.toString()
                    viewModel.insertCustomList(
                        listTitle = text,
                    )
                    dismissAllowingStateLoss()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}