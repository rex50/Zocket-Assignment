package com.rex50.zocketassignment.utils

import android.app.Activity
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

fun Activity.getFbHashKey() {
    try {
        val info: PackageInfo = packageManager.getPackageInfo(applicationContext.packageName, PackageManager.GET_SIGNATURES)
        for (signature in info.signatures) {
            val md = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            Log.e("fb hash", Base64.encodeToString(md.digest(), Base64.NO_WRAP))
        }
    } catch (e: PackageManager.NameNotFoundException) {
        Log.e("Name not found", e.message.toString())
    } catch (e: NoSuchAlgorithmException) {
        Log.e("Error", e.message.toString())
    }
}