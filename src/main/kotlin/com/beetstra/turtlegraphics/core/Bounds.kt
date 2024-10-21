package com.beetstra.turtlegraphics.core

data class Bounds(val minX: Double, val minY: Double, val maxX: Double, val maxY: Double) {
    val width get() = maxX - minX
    val height get() = maxY - minY

    fun scaleTo(targetWidth: Double, targetHeight: Double): Scale =
        if (targetWidth * height < width * targetHeight)
            scaleToWidth(targetWidth)
        else
            scaleToHeight(targetHeight)

    fun scaleToHeight(targetHeight: Double): Scale = FreeScale(targetHeight / height)
    fun scaleToWidth(targetWidth: Double): Scale = FreeScale(targetWidth / width)

    private inner class FreeScale(private val scale: Double) : Scale {
        override fun x(x: Double): Double = (x - minX) * scale
        override fun y(y: Double): Double = (y - minY) * scale
        override fun stroke(w: Double): Double = w * scale
    }
}

fun TurtleGraphics.calculateBounds(): Bounds = GetBoundsSurface().apply { draw(this@calculateBounds) }.bounds
const val animationCalculationSteps = 105
fun TurtleAnimation.calculateBounds(durationInMillis: Int): Bounds =
    GetBoundsSurface().apply {
        (0..animationCalculationSteps).map {
            (durationInMillis * it) / animationCalculationSteps
        }.forEach { drawFrame(it, this@calculateBounds) }
    }.bounds

internal class GetBoundsSurface : TurtleSurface {
    private var minX = 0.0
    private var minY = 0.0
    private var maxX = 1.0
    private var maxY = 1.0
    val bounds get() = Bounds(minX, minY, maxX, maxY)

    override fun drawLine(x1: Double, y1: Double, x2: Double, y2: Double, brush: TurtleBrush) {
        val strokeWidth = brush.strokeWidth
        checkMinMaxX(x1, strokeWidth)
        checkMinMaxY(y1, strokeWidth)
        checkMinMaxX(x2, strokeWidth)
        checkMinMaxY(y2, strokeWidth)
    }

    private fun checkMinMaxX(x: Double, width: Double = 0.0) {
        checkMinX(x - width / 2)
        checkMaxX(x + width / 2)
    }

    private fun checkMaxX(x: Double) {
        if (x > maxX) maxX = x
    }

    private fun checkMinX(x: Double) {
        if (x < minX) minX = x
    }

    private fun checkMinMaxY(y: Double, width: Double = 0.0) {
        checkMinY(y - width / 2)
        checkMaxY(y + width / 2)
    }

    private fun checkMinY(y: Double) {
        if (y < minY) minY = y
    }

    private fun checkMaxY(y: Double) {
        if (y > maxY) maxY = y
    }
}
