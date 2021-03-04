package com.example.pickrecipe.ui.pantry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pickrecipe.R

class PantryFragment : Fragment() {

    private lateinit var pantryViewModel: PantryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pantryViewModel =
            ViewModelProvider(this).get(PantryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_pantry, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        pantryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}