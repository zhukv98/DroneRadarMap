package edu.uc.zhukv.droneradarmap

import android.graphics.Color
import androidx.annotation.ColorInt

class FlyfrbBasePainter {
    val heightToColor: Map<Int, Int> = mutableMapOf()

    @get:ColorInt
    @ColorInt
    val colorTransparent: Int = Color.argb(0, 0, 0, 0)

}