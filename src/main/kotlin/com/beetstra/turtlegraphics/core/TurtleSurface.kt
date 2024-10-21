package com.beetstra.turtlegraphics.core

interface TurtleBrush {
    val strokeColor: TurtleColor
    val strokeWidth: Double
}

interface TurtleSurface {
    fun drawLine(x1: Double, y1: Double, x2: Double, y2: Double, brush: TurtleBrush)
}
