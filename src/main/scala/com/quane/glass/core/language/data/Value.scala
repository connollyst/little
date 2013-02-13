package com.quane.glass.core.language.data

import java.awt.Point
import java.util.Date

import com.quane.glass.core.language.Expression

/** Values are the primitive data types exposed to the user. A Value is an
  * Expression in an of itself, a Value evaluates to itself.
  *
  * @author Sean Connolly
  */
sealed trait Value {
    
    def primitive: Any
    
}

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

    def primitive: Point = value
    
    def point: Point = primitive
    
}

class Number(value: Int)
        extends Expression[Number]
        with Value {

    def this(value: String) = {
        this(value.toInt)
    }

    def evaluate: Number = this;

    def primitive: Int = value
    
    def int: Int = primitive
    
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

    def primitive: Int = value
    
    def degrees: Int = primitive

}

class Text(value: String)
        extends Expression[Text]
        with Value {

    def evaluate: Text = this;

    def primitive: String = value
    
    def string: String = primitive

}

class Time(value: Date)
        extends Expression[Time]
        with Value {

    def evaluate: Time = this;

    def primitive: Date = value
    
    def date: Date = primitive

}
