package kr.co.ldcc.contentsservice.etc

import android.content.Context

class DisplayMetric(context: Context) {
    val displayMetric = context.resources.displayMetrics
    val height : Int = displayMetric.heightPixels
    val width : Int = displayMetric.widthPixels
}