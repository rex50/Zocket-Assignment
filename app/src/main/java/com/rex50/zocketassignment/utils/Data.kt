package com.rex50.zocketassignment.utils

/**
 * A generic wrapper class around data request
 */
data class Data<T>(
    var status: Status,
    var data: T? = null,
    var error: Exception? = null
)

enum class Status {
    SUCCESSFUL, ERROR, LOADING
}