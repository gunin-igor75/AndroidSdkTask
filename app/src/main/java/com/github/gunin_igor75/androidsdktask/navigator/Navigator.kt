package com.github.gunin_igor75.androidsdktask.navigator

import androidx.fragment.app.Fragment

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {

    fun launch(fragment: Fragment)

    fun goBack()
}