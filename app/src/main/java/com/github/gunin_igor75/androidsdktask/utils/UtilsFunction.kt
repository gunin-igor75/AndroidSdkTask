package com.github.gunin_igor75.androidsdktask.utils

import android.os.Build
import android.os.Bundle
import android.os.Parcelable


@Suppress("DEPRECATION")
inline fun <reified T : Parcelable> Bundle.getParcelableProvider(keyParcelable: String): T? {

    return if (Build.VERSION.SDK_INT >= 33) {
        this.getParcelable(keyParcelable, T::class.java)
    } else {
        this.getParcelable(keyParcelable)
    }
}