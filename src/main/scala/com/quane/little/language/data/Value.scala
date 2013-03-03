package com.quane.little.language.data

import java.awt.Point
import java.util.Date

import com.quane.little.language.Expression

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

class Number(value: Int)
        extends Expression[Number]
        with Value {

    def this(value: String) {
        this(value.toInt)
    }

    def evaluate: Number = this;

    def primitive: Int = value

    def int: Int = primitive

}

class Text(value: String)
        extends Expression[Text]
        with Value {

    def evaluate: Text = this;

    def primitive: String = value

    def string: String = primitive

}
