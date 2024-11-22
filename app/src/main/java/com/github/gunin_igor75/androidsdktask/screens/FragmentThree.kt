package com.github.gunin_igor75.androidsdktask.screens

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.gunin_igor75.androidsdktask.R
import com.github.gunin_igor75.androidsdktask.databinding.FragmentThreeBinding
import com.github.gunin_igor75.androidsdktask.navigator.Router
import com.github.gunin_igor75.androidsdktask.navigator.RouterHandler

class FragmentThree: Fragment(R.layout.fragment_three) {

    private val binding: FragmentThreeBinding by viewBinding(R.id.fragmentThree)

    private lateinit var router: Router

    override fun onAttach(context: Context) {
        super.onAttach(context)
        router = (context as RouterHandler).router
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener {
            router.goBack()
        }
    }

    companion object {
        fun newInstance() = FragmentThree()
    }
}