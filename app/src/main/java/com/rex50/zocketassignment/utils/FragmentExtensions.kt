package com.rex50.zocketassignment.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

@JvmOverloads
fun AppCompatActivity.replaceFragment(
    containerId: Int,
    fragment: Fragment,
    transaction: (FragmentTransaction.() -> Unit)? = null
) {
    supportFragmentManager.apply {
        val tr = beginTransaction()
        tr.replace(containerId, fragment)
        if (transaction != null) {
            tr.transaction()
        }
        tr.commit()
    }
}

fun AppCompatActivity.addFragment(
    containerId: Int,
    fragment: Fragment,
    transaction: (FragmentTransaction.() -> Unit)? = null
) {
    supportFragmentManager.apply {
        val tr = beginTransaction()
        tr.add(containerId, fragment)
        if (transaction != null) {
            tr.transaction()
        }
        tr.commit()
    }
}

fun Fragment.replaceFragment(
    containerId: Int,
    fragment: Fragment,
    transaction: (FragmentTransaction.() -> Unit)? = null
) {
    childFragmentManager.apply {
        val tr = beginTransaction()
        tr.replace(containerId, fragment)
        if (transaction != null) {
            tr.transaction()
        }
        tr.commit()
    }
}

fun Fragment.addFragment(
    containerId: Int,
    fragment: Fragment,
    transaction: (FragmentTransaction.() -> Unit)? = null
) {
    childFragmentManager.apply {
        val tr = beginTransaction()
        tr.add(containerId, fragment)
        if (transaction != null) {
            tr.transaction()
        }
        tr.commit()
    }
}

fun Fragment.createPath(vararg path: String): String {
    val stringBuilder = StringBuilder()
    stringBuilder.append(requireActivity().getExternalFilesDir(null))
    path.forEach {
        stringBuilder.append(it)
    }
    return stringBuilder.toString()
}