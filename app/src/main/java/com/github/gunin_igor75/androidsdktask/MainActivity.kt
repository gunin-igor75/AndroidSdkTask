package com.github.gunin_igor75.androidsdktask

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.github.gunin_igor75.androidsdktask.databinding.ActivityMainBinding
import com.github.gunin_igor75.androidsdktask.navigator.Router
import com.github.gunin_igor75.androidsdktask.navigator.RouterHandler
import com.github.gunin_igor75.androidsdktask.screens.FragmentOne

class MainActivity : AppCompatActivity(), RouterHandler {

    private lateinit var binding: ActivityMainBinding

    override val router: Router = object : Router{
        override fun launch(fragment: Fragment) {
            launchFragment(fragment)
        }

        override fun goBack() {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            launchFragment(FragmentOne.newInstance(), false)
        }
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallBacks, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallBacks)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun launchFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        val transaction = supportFragmentManager.beginTransaction()
        if (addToBackStack) transaction.addToBackStack(null)
        transaction
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private val fragmentCallBacks = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?,
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
    }
}