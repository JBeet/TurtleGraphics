package com.beetstra.turtlegraphics.jupyter

import com.beetstra.turtlegraphics.bitmap.Graphics2DSurface
import com.beetstra.turtlegraphics.core.TurtleAnimationContext
import com.beetstra.turtlegraphics.core.TurtleGraphicsContext
import com.beetstra.turtlegraphics.svg.SVG
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.jetbrains.kotlinx.jupyter.api.HTML
import org.jetbrains.kotlinx.jupyter.api.libraries.ExecutionHost
import java.awt.image.BufferedImage
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

const val DEFAULT_HEIGHT = 200

sealed interface AnimatedTurtleRenderer {
    fun animate(program: TurtleAnimationContext.() -> Unit)
}

sealed interface TurtleRenderer {
    fun draw(program: TurtleGraphicsContext.() -> Unit)
}

fun AnimatedTurtleRenderer(host: ExecutionHost, height: Int, duration: Duration): AnimatedTurtleRenderer {
    require(height > 0) { "Height must be positive but was $height" }
    require(duration.inWholeMilliseconds > 0) { "Duration must be positive but was $duration" }
    return BitmapRenderer(host, height, duration)
}

fun TurtleRenderer(host: ExecutionHost, height: Int, duration: Duration): TurtleRenderer {
    require(height > 0) { "Height must be positive but was $height" }
    if (duration.inWholeMilliseconds <= 0)
        return SVGRenderer(host, height)
    return BitmapRenderer(host, height, duration)
}

class SVGRenderer(private val host: ExecutionHost, private val height: Int) : TurtleRenderer {
    override fun draw(program: TurtleGraphicsContext.() -> Unit) {
        val svg = SVG.draw(program, height)
        host.execute { display(HTML("""<div style="display: inline-block">$svg</div>"""), null) }
    }
}

data class BitmapRenderer(
    private val host: ExecutionHost,
    private val height: Int,
    private val duration: Duration = 0.seconds
) : TurtleRenderer, AnimatedTurtleRenderer {
    override fun animate(program: TurtleAnimationContext.() -> Unit) = runBlocking {
        val durationInMillis = duration.inWholeMilliseconds.toInt()
        val images = Graphics2DSurface.bufferedImagesFor(program, height, durationInMillis)
        showImages(images)
    }

    override fun draw(program: TurtleGraphicsContext.() -> Unit) = runBlocking {
        val images = if (duration.isPositive())
            Graphics2DSurface.bufferedImagesFor(program, height)
        else
            listOf(Graphics2DSurface.bufferedImageFor(program, height))
        showImages(images)
    }

    private suspend fun showImages(images: List<BufferedImage>) {
        val delay = duration.inWholeMilliseconds / images.size
        val displayId = "TurtleGraphics" + Math.random().toString() + hashCode()
        images.forEachIndexed { index, image ->
            if (index == 0)
                host.execute { display(image, displayId) }
            else {
                delay(delay)
                host.execute { updateDisplay(image, displayId) }
            }
        }
    }
}

