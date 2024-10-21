package com.beetstra.turtlegraphics.core

import kotlin.math.sqrt

fun TurtleGraphics.calculateLength(): Double = GetLengthSurface().apply { draw(this@calculateLength) }.length

internal class GetLengthSurface : TurtleSurface {
    var length: Double = 0.0
        private set

    override fun drawLine(x1: Double, y1: Double, x2: Double, y2: Double, brush: TurtleBrush) {
        length += sqrt((x2 - x1).squared + (y2 - y1).squared)
        length += 20
    }

    private val Double.squared get() = this * this
}
