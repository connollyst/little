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

    def asBool: Bool

    def asNumber: Number

    def asText: Text
}

class Bool(value: Boolean)
        extends Expression[Bool]
        with Value {

    def evaluate: Bool = this

    def primitive: Boolean = value

    def asBool: Bool = this

    def asNumber: Number = {
        if (value) {
            new Number(1)
        } else {
            new Number(0)
        }
    }

    def asText: Text = {
        if (value) {
            new Text("true")
        } else {
            new Text("false")
        }
    }
}

class Number(value: Int)
        extends Expression[Number]
        with Value {

    def this(value: String) {
        this(value.toInt)
    }

    def evaluate: Number = this

    def primitive: Int = value

    def int: Int = primitive

    def asBool: Bool = {
        if (int == 1) {
            new True
        } else if (int == 0) {
            new False
        } else {
            throw new ClassCastException("Number " + int + " cannot be converted to a Bool")
        }
    }

    def asNumber: Number = this

    def asText: Text = {
        new Text(value toString)
    }
}

class Text(value: String)
        extends Expression[Text]
        with Value {

    def evaluate: Text = this

    def primitive: String = value

    def string: String = primitive

    def asBool: Bool = {
        if (string equalsIgnoreCase "true") {
            new True
        } else if (string equalsIgnoreCase "false") {
            new False
        } else {
            throw new ClassCastException("Text '" + string + "' cannot be converted to a Bool")
        }
    }

    def asNumber: Number = {
        try {
            new Number(value toInt)
        } catch {
            case e: ClassCastException =>
                throw new ClassCastException("Text '" + string + "' cannot be converted to a Number")
        }
    }

    def asText: Text = this
}
