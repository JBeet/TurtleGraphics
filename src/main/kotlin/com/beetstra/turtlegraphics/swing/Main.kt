package com.beetstra.turtlegraphics.swing

import com.beetstra.turtlegraphics.bitmap.Graphics2DSurface
import com.beetstra.turtlegraphics.core.TurtleAnimation
import com.beetstra.turtlegraphics.core.TurtleColor
import com.beetstra.turtlegraphics.core.calculateBounds
import java.awt.Canvas
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JFrame
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun main() {
    JFrame("Turtle Graphics").apply {
        setSize(400, 500)
        add(animatedFractal())
        isVisible = true
    }
}

class AnimatedTurtle(duration: Duration, private val program: TurtleAnimation) : Canvas() {
    private val totalDurationInMillis = duration.inWholeMilliseconds.toInt()
    private val bounds = program.calculateBounds(totalDurationInMillis)
    private val scale = bounds.scaleTo(400.0, 500.0)
    override fun paint(g: Graphics?) {
        super.paint(g)
        val timeInMillis = System.currentTimeMillis() % totalDurationInMillis
        if (g is Graphics2D) {
            Graphics2DSurface.drawFrame(g, scale, timeInMillis.toInt(), program)
        }
    }
}

private fun Int.power(n: Int): Int = if (n == 0) 1 else this * this.power(n - 1)

private fun animatedFractal() = AnimatedTurtle(duration = 5.seconds) {
    val maxDepth = timeInMillis / 1000
    val frame = 6 - maxDepth
    fun drawStep(depth: Int) {
        fun line(color: TurtleColor = black) {
            if (depth >= maxDepth) {
                pencolor(color)
                pensize(5 * 2.power(frame))
                forward(10 * 3.power(frame))
            } else
                drawStep(depth + 1)
        }
        line(red)
        left(60)
        line(green)
        right(120)
        line(blue)
        left(60)
        line(purple)
    }
    pendown()
    repeat(3) {
        drawStep(0)
        right(120)
    }
}
