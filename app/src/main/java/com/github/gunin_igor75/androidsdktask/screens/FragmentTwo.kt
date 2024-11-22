package com.github.gunin_igor75.androidsdktask.screens

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.gunin_igor75.androidsdktask.R
import com.github.gunin_igor75.androidsdktask.databinding.FragmentTwoBinding
import com.github.gunin_igor75.androidsdktask.navigator.Router
import com.github.gunin_igor75.androidsdktask.navigator.RouterHandler

class FragmentTwo : Fragment(R.layout.fragment_two) {

    private val binding: FragmentTwoBinding by viewBinding(R.id.fragmentTwo)

    private lateinit var router: Router

    override fun onAttach(context: Context) {
        super.onAttach(context)
        router = (context as RouterHandler).router
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonForward.setOnClickListener {
            router.launch(FragmentThree.newInstance())
        }

        binding.buttonBack.setOnClickListener {
            router.goBack()
        }
    }

    companion object{
        fun newInstance() = FragmentTwo()
    }
}