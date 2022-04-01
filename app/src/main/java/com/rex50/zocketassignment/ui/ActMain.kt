package com.rex50.zocketassignment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rex50.zocketassignment.R
import com.rex50.zocketassignment.databinding.ActMainBinding
import com.rex50.zocketassignment.ui.login.FragFBLogin
import com.rex50.zocketassignment.utils.replaceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActMain : AppCompatActivity() {

    private lateinit var homeBinding: ActMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActMainBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        replaceFragment(
            R.id.fragmentContainer,
            FragFBLogin.newInstance()
        )

    }
}