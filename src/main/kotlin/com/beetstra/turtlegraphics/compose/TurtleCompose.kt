package com.beetstra.turtlegraphics.compose

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.beetstra.turtlegraphics.core.*
import kotlin.time.Duration

@Composable
fun Turtle(program: TurtleGraphics) = Canvas(modifier = Modifier.fillMaxSize()) {
    DrawScopeSurface(this, program.calculateBounds()).run {
        draw(program)
    }
}

@Composable
fun AnimatedTurtle(duration: Duration, program: TurtleAnimation) {
    val totalDurationInMillis = duration.inWholeMilliseconds.toInt()
    val transition = rememberInfiniteTransition()
    val timeInMillis = transition.animateValue(
        0, totalDurationInMillis, Int.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = TweenSpec(totalDurationInMillis, easing = LinearEasing),
        )
    )
    Canvas(modifier = Modifier.fillMaxSize()) {
        DrawScopeSurface(this, program.calculateBounds(totalDurationInMillis)).run {
            drawFrame(timeInMillis.value, program)
        }
    }
}

private class DrawScopeSurface(private val ds: DrawScope, bounds: Bounds) : TurtleSurface {
    private val scale = ToFloatScale(bounds.scaleTo(ds.size.width.toDouble(), ds.size.height.toDouble()))
    override fun drawLine(x1: Double, y1: Double, x2: Double, y2: Double, brush: TurtleBrush) {
        ds.drawLine(brush.dsColor, offset(x1, y1), offset(x2, y2), scale.stroke(brush.strokeWidth))
    }

    private fun offset(x: Double, y: Double) = Offset(scale.x(x), scale.y(y))

    private val TurtleBrush.dsColor: Color get() = strokeColor.dsColor
    private val TurtleColor.dsColor: Color get() = Color(r, g, b)
}

private class ToFloatScale(private val scale: Scale) {
    fun x(x: Double): Float = scale.x(x).toFloat()
    fun y(y: Double): Float = scale.y(y).toFloat()
    fun stroke(w: Double): Float = scale.stroke(w).toFloat()
}
