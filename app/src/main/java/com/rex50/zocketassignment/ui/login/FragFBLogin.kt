package com.rex50.zocketassignment.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.rex50.zocketassignment.databinding.FragFBLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragFBLogin : Fragment() {

    companion object {
        fun newInstance() = FragFBLogin()
    }

    private lateinit var loginViewBinding: FragFBLoginBinding

    private val viewModel: FragFBLoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewBinding = FragFBLoginBinding.inflate(inflater, container, false)
        return loginViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFBLogin()

        initClicks()
    }

    private fun initFBLogin() {
        // TODO: init fb button
    }

    private fun initClicks() {
        loginViewBinding.btnLogin.setOnClickListener {
            viewModel.viewModelScope.launch {
                loginViewBinding.btnLogin.startLoading()
                delay(3000)
                loginViewBinding.btnLogin.doResult(true)
            }
        }
    }

}