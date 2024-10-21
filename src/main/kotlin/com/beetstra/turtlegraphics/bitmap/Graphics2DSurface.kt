package com.beetstra.turtlegraphics.bitmap

import com.beetstra.turtlegraphics.core.*
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage

class Graphics2DSurface private constructor(
    private val graphics: Graphics2D,
    private val scale: BitmapScale,
    private var maxLength: Double = Double.MAX_VALUE
) : TurtleSurface {
    private val lengthHelper = GetLengthSurface()
    override fun drawLine(x1: Double, y1: Double, x2: Double, y2: Double, brush: TurtleBrush) {
        if (lengthHelper.length > maxLength) return

        graphics.color = brush.strokeColor.toJavaColor()
        graphics.stroke = BasicStroke(scale.stroke(brush.strokeWidth))
        val oldLength = lengthHelper.length
        lengthHelper.drawLine(x1, y1, x2, y2, brush)
        if (lengthHelper.length > maxLength) {
            val fraction = (maxLength - oldLength) / (lengthHelper.length - oldLength)
            val newX2 = x1 + (x2 - x1) * fraction
            val newY2 = y1 + (y2 - y1) * fraction
            graphics.drawLine(scale.x(x1), scale.y(y1), scale.x(newX2), scale.y(newY2))
        } else {
            graphics.drawLine(scale.x(x1), scale.y(y1), scale.x(x2), scale.y(y2))
        }
    }

    private class BufferedImageProducer(private val program: TurtleGraphics, height: Int) {
        private val bounds = program.calculateBounds()
        private val scale = BitmapScale(bounds.scaleToHeight(height.toDouble()))
        private val sizeX = scale.x(bounds.maxX) + 1
        private val sizeY = scale.y(bounds.maxY) + 1

        private fun draw(maxLength: Double): BufferedImage =
            BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB).apply {
                createGraphics().also {
                    it.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
                    Graphics2DSurface(it, scale, maxLength).draw(program)
                }
            }

        fun last(): BufferedImage = draw(Double.MAX_VALUE)
        fun animate(count: Int): List<BufferedImage> {
            val totalLength = program.calculateLength()
            return (0..count).map { draw((totalLength * it) / count) }
        }
    }

    private class BufferedImageAnimator(
        private val program: TurtleAnimation,
        height: Int,
        private val durationInMillis: Int
    ) {
        private val bounds = program.calculateBounds(durationInMillis)
        private val scale = BitmapScale(bounds.scaleToHeight(height.toDouble()))
        private val sizeX = scale.x(bounds.maxX) + 1
        private val sizeY = scale.y(bounds.maxY) + 1

        private fun draw(timeInMillis: Int): BufferedImage =
            BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB).apply {
                createGraphics().also {
                    it.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
                    Graphics2DSurface(it, scale).drawFrame(timeInMillis, program)
                }
            }

        fun animate(): List<BufferedImage> = (0..durationInMillis step 16).map { draw(it) }
    }

    companion object {
        fun bufferedImageFor(program: TurtleGraphics, height: Int): BufferedImage =
            BufferedImageProducer(program, height).last()

        @JvmName("bufferedGraphicsImagesFor")
        fun bufferedImagesFor(program: TurtleGraphics, height: Int, count: Int = 100): List<BufferedImage> =
            BufferedImageProducer(program, height).animate(count)

        @JvmName("bufferedAnimationImagesFor")
        fun bufferedImagesFor(program: TurtleAnimation, height: Int, durationInMillis: Int): List<BufferedImage> =
            BufferedImageAnimator(program, height, durationInMillis).animate()

        fun drawFrame(target: Graphics2D, scale: Scale, timeInMillis: Int, program: TurtleAnimation) {
            target.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            Graphics2DSurface(target, BitmapScale(scale)).drawFrame(timeInMillis, program)
        }
    }
}

private class BitmapScale(private val scale: Scale) {
    fun x(x: Double): Int = scale.x(x).toInt()
    fun y(y: Double): Int = scale.y(y).toInt()
    fun stroke(w: Double): Float = scale.stroke(w).toFloat()
}

private fun TurtleColor.toJavaColor(): Color = Color(r, g, b)
