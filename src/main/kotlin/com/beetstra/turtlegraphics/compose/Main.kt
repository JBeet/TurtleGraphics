package com.beetstra.turtlegraphics.compose

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.beetstra.turtlegraphics.core.TurtleColor
import com.beetstra.turtlegraphics.core.TurtleGraphicsContext
import kotlin.math.abs
import kotlin.time.Duration.Companion.seconds

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "TurtleGraphics") {
        App()
    }
}

@Preview
@Composable
fun AppDesktopPreview() {
    Turtle {
        stuff()
    }
}

fun Int.power(n: Int): Int = if (n == 0) 1 else this * this.power(n - 1)

@Composable
fun App() {
    MaterialTheme {
        animatedFractal()
        bouncingBall()
    }
}

@Composable
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

@Composable
private fun animatedFractal4() = AnimatedTurtle(duration = 5.seconds) {
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
        left(90)
        line(green)
        right(90)
        line(teal)
        right(90)
        line(blue)
        left(90)
        line(purple)
    }
    pendown()
    drawStep(0)
}

@Composable
fun bouncingBall() = AnimatedTurtle(duration = 60.seconds) {
    val bounceDuration = 1250
    x = 450.0
    y = -1000 * abs(sine(180 * timeInMillis / bounceDuration))
    pensize(5)
    pencolor(red)
    repeat(360) {
        if (y < -1000) penup() else pendown()
        forward(2)
        left(1)
    }
}

private fun TurtleGraphicsContext.stuff() {
    pencolor(blue)
    repeat(5) {
        forward(50)
        right(72)
    }
    pencolor(red)
    pensize(2)
    repeat(5) {
        backward(100)
        right(144)
    }
    pencolor(green)
    pensize(3)
    repeat(90) {
        if (it % 3 == 0) penup() else pendown()
        forward(4)
        right(4)
    }
    pensize(1)

    fun drawStep(l: Int) {
        fun drawPart(l: Int) = if (l <= 5) forward(l) else drawStep(l / 3)
        drawPart(l)
        left(90)
        drawPart(l)
        right(90)
        drawPart(l)
        right(90)
        drawPart(l)
        left(90)
        drawPart(l)
    }

    penup()
    backward(120)
    pendown()
    pencolor(fuchsia)
    drawStep(81)
}
