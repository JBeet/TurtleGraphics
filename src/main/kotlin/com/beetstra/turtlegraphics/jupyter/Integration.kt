package com.beetstra.turtlegraphics.jupyter

import com.beetstra.turtlegraphics.core.TurtleAnimation
import com.beetstra.turtlegraphics.core.TurtleGraphics
import com.beetstra.turtlegraphics.svg.SVG
import org.jetbrains.kotlinx.jupyter.api.annotations.JupyterLibrary
import org.jetbrains.kotlinx.jupyter.api.libraries.ExecutionHost
import org.jetbrains.kotlinx.jupyter.api.libraries.JupyterIntegration
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

data class AnimatedTurtle(
    val height: Int = DEFAULT_HEIGHT, val duration: Duration = 2.seconds,
    val program: TurtleAnimation
) {
    fun renderWithHost(host: ExecutionHost) {
        val renderer = AnimatedTurtleRenderer(host, height, duration)
        renderer.animate(program)
    }
}

data class Turtle(
    val height: Int = DEFAULT_HEIGHT, val duration: Duration = 0.seconds,
    val program: TurtleGraphics
) {
    fun renderWithHost(host: ExecutionHost) {
        val renderer = TurtleRenderer(host, height, duration)
        renderer.draw(program)
    }

    val svg: String get() = SVG.draw(program, height)
}

@JupyterLibrary
class Integration : JupyterIntegration() {
    override fun Builder.onLoaded() {
        import("com.beetstra.turtlegraphics.jupyter.Turtle")
        import("com.beetstra.turtlegraphics.jupyter.AnimatedTurtle")
        import("com.beetstra.turtlegraphics.core.TurtleGraphicsContext")
        import("com.beetstra.turtlegraphics.core.TurtleColor")
        renderWithHost<Turtle> { host, turtle ->
            turtle.renderWithHost(host)
        }
        renderWithHost<AnimatedTurtle> { host, turtle ->
            turtle.renderWithHost(host)
        }
    }
}
