package com.github.gunin_igor75.androidsdktask.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.gunin_igor75.androidsdktask.R
import com.github.gunin_igor75.androidsdktask.databinding.FragmentOneBinding
import com.github.gunin_igor75.androidsdktask.navigator.navigator


class FragmentOne : Fragment(R.layout.fragment_one) {

    private val binding: FragmentOneBinding by viewBinding(R.id.fragmentOne)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            navigator().launch(FragmentTwo.newInstance())
        }
    }

    companion object {
        fun newInstance() = FragmentOne()
    }
}