package com.github.gunin_igor75.androidsdktask.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.gunin_igor75.androidsdktask.R
import com.github.gunin_igor75.androidsdktask.databinding.FragmentTwoBinding
import com.github.gunin_igor75.androidsdktask.navigator.navigator

class FragmentTwo: Fragment(R.layout.fragment_two) {

    private val binding: FragmentTwoBinding by viewBinding(R.id.fragmentTwo)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonForward.setOnClickListener {
            navigator().launch(FragmentThree.newInstance())
        }

        binding.buttonBack.setOnClickListener {
            navigator().goBack()
        }
    }

    companion object{
        fun newInstance() = FragmentTwo()
    }
}