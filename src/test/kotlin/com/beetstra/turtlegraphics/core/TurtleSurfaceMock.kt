package com.beetstra.turtlegraphics.core

import kotlin.test.assertEquals

private const val epsilon = 1e-10

data class Line(val x1: Double, val y1: Double, val x2: Double, val y2: Double, val brush: TurtleBrush) {
    fun checkPoints(expX1: Double, expY1: Double, expX2: Double, expY2: Double) {
        assertEquals(expX1, x1, epsilon, "x1")
        assertEquals(expY1, y1, epsilon, "y1")
        assertEquals(expX2, x2, epsilon, "x2")
        assertEquals(expY2, y2, epsilon, "y2")
    }
}

class TurtleSurfaceMock : TurtleSurface {
    val lines = mutableListOf<Line>()
    override fun drawLine(x1: Double, y1: Double, x2: Double, y2: Double, brush: TurtleBrush) {
        lines.add(Line(x1, y1, x2, y2, brush))
    }
}
