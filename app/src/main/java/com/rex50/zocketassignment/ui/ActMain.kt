package com.rex50.zocketassignment.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rex50.zocketassignment.R
import com.rex50.zocketassignment.databinding.ActMainBinding
import com.rex50.zocketassignment.ui.page.FragPageDetails
import com.rex50.zocketassignment.ui.login.FragLogin
import com.rex50.zocketassignment.ui.pages.FragPages
import com.rex50.zocketassignment.utils.getFbHashKey
import com.rex50.zocketassignment.utils.replaceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActMain : AppCompatActivity(),
    FragLogin.OnFragFBLoginInteractionListener,
    FragPages.OnFragFBPagesInteractionListener,
    FragPageDetails.OnFragHomeInteractionListener {

    private lateinit var homeBinding: ActMainBinding

    private val fragmentContainer = R.id.fragmentContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActMainBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        getFbHashKey()

        // Initially load login page
        loadLoginPage()

    }

    override fun onLoggedIn() {

        loadPageListPage()
    }

    override fun onPageSelected() {
        // show selected page details
        loadPageDetailPage()
    }

    override fun onChangePage() {
        loadPageListPage()
    }

    private fun loadLoginPage() {
        replaceFragment(
            containerId = fragmentContainer,
            fragment = FragLogin.newInstance(),
            isAnimated = true
        )
    }

    private fun loadPageListPage() {
        replaceFragment(
            containerId = fragmentContainer,
            fragment = FragPages.newInstance(),
            isAnimated = true
        )
    }

    private fun loadPageDetailPage() {
        replaceFragment(
            containerId = fragmentContainer,
            fragment = FragPageDetails.newInstance(),
            isAnimated = true
        )
    }

}