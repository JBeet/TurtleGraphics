# Turtle Graphics in Kotlin

## Overview

Interface `TurtleGraphicsContext` is used to define commands (and colors as 'variables').
Commands executed on that context are aliased to `TurtleGraphics`: 

    typealias TurtleGraphics = TurtleGraphicsContext.() -> Unit

Drawing is done via the `TurtleSurface` interface, which currently has implementations for
* Compose Multiplatform (`com.beetstra.turtlegraphics.compose.DrawScopeSurface`)
* Java BufferedImage (`com.beetstra.turtlegraphics.bitmap.DrawScopeSurface`)
* SVG (`com.beetstra.turtlegraphics.svg.SVGSurface`)

The `TurtleEngine` class translates the `TurtleGraphics` to calls on the `TurtleSurface` classes.

    fun TurtleSurface.draw(graphics: TurtleGraphics) = TurtleEngine(this).graphics()

## Compose Multiplatform

Should work for Multiplatform without changes, but currently only Desktop/JVM example
* Use wizard to create simple example project
* copy relevant Gradle parts to this project
  * Plugin `id("org.jetbrains.compose") version "1.6.0-alpha01"`
  * several dependencies
  * `compose.desktop { application { mainClass = ... } } }`
* Make sure plugins are installed for preview functionality
* Implementation is based on `Canvas` / `DrawScope`

## Jupyter Kotlin Notebook

* Update the settings to include the 'main' classpath for the project.
* Update the Gradle buildfile
  * Plugin `kotlin("jupyter.api") version "0.12.0-110"`
  * add setting `kotlin.jupyter.add.scanner=true` in `gradle.properties`.

### Links
* General: https://github.com/Kotlin/kotlin-jupyter?tab=readme-ov-file
* Adding libraries: https://github.com/Kotlin/kotlin-jupyter/blob/master/docs/libraries.md

