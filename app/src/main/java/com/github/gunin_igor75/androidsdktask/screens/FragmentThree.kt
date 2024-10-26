package com.github.gunin_igor75.androidsdktask.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.gunin_igor75.androidsdktask.R
import com.github.gunin_igor75.androidsdktask.databinding.FragmentThreeBinding
import com.github.gunin_igor75.androidsdktask.navigator.navigator

class FragmentThree: Fragment(R.layout.fragment_three) {

    private val binding: FragmentThreeBinding by viewBinding(R.id.fragmentThree)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener {
            navigator().goBack()
        }
    }

    companion object {
        fun newInstance() = FragmentThree()
    }
}