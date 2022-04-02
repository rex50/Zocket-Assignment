package com.rex50.zocketassignment.ui.base

import android.content.Context
import androidx.viewbinding.BuildConfig
import java.lang.Exception

abstract class BaseFragmentWithListener<Binding, Listener> : BaseFragment<Binding>(){

    var listener: Listener? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)

        whenAttached(context)

        val exception = RuntimeException(context.toString()
                + " must implement OnFragmentInteractionListener")

        listener = try {
            context as Listener?
        } catch (e: Exception) {
            when {
                BuildConfig.DEBUG -> {
                    throw exception
                }
                else -> null
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    open fun whenAttached(context: Context) {}
}