package com.github.gunin_igor75.androidsdktask.navigator

import androidx.fragment.app.Fragment

interface Router {

    fun launch(fragment: Fragment)

    fun goBack()
}

interface RouterHandler{
    val router: Router
}