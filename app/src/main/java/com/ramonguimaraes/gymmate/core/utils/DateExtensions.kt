package com.ramonguimaraes.gymmate.core.utils

import java.text.SimpleDateFormat
import java.util.Date

fun Date.format(pattern: String = "dd 'de' MMMM 'de' yyyy 'às' HH:mm"): String {
    val format = SimpleDateFormat(pattern)
    return format.format(this)
}