package com.ruviapps.tacklingnephrotic.ui.test_result

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ruviapps.tacklingnephrotic.databinding.ResultPickerFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ResultPickerFragment : Fragment() {

    val viewModel : ResultPickerViewModel by viewModels()

    private lateinit var binding : ResultPickerFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = ResultPickerFragmentBinding.inflate(layoutInflater)
        viewModel.setLog()
        return binding.root
    }


}
