package com.rex50.zocketassignment.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class BaseFragment<Binding> : Fragment(){

    var isFragmentLoaded: Boolean = false

    var binding: Binding? = null
        private set

    protected var fragScope: CoroutineScope? = null
        get() {
            if(!isDetached && field == null)
                field = CoroutineScope(Dispatchers.Main + SupervisorJob())
            return field
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflateViewBinding(inflater, container)
        initArguments(arguments)
        return (binding as ViewBinding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onResume() {
        super.onResume()
        whenResumed()
        if(!isFragmentLoaded) {
            isFragmentLoaded = true
            load()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        fragScope?.let {
            it.cancel("Fragment destroyed")
            fragScope = null
        }
    }

    /**
     * To bind the view
     *
     * Example: return <LayoutNameBinding>.inflate(inflater,container,false)
     *
     * @return the binding
     */
    abstract fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): Binding

    /**
     * use this function to init layouts only
     */
    abstract fun initView()

    /**
     * use this function to load
     */
    abstract fun load()

    open fun initArguments(bundle: Bundle?) {}

    open fun whenResumed(){}
}