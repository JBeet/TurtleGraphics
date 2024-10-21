package com.beetstra.turtlegraphics.core

import kotlin.test.Test
import kotlin.test.assertEquals

class TurtleAnimationTest {
    @Test
    fun `timeInMillis can be used`() {
        TurtleSurfaceMock().run {
            drawFrame(100) {
                forward(timeInMillis)
            }
            lines[0].checkPoints(0.0, 0.0, 100.0, 0.0)
        }
    }

    @Test
    fun `bounds can be measured for animation`() {
        val animation: TurtleAnimation = {
            pensize(4)
            repeat(4) {
                forward(timeInMillis)
                right(90)
            }
        }
        val bounds = animation.calculateBounds(100)
        assertEquals(-2.0, bounds.minX, 0.01)
        assertEquals(-2.0, bounds.minY, 0.01)
        assertEquals(102.0, bounds.maxX, 0.01)
        assertEquals(102.0, bounds.maxY, 0.01)
    }
}
