package com.example.pickrecipe.ui.pantry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PantryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is pantry Fragment"
    }
    val text: LiveData<String> = _text
}