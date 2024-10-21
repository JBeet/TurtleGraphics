package com.beetstra.turtlegraphics.svg

import com.beetstra.turtlegraphics.core.*
import java.util.*

object SVG {
    fun draw(program: TurtleGraphics, height: Int): String =
        draw("""height="$height" width="100%" preserveAspectRatio="xMinYMin" """, program)

    internal fun draw(attributes: String, program: TurtleGraphics): String =
        SVGSurface(attributes).apply { draw(program) }.svg
}

internal class SVGSurface(private val attributes: String) : TurtleSurface {
    private val boundsHelper = GetBoundsSurface()
    private var activeBrush = SVGBrush(1.0, SVGColor(0, 0, 0))
    private var activeLines = mutableListOf<SVGLine>()
    private val sb = StringBuilder()

    private data class SVGPoint(val x: Double, val y: Double)
    private data class SVGLine(val from: SVGPoint, val to: SVGPoint)

    override fun drawLine(x1: Double, y1: Double, x2: Double, y2: Double, brush: TurtleBrush) {
        boundsHelper.drawLine(x1, y1, x2, y2, brush)
        if (!activeBrush.matches(brush)) {
            drawActiveLines()
            activeBrush = SVGBrush(brush)
            activeLines.clear()
        }
        activeLines += SVGLine(SVGPoint(x1, y1), SVGPoint(x2, y2))
    }

    private fun drawActiveLines() {
        if (activeLines.isEmpty()) return
        val line = activeLines.singleOrNull()
        if (line != null)
            addSVGLine(line)
        else
            addSVGPath()
    }

    private fun addSVGPath() = with(sb) {
        fun appendLineTo(point: SVGPoint) {
            append("L")
            appendRounded(point.x)
            append(" ")
            appendRounded(point.y)
            append(" ")
        }

        fun appendMoveTo(point: SVGPoint) {
            append("M")
            appendRounded(point.x)
            append(" ")
            appendRounded(point.y)
            append(" ")
        }

        append("<path d=\"")
        val startPoint = activeLines.first().from
        val endPoint = activeLines.last().to
        appendMoveTo(startPoint)
        activeLines.zipWithNext { a, b ->
            appendLineTo(a.to)
            if (b.from != a.to)
                appendMoveTo(b.from)
        }
        if (endPoint == startPoint)
            append("Z")
        else
            appendLineTo(endPoint)
        append("\" ")
        activeBrush.appendTo(this, true)
        append("/>")
    }

    private fun addSVGLine(line: SVGLine) = with(sb) {
        append("<line x1=\"")
        appendRounded(line.from.x)
        append("\" y1=\"")
        appendRounded(line.from.y)
        append("\" x2=\"")
        appendRounded(line.to.x)
        append("\" y2=\"")
        appendRounded(line.to.y)
        append("\" ")
        activeBrush.appendTo(this)
        append("/>")
    }

    val svg: String
        get() {
            drawActiveLines()
            return """<svg $attributes viewBox="$viewBox" xmlns="http://www.w3.org/2000/svg">$sb</svg>"""
        }
    private val viewBox get() = boundsHelper.bounds.svg()

    private fun Bounds.svg() = buildString {
        appendRounded(minX)
        append(" ")
        appendRounded(minY)
        append(" ")
        appendRounded(width)
        append(" ")
        appendRounded(height)
    }
}

private data class SVGColor(val r: Int, val g: Int, val b: Int) {
    constructor(color: TurtleColor) : this(color.r, color.g, color.b)

    fun matches(color: TurtleColor): Boolean = r == color.r && g == color.g && b == color.b

    fun appendTo(sb: StringBuilder): Unit = with(sb) {
        append("rgb(")
        append(r)
        append(",")
        append(g)
        append(",")
        append(b)
        append(")")
    }
}

private data class SVGBrush(val strokeWidth: Double, val color: SVGColor) {
    constructor(brush: TurtleBrush) : this(brush.strokeWidth, SVGColor(brush.strokeColor))

    fun matches(brush: TurtleBrush): Boolean =
        strokeWidth == brush.strokeWidth && color.matches(brush.strokeColor)

    fun appendTo(sb: StringBuilder, specifyFill: Boolean = false): Unit = with(sb) {
        append("style=\"")
        if (specifyFill) append("fill:none;")
        append("stroke:")
        color.appendTo(this)
        append(";stroke-width:")
        appendRounded(strokeWidth)
        append("\" ")
    }
}

private fun StringBuilder.appendRounded(v: Double): StringBuilder {
    val format = "%.4f".format(Locale.ROOT, v)
    return append(format.dropLastWhile { it == '0' }.removeSuffix("."))
}
