package com.beetstra.turtlegraphics.svg

import com.beetstra.turtlegraphics.core.TurtleBrush
import com.beetstra.turtlegraphics.core.TurtleColor
import kotlin.test.Test
import kotlin.test.assertEquals

class SVGSurfaceTest : TurtleBrush {
    override val strokeColor = TurtleColor.DEFAULT
    override var strokeWidth: Double = 4.0

    @Test
    fun `test a simple line`() {
        val tested = SVGSurface("test=\"ok\"")
        tested.drawLine(-10.0, -20.0, 30.0, 40.0, this)
        assertEquals(
            """<svg test="ok" viewBox="-12 -22 44 64" xmlns="http://www.w3.org/2000/svg">""" +
                    """<line x1="-10" y1="-20" x2="30" y2="40" style="stroke:rgb(0,0,0);stroke-width:4" />""" +
                    """</svg>""",
            tested.svg
        )
    }

    @Test
    fun `test two simple lines`() {
        val tested = SVGSurface("test=\"ok\"")
        tested.drawLine(-10.0, -20.0, 30.0, 40.0, this)
        strokeWidth = 2.0
        tested.drawLine(-5.0, -10.0, 20.0, 30.0, this)
        assertEquals(
            """<svg test="ok" viewBox="-12 -22 44 64" xmlns="http://www.w3.org/2000/svg">""" +
                    """<line x1="-10" y1="-20" x2="30" y2="40" style="stroke:rgb(0,0,0);stroke-width:4" />""" +
                    """<line x1="-5" y1="-10" x2="20" y2="30" style="stroke:rgb(0,0,0);stroke-width:2" />""" +
                    """</svg>""",
            tested.svg
        )
    }

    @Test
    fun `test rounding`() {
        val tested = SVGSurface("test=\"ok\"")
        tested.drawLine(-10.0, -20.0, 30.0, 40.0, this)
        strokeWidth = 2.25
        tested.drawLine(-5.5, -10.01, 20.002, 30.12345, this)
        assertEquals(
            """<svg test="ok" viewBox="-12 -22 44 64" xmlns="http://www.w3.org/2000/svg">""" +
                    """<line x1="-10" y1="-20" x2="30" y2="40" style="stroke:rgb(0,0,0);stroke-width:4" />""" +
                    """<line x1="-5.5" y1="-10.01" x2="20.002" y2="30.1235" style="stroke:rgb(0,0,0);stroke-width:2.25" />""" +
                    """</svg>""",
            tested.svg
        )
    }

    @Test
    fun `test a triangle with the same brush creates a path`() {
        val tested = SVGSurface("test=\"ok\"")
        tested.drawLine(0.0, -100.0, -75.0, 100.0, this)
        tested.drawLine(-75.0, 100.0, 75.0, 100.0, this)
        tested.drawLine(75.0, 100.0, 0.0, -100.0, this)
        assertEquals(
            """<svg test="ok" viewBox="-77 -102 154 204" xmlns="http://www.w3.org/2000/svg">""" +
                    """<path d="M0 -100 L-75 100 L75 100 Z" style="fill:none;stroke:rgb(0,0,0);stroke-width:4" />""" +
                    """</svg>""",
            tested.svg
        )
    }
}
