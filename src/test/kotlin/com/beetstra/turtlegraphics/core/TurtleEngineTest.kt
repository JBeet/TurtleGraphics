package com.beetstra.turtlegraphics.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TurtleEngineTest {
    @Test
    fun `forward draws a line`() {
        TurtleSurfaceMock().run {
            draw {
                forward(100)
            }
            lines[0].checkPoints(0.0, 0.0, 100.0, 0.0)
        }
    }

    @Test
    fun `backward draws a line in the other direction`() {
        TurtleSurfaceMock().run {
            draw {
                backward(100)
            }
            lines[0].checkPoints(0.0, 0.0, -100.0, 0.0)
        }
    }

    @Test
    fun `a right turn changes the direction`() {
        TurtleSurfaceMock().run {
            draw {
                right(90)
                forward(100)
            }
            lines[0].checkPoints(0.0, 0.0, 0.0, 100.0)
        }
    }

    @Test
    fun `a left turn changes the direction`() {
        TurtleSurfaceMock().run {
            draw {
                left(90)
                forward(100)
            }
            lines[0].checkPoints(0.0, 0.0, 0.0, -100.0)
        }
    }

    @Test
    fun `after penup nothing is drawn`() {
        TurtleSurfaceMock().run {
            draw {
                penup()
                forward(100)
            }
            assertTrue { lines.isEmpty() }
        }
    }

    @Test
    fun `pendown reactivates drawing`() {
        TurtleSurfaceMock().run {
            draw {
                penup()
                forward(50)
                pendown()
                forward(50)
            }
            lines[0].checkPoints(50.0, 0.0, 100.0, 0.0)
        }
    }

    @Test
    fun `pencolor can be changed`() {
        TurtleSurfaceMock().run {
            draw {
                pencolor(red)
                forward(50)
            }
            val line = lines[0]
            assertEquals(255, line.brush.strokeColor.r)
            assertEquals(0, line.brush.strokeColor.g)
            assertEquals(0, line.brush.strokeColor.b)
        }
    }

    @Test
    fun `pensize can be changed`() {
        TurtleSurfaceMock().run {
            draw {
                pensize(5)
                forward(50)
            }
            val line = lines[0]
            assertEquals(5.0, line.brush.strokeWidth, 0.000000001)
        }
    }
}
