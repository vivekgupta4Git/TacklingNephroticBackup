package com.ruviApps.tacklingnephrotic.ui.slideshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruviApps.tacklingnephrotic.domain.use_cases.caretaker.CareTakerUseCases
import kotlinx.coroutines.launch

class SlideshowViewModel : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text



}