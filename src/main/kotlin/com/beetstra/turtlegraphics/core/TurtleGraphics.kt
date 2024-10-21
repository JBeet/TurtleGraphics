package com.beetstra.turtlegraphics.core

import kotlin.math.PI

private const val degrees2radians = PI / 180

interface TurtleGraphicsContext : WithTurtleColors {
    var x: Double
    var y: Double
    fun forward(len: Int) = forward(len.toDouble())
    fun forward(len: Double)
    fun backward(len: Int) = backward(len.toDouble())
    fun backward(len: Double)
    fun left(angle: Int)
    fun right(angle: Int)
    fun penup()
    fun pendown()
    fun pencolor(color: TurtleColor)
    fun pensize(size: Int)
    fun rgb(r: Int, g: Int, b: Int): TurtleColor
    fun sine(angleInDegrees: Int): Double = sine(angleInDegrees.toDouble())
    fun sine(angleInDegrees: Double): Double = kotlin.math.sin(angleInDegrees * degrees2radians)
    fun cosine(angleInDegrees: Int): Double = cosine(angleInDegrees.toDouble())
    fun cosine(angleInDegrees: Double): Double = kotlin.math.cos(angleInDegrees * degrees2radians)
    fun tangent(angleInDegrees: Int): Double = tangent(angleInDegrees.toDouble())
    fun tangent(angleInDegrees: Double): Double = kotlin.math.tan(angleInDegrees * degrees2radians)
}

typealias TurtleGraphics = TurtleGraphicsContext.() -> Unit
