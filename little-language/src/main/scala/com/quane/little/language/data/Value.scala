package com.quane.little.language.data

import ValueType._
import com.google.common.base.Objects
import com.quane.little.language.Expression
import scala.None

object Value {

  def apply(primitive: Any): Value =
    primitive match {
      case b: Boolean => new Bool(b)
      case i: Int => new Number(i)
      case d: Double => new NumberDecimal(d)
      case s: String => new Text(s)
      case None => new Nada
      case _ => throw new IllegalArgumentException(
        "Cannot resolve value type for " + primitive.getClass + ": " + primitive
      )
    }

}

sealed trait Value
  extends Expression
  with Ordered[Value] {

  private[language] val primitive: Any

  private[language] def valueType: ValueType

  def asText: String

  def asBool: Boolean

  def asInt: Int

  def asDouble: Double


  /** A value evaluates to itself.
    *
    * @return the value itself
    */
  def evaluate: Value = this

  override def equals(other: Any): Boolean = {
    other match {
      case that: Value => this.asText == that.asText
      case that: String => this.asText == that
      case that: Boolean => this.asBool == that
      case that: Int => this.asInt == that
      case that: Double => this.asDouble == that
      case _ => false
    }
  }

  def compare(that: Value): Int = {
    // TODO move to individual implementation classes
    // 1) If the values are the same primitive type, compare the primitives
    if (valueType == that.valueType) {
      return valueType match {
        case ValueType.String => asText compareTo that.asText
        case ValueType.Boolean => asBool compareTo that.asBool
        case ValueType.Integer => asInt compareTo that.asInt
        case ValueType.Double => asDouble compareTo that.asDouble
        case _ => throw new IllegalAccessException("Unrecognized value type: " + toString)
      }
    }
    // 2) If the primitive types are different, see if they can be cast (eg: Int & Double, Int & Boolean?)
    if ( // Compare int to double as int
      (valueType == ValueType.Integer && that.valueType == ValueType.Double)
        || (valueType == ValueType.Double && that.valueType == ValueType.Integer)
        // Compare int to boolean as int
        || (valueType == ValueType.Integer && that.valueType == ValueType.Boolean)
        || (valueType == ValueType.Boolean && that.valueType == ValueType.Integer)
        // Compare double to boolean as int
        || (valueType == ValueType.Double && that.valueType == ValueType.Boolean)
        || (valueType == ValueType.Boolean && that.valueType == ValueType.Double)
    ) {
      return asInt compareTo that.asInt
      // TODO what other conversions are possible? boolean to 'true'/'false'?
    }
    // 3) If the primitive types are different and no cast makes sense, convert to text and compare
    asText compareTo that.asText
  }

  override def toString: String = {
    Objects.toStringHelper(getClass)
      .add("value", asText)
      .add("type", valueType)
      .toString
  }

}

class Bool(bool: Boolean)
  extends Value {

  override val primitive = bool

  override def valueType: ValueType = ValueType.Boolean

  override def asBool: Boolean = bool

  override def asInt: Int = {
    val boolean = bool.asInstanceOf[Boolean]
    if (boolean) {
      1
    } else {
      0
    }
  }

  override def asDouble: Double = asInt

  override def asText: String = bool.toString

}

class Number(int: Int)
  extends Value {

  override val primitive = int

  override def valueType: ValueType = ValueType.Integer

  override def asBool: Boolean =
    int match {
      case 1 => true
      case _ => false
    }

  override def asInt: Int = int

  override def asDouble: Double = int

  override def asText: String = int.toString

}

class NumberDecimal(double: Double)
  extends Value {

  override val primitive = double

  override def valueType: ValueType = ValueType.Double

  override def asBool: Boolean =
    double match {
      case 1 => true
      case _ => false
    }

  override def asInt: Int = double.toInt

  override def asDouble: Double = double

  override def asText: String = double.toString

}

class Text(string: String)
  extends Value {

  override val primitive = string

  override def valueType: ValueType = ValueType.String

  override def asBool: Boolean = {
    if (string equalsIgnoreCase "true") {
      true
    } else if (string equalsIgnoreCase "false") {
      false
    } else if (string.isEmpty) {
      false
    } else {
      throw new ClassCastException(
        toString + " cannot be converted to a Bool"
      )
    }
  }

  override def asInt: Int = {
    try {
      string.toInt
    } catch {
      case e: NumberFormatException =>
        try {
          // TODO avoid newing up a Value here
          Value(asBool).asInt
        } catch {
          case e: ClassCastException =>
            throw new ClassCastException(
              toString + " cannot be converted to a Number"
            )
        }
    }
  }

  override def asDouble: Double =
    try {
      string.toDouble
    } catch {
      case e: NumberFormatException =>
        try {
          // TODO avoid newing up a Value here
          Value(asBool).asDouble
        } catch {
          case e: ClassCastException =>
            throw new ClassCastException(
              toString + " cannot be converted to a Double"
            )
        }
    }

  override def asText: String = string

}

class Nada
  extends Value {

  override val primitive = None

  override def valueType: ValueType = ValueType.Nada

  override def asBool: Boolean = false

  override def asInt: Int = 0

  override def asDouble: Double = 0.0d

  override def asText: String = "nada"

}
