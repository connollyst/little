package com.quane.glass.core.language.data

import com.quane.glass.core.language.Expression
import java.awt.Point
import java.util.Date

sealed trait Value[+T]
    extends Expression[T]

class Bool(value: Boolean)
        extends Value[Boolean] {
    def evaluate: Boolean = value
}

class Location(value: Point)
        extends Value[Point] { // TODO replace java.awt.Point
    def evaluate: Point = value
}

class Number(value: Int)
        extends Value[Int] {

    def this(value: String) = {
        this(value.toInt)
    }

    def evaluate: Int = value
}

class Direction(value: Int)
        extends Value[Int] {

    def this(value: String) = {
        this(value.toInt)
    }

    def evaluate: Int = value
}

class Text(value: String)
        extends Value[String] {
    def evaluate: String = value
}

class Time(value: Date)
        extends Value[Date] {
    def evaluate: Date = value
}
