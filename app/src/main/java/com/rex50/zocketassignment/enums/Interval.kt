package com.rex50.zocketassignment.enums

import java.util.concurrent.TimeUnit

enum class Interval(
    val interval: Long,
    val unit: TimeUnit
) {

    FIFTEEN_MINUTES(15, TimeUnit.MINUTES),
    THIRTY_MINUTES(30, TimeUnit.MINUTES),
    ONE_HOUR(1, TimeUnit.HOURS),
    THREE_HOURS(3, TimeUnit.HOURS),
    SIX_HOURS(6, TimeUnit.HOURS),
    TWELVE_HOURS(12, TimeUnit.HOURS),
    TWENTY_FOUR_HOURS(24, TimeUnit.HOURS)

}