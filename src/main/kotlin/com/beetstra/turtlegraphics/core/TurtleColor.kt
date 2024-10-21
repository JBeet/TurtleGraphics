package com.beetstra.turtlegraphics.core

interface TurtleColor {
    object DEFAULT : TurtleColor by NamedColors.BLACK

    val r: Int
    val g: Int
    val b: Int
}

interface WithTurtleColors {
    val white: TurtleColor get() = NamedColors.WHITE
    val silver: TurtleColor get() = NamedColors.SILVER
    val gray: TurtleColor get() = NamedColors.GRAY
    val black: TurtleColor get() = NamedColors.BLACK
    val red: TurtleColor get() = NamedColors.RED
    val orange: TurtleColor get() = NamedColors.ORANGE
    val maroon: TurtleColor get() = NamedColors.MAROON
    val yellow: TurtleColor get() = NamedColors.YELLOW
    val olive: TurtleColor get() = NamedColors.OLIVE
    val lime: TurtleColor get() = NamedColors.LIME
    val green: TurtleColor get() = NamedColors.GREEN
    val aqua: TurtleColor get() = NamedColors.AQUA
    val teal: TurtleColor get() = NamedColors.TEAL
    val blue: TurtleColor get() = NamedColors.BLUE
    val navy: TurtleColor get() = NamedColors.NAVY
    val fuchsia: TurtleColor get() = NamedColors.FUCHSIA
    val purple: TurtleColor get() = NamedColors.PURPLE
}

private enum class NamedColors(override val r: Int, override val g: Int, override val b: Int) : TurtleColor {
    WHITE(255, 255, 255),
    SILVER(192, 192, 192),
    GRAY(128, 128, 128),
    BLACK(0, 0, 0),
    RED(255, 0, 0),
    MAROON(128, 0, 0),
    YELLOW(255, 255, 0),
    OLIVE(128, 128, 0),
    LIME(0, 255, 0),
    GREEN(0, 128, 0),
    AQUA(0, 255, 255),
    TEAL(0, 128, 128),
    BLUE(0, 0, 255),
    NAVY(0, 0, 128),
    FUCHSIA(255, 0, 255),
    PURPLE(128, 0, 128),
    ORANGE(255, 128, 0)
}
