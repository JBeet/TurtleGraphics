package com.beetstra.turtlegraphics.core

import kotlin.test.Test
import kotlin.test.assertEquals

private const val epsilon = 1e-10

class BoundsTest {
    @Test
    fun `scale evenly spaced bounds starting at 0`() {
        val bounds = Bounds(0.0, 0.0, 400.0, 300.0)
        val tested = bounds.scaleTo(200.0, 150.0)
        assertEquals(50.0, tested.x(100.0), epsilon)
        assertEquals(75.0, tested.y(150.0), epsilon)
    }

    @Test
    fun `scale evenly spaced bounds not at offset 0`() {
        val bounds = Bounds(-200.0, -150.0, 400.0, 300.0)
        val tested = bounds.scaleTo(200.0, 150.0)
        assertEquals(150.0, tested.x(250.0), epsilon)
        assertEquals(100.0, tested.y(150.0), epsilon)
    }

    @Test
    fun `scale preserving aspect ratio with target taller, image at top`() {
        val bounds = Bounds(-200.0, -150.0, 400.0, 300.0)
        val tested = bounds.scaleTo(500.0, 500.0)
        assertEquals(375.0, tested.x(250.0), epsilon)
        assertEquals(250.0, tested.y(150.0), epsilon)
    }

    @Test
    fun `scale preserving aspect ratio with target wider, image to left`() {
        val bounds = Bounds(-200.0, -150.0, 400.0, 300.0)
        val tested = bounds.scaleTo(300.0, 150.0)
        assertEquals(150.0, tested.x(250.0), epsilon)
        assertEquals(100.0, tested.y(150.0), epsilon)
    }

    @Test
    fun `measure bounds for a simple line`() {
        val program: TurtleGraphics = {
            pensize(6)
            forward(100)
        }
        val bounds = program.calculateBounds()
        assertEquals(-3.0, bounds.minX, epsilon)
        assertEquals(-3.0, bounds.minY, epsilon)
        assertEquals(103.0, bounds.maxX, epsilon)
        assertEquals(3.0, bounds.maxY, epsilon)
    }

    @Test
    fun `measure bounds for a rectangle`() {
        val program: TurtleGraphics = {
            pensize(6)
            repeat(4) {
                forward(100)
                right(90)
                forward(50)
                right(90)
            }
        }
        val bounds = program.calculateBounds()
        assertEquals(-3.0, bounds.minX, epsilon)
        assertEquals(-3.0, bounds.minY, epsilon)
        assertEquals(103.0, bounds.maxX, epsilon)
        assertEquals(53.0, bounds.maxY, epsilon)
    }

    @Test
    fun `measure bounds when x and y are set`() {
        val program: TurtleGraphics = {
            pensize(6)
            x = -50.0
            y = -20.0;
            repeat(4) {
                forward(100)
                right(90)
                forward(50)
                right(90)
            }
        }
        val bounds = program.calculateBounds()
        assertEquals(-53.0, bounds.minX, epsilon)
        assertEquals(-23.0, bounds.minY, epsilon)
        assertEquals(53.0, bounds.maxX, epsilon)
        assertEquals(33.0, bounds.maxY, epsilon)
    }
}
