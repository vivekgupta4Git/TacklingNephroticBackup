package com.ruviapps.tacklingnephrotic.ui.test_result

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ruviapps.tacklingnephrotic.R
import com.ruviapps.tacklingnephrotic.database.entities.ResultCode
import com.ruviapps.tacklingnephrotic.databinding.ResultPickerFragmentBinding
import com.ruviapps.tacklingnephrotic.domain.TestResult
import com.ruviapps.tacklingnephrotic.ui.home.HomeFragment
import com.ruviapps.tacklingnephrotic.utility.Event
import com.ruviapps.tacklingnephrotic.utility.EventObserver
import com.ruviapps.tacklingnephrotic.utility.NavigationCommand
import dagger.hilt.android.AndroidEntryPoint
import java.time.*


@AndroidEntryPoint
class ResultPickerFragment : Fragment() {

    val viewModel : ResultPickerViewModel by viewModels()

    private lateinit var binding : ResultPickerFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = ResultPickerFragmentBinding.inflate(layoutInflater)
      //  viewModel.initializeDatabase()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.navigateToDashBoard.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { command ->
                when(command){
                    is NavigationCommand.ToDirection ->
                        findNavController().navigate(command.directions)
                    is NavigationCommand.ShowError -> {
                        Toast.makeText(requireContext(),command.errMsg,Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }


        binding.result1PlusBtn.setOnClickListener {
            viewModel.saveResult(TestResult(LocalDate.now(),
                ResultCode.ONE_PLUS.name,
                "",
                1))


        }
        binding.resultTracePlusBtn.setOnClickListener {
            viewModel.saveResult(TestResult(  LocalDate.now(),ResultCode.TRACE.name,"",
               1))

        }
        binding.result2PlusBtn.setOnClickListener {
            viewModel.saveResult(TestResult( LocalDate.now(), ResultCode.TWO_PLUS.name,
                "",1))

        }
        binding.result3PlusBtn.setOnClickListener {
            viewModel.saveResult(TestResult( LocalDate.now(), ResultCode.THREE_PLUS.name,"",
                1))

        }
        binding.result4PlusBtn.setOnClickListener {
            viewModel.saveResult(TestResult( LocalDate.now(), ResultCode.FOUR_PLUS.name,
                "",1))

        }
        binding.resultNegativePlusBtn.setOnClickListener {
            viewModel.saveResult(TestResult( LocalDate.now(), ResultCode.NEGATIVE.name,
                "",1))

        }

    }
}
