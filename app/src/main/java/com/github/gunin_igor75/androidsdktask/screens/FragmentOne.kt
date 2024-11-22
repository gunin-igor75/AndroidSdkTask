package com.github.gunin_igor75.androidsdktask.screens

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.gunin_igor75.androidsdktask.R
import com.github.gunin_igor75.androidsdktask.databinding.FragmentOneBinding
import com.github.gunin_igor75.androidsdktask.navigator.Router
import com.github.gunin_igor75.androidsdktask.navigator.RouterHandler


class FragmentOne : Fragment(R.layout.fragment_one) {

    private val binding: FragmentOneBinding by viewBinding(R.id.fragmentOne)

    private lateinit var router: Router

    override fun onAttach(context: Context) {
        super.onAttach(context)
        router = (context as RouterHandler).router
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            router.launch(FragmentTwo.newInstance())
        }
    }

    companion object {
        fun newInstance() = FragmentOne()
    }
}