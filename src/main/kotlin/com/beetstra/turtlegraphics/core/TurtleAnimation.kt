package com.beetstra.turtlegraphics.core

interface TurtleAnimationContext : TurtleGraphicsContext {
    val timeInMillis: Int
}

typealias TurtleAnimation = TurtleAnimationContext.() -> Unit
