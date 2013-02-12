package com.quane.glass.core.language.data

import java.awt.Point
import java.util.Date

import com.quane.glass.core.language.Expression

/** Values are the primitive data types exposed to the user. A Value is an
  * Expression in an of itself, a Value evaluates to itself.
  *
  * @author Sean Connolly
  */
sealed trait Value

class Bool(value: Boolean)
        extends Expression[Bool]
        with Value {

    def evaluate: Bool = this;

    def primitive: Boolean = value

}

// TODO replace java.awt.Point
class Location(value: Point)
        extends Expression[Location]
        with Value {

    def evaluate: Location = this;

    def point: Point = value

}

class Number(value: Int)
        extends Expression[Number]
        with Value {

    def this(value: String) = {
        this(value.toInt)
    }

    def evaluate: Number = this;

    def int: Int = value

}

class Direction(value: Int)
        extends Expression[Direction]
        with Value {

    def this(value: String) = {
        this(value.toInt)
    }
    
    def this(value: Number) = {
        this(value.int);
    }

    def evaluate: Direction = this;

    def degrees: Int = value

}

class Text(value: String)
        extends Expression[Text]
        with Value {

    def evaluate: Text = this;

    def string: String = value

}

class Time(value: Date)
        extends Expression[Time]
        with Value {

    def evaluate: Time = this;

    def date: Date = value

}
