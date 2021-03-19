package com.example.pickrecipe.ui.pantry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PantryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Select an ingredient and click on the \"+\" button to add it to your pantry. "
    }
    val text: LiveData<String> = _text
}