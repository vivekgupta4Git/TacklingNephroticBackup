package com.ruviapps.tacklingnephrotic.ui.test_result

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ruviapps.tacklingnephrotic.BuildConfig
import com.ruviapps.tacklingnephrotic.databinding.ReadingSliderBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.util.*


@AndroidEntryPoint
class ResultPickerFragment : Fragment() {

    private var latestImageUri : Uri? = null
    private val firstCardPreviewImage by lazy { binding.firsCardUserImage }
    private val secondCardPreviewImage by lazy { binding.secondCardUserImage }
    private val thirdCardPreviewImage by lazy { binding.thirdCardUserImage }
    private val forthCardPreviewImage by lazy { binding.fourthCardUserImage }
    private val fifthCardPreviewImage by lazy { binding.fifthCardUserImage }
    private val sixthCardPreviewImage by lazy { binding.SixthCardUserImage }
    private val readingContainer by lazy {  binding.readingsContainer}
    private val firstCardView by lazy {   binding.firstCard }
    private val secondCardView by lazy {binding.secondCard }
    private val thirdCardView by lazy { binding.thirdCard }
    private val forthCardView by lazy {binding.fourthCard }
    private val fifthCardView by lazy {binding.fifthCard}
    private val sixthCardView by lazy {binding.SixthCard}
    private   val sliderContainer by lazy {binding.sliderContainer}


    private val takePictureContract = registerForActivityResult(ActivityResultContracts.TakePicture()){
            taken->
        if(taken){
            latestImageUri.let {
                firstCardPreviewImage.visibility = View.VISIBLE
                secondCardPreviewImage.visibility = View.VISIBLE
                thirdCardPreviewImage.visibility = View.VISIBLE
                forthCardPreviewImage.visibility = View.VISIBLE
                fifthCardPreviewImage.visibility = View.VISIBLE
                sixthCardPreviewImage.visibility = View.VISIBLE
                firstCardPreviewImage.setImageURI(it)
                secondCardPreviewImage.setImageURI(it)
                thirdCardPreviewImage.setImageURI(it)
                forthCardPreviewImage.setImageURI(it)
                fifthCardPreviewImage.setImageURI(it)
                sixthCardPreviewImage.setImageURI(it)

            }
        }else
        {
            firstCardPreviewImage.visibility  = View.GONE
            secondCardPreviewImage.visibility = View.GONE
            thirdCardPreviewImage.visibility = View.GONE
            forthCardPreviewImage.visibility = View.GONE
            fifthCardPreviewImage.visibility = View.GONE
            sixthCardPreviewImage.visibility = View.GONE
        }
    }
    val viewModel: ResultPickerViewModel by viewModels()
    private lateinit var binding: ReadingSliderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = ReadingSliderBinding.inflate(layoutInflater)
        //  viewModel.initializeDatabase()
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val container = binding.constraintLayoutContainer
        container.setOnTouchListener{ _, event->

            when(event.action and event.actionMasked){
                MotionEvent.ACTION_MOVE ->{
                    move(event)
                }


            }
            true
        }

        val cameraButton = binding.cameraButton
        cameraButton.setOnClickListener {
            takePicture()
        }


    }

  private fun move(e : MotionEvent){
      val maxUpwardMovement = readingContainer.top
      val minDownwardMovement = readingContainer.top + readingContainer.height
      val offset = sliderContainer.height/2
      val firstCardYPos = readingContainer.top
      val secondCardYPos = firstCardYPos + firstCardView.height
      val thirdCardYPos = secondCardYPos + secondCardView.height
      val forthCardYPos = thirdCardYPos + thirdCardView.height
      val fifthCardYPos = forthCardYPos + forthCardView.height
      val sixthCardYPos = fifthCardYPos + fifthCardView.height

      when(e.y.toInt()){
          in maxUpwardMovement..minDownwardMovement -> {
              sliderContainer.y = e.y - offset
              Log.d("TAG","${sliderContainer.y}, $firstCardYPos $secondCardYPos")

              when(e.y.toInt()){
                  in firstCardYPos..secondCardYPos ->  {
                      firstCardView.isActivated = true
                      secondCardView.isActivated = false
                      thirdCardView.isActivated = false
                      forthCardView.isActivated = false
                      fifthCardView.isActivated= false
                      sixthCardView.isActivated = false
                  }
                  in secondCardYPos..thirdCardYPos ->   {
                      firstCardView.isActivated = false
                      secondCardView.isActivated = true
                      thirdCardView.isActivated = false
                      forthCardView.isActivated = false
                      fifthCardView.isActivated= false
                      sixthCardView.isActivated = false
                  }
                  in thirdCardYPos..forthCardYPos ->    {
                      firstCardView.isActivated = false
                      secondCardView.isActivated = false
                      thirdCardView.isActivated = true
                      forthCardView.isActivated = false
                      fifthCardView.isActivated= false
                      sixthCardView.isActivated = false
                  }
                  in forthCardYPos..fifthCardYPos ->    {
                      firstCardView.isActivated = false
                      secondCardView.isActivated = false
                      thirdCardView.isActivated = false
                      forthCardView.isActivated = true
                      fifthCardView.isActivated= false
                      sixthCardView.isActivated = false
                  }
                  in fifthCardYPos..sixthCardYPos ->    {
                      firstCardView.isActivated = false
                      secondCardView.isActivated = false
                      thirdCardView.isActivated = false
                      forthCardView.isActivated = false
                      fifthCardView.isActivated= true
                      sixthCardView.isActivated = false
                  }
                  in sixthCardYPos..minDownwardMovement -> {
                      firstCardView.isActivated = false
                      secondCardView.isActivated = false
                      thirdCardView.isActivated = false
                      forthCardView.isActivated = false
                      fifthCardView.isActivated= false
                      sixthCardView.isActivated = true
                  }

              }

          }
    }



    }

    private fun takePicture(){
        lifecycleScope.launch {
            getTmpPicture().let {
                latestImageUri = it
                takePictureContract.launch(it)
            }
        }
    }
    private fun getTmpPicture() : Uri?{
        val tmpFile = File.createTempFile(Calendar.getInstance().timeInMillis.toString(),".jpeg",requireContext().cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }
        return   FileProvider.getUriForFile(requireContext(), "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
    }


/* binding.arrow.setOnTouchListener(touchListener)
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

        binding.arrow.y = binding.resultNegativePlusBtn.y
*/
        /*binding.result1PlusBtn.setOnClickListener {
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
*/
    }




   /* private val touchListener = OnTouchListener { view, event ->
        val x = event.rawX.toInt()
        val y = event.rawY.toInt()


        when (event.action and MotionEvent.ACTION_MASK) {



            MotionEvent.ACTION_DOWN -> {
                val lParams = view.layoutParams as FrameLayout.LayoutParams
                xDelta = x - lParams.leftMargin
                yDelta = y - lParams.topMargin
            }
            MotionEvent.ACTION_UP -> {
                val lparas = view.layoutParams as FrameLayout.LayoutParams
                xDelta = x - lparas.leftMargin
                yDelta = y- lparas.bottomMargin
            }

            MotionEvent.ACTION_MOVE -> {
                if (y - yDelta + view.height <= container.getHeight() && y - yDelta >= 0) {
                val layoutParams = view.layoutParams as FrameLayout.LayoutParams
                layoutParams.topMargin = y - yDelta
                layoutParams.bottomMargin = 0
                view.layoutParams = layoutParams
            }

              *//* if (x - xDelta + view.width <= container.getWidth() && y - yDelta + view.height <= container.getHeight() && x - xDelta >= 0 && y - yDelta >= 0) {
                    val layoutParams = view.layoutParams as FrameLayout.LayoutParams
                    layoutParams.leftMargin = x - xDelta
                    layoutParams.topMargin = y - yDelta
                    layoutParams.rightMargin = 0
                    layoutParams.bottomMargin = 0
                    view.layoutParams = layoutParams
                }*//*
            }

            MotionEvent.ACTION_BUTTON_PRESS ->{

            }


        }
        container.invalidate()
        true
    }

}*/

/*
//use of compose

override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
): View {
//below line is important for fragment; In activity we can simply call setContent() but not in fragment
    return ComposeView(requireContext()).apply {

        setContent {
            Column(verticalArrangement = Arrangement.Center,

                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()) {
                ResultButton(text = "NEGATIVE", viewModel = viewModel)
                ResultButton(text = "ONE_PLUS", viewModel = viewModel)
                ResultButton(text = "TWO_PLUS", viewModel = viewModel)
                ResultButton(text = "THREE_PLUS", viewModel = viewModel)
                ResultButton(text = "FOUR_PLUS", viewModel = viewModel)

            }

        }
    }

//Result button is a composable function
 */

