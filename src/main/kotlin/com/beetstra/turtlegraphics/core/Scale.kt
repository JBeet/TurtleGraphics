package com.beetstra.turtlegraphics.core

interface Scale {
    fun x(x: Double): Double
    fun y(y: Double): Double
    fun stroke(w: Double): Double
}
