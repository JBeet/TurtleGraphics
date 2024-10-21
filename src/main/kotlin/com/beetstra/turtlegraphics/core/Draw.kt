package com.beetstra.turtlegraphics.core

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

fun TurtleSurface.draw(graphics: TurtleGraphics) = TurtleEngine(this).graphics()
fun TurtleSurface.drawFrame(timeInMillis: Int, animation: TurtleAnimation) =
    TurtleEngine(this, timeInMillis).animation()

private data class SimpleTurtleBrush(override val strokeColor: TurtleColor, override val strokeWidth: Double) :
    TurtleBrush {
    fun withStrokeColor(color: TurtleColor) = copy(strokeColor = color)

    fun withStrokeWidth(size: Int) = copy(strokeWidth = size.toDouble())
}

private class TurtleEngine(private val surface: TurtleSurface, override val timeInMillis: Int = 0) :
    TurtleGraphicsContext, TurtleAnimationContext {
    override var x = 0.0
    override var y = 0.0
    private var direction = 0.0
    private var isPenDown = true
    private var brush = SimpleTurtleBrush(TurtleColor.DEFAULT, 1.0)

    override fun forward(len: Double) {
        val endX = x + (len * cos(direction * PI / 180))
        val endY = y + (len * sin(direction * PI / 180))
        drawLineTo(endX, endY)
    }

    private fun drawLineTo(newX: Double, newY: Double) {
        if (isPenDown) surface.drawLine(x, y, newX, newY, brush)
        x = newX
        y = newY
    }

    override fun backward(len: Double) = forward(-len)
    override fun left(angle: Int) = right(-angle)
    override fun right(angle: Int) {
        direction += angle
    }

    override fun penup() {
        isPenDown = false
    }

    override fun pendown() {
        isPenDown = true
    }

    override fun pencolor(color: TurtleColor) {
        brush = brush.withStrokeColor(color)
    }

    override fun pensize(size: Int) {
        brush = brush.withStrokeWidth(size)
    }

    override fun rgb(r: Int, g: Int, b: Int) = object : TurtleColor {
        override val r: Int
            get() = r
        override val g: Int
            get() = g
        override val b: Int
            get() = b
    }
}

