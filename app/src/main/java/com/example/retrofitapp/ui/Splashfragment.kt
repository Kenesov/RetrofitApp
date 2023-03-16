package com.example.retrofitapp.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.retrofitapp.R
import com.example.retrofitapp.models.data.Local.LocalStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Splashfragment : Fragment(R.layout.splashfragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      /*  Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(SplashfragmentDirections.actionSplashfragmentToFirstFragment())
        }, 3000) */

        lifecycleScope.launchWhenResumed {
            delay(3000)
            if (LocalStorage().isLogin) {
                findNavController().navigate(
                    SplashfragmentDirections.actionSplashfragmentToSecondFragment()
                )
            } else {
                findNavController().navigate(
                    SplashfragmentDirections.actionSplashfragmentToFirstFragment()
                )
            }
        }
    }
}