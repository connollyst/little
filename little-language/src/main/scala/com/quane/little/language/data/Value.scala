package com.quane.little.language.data

import ValueType._
import com.google.common.base.Objects
import com.quane.little.language.Expression
import scala.None

class Value(val primitive: Any)
  extends Expression
  with Ordered[Value] {

  val valueType: ValueType = {
    primitive match {
      case b: Boolean => ValueType.Boolean
      case i: Int => ValueType.Integer
      case d: Double => ValueType.Double
      case s: String => ValueType.String
      case None => ValueType.Nada
      case _ => throw new IllegalArgumentException(
        "Cannot resolve value type for " + primitive.getClass + ": " + primitive
      )
    }
  }

  def asText: String = primitive.toString

  def asBool: Boolean = {
    valueType match {
      case ValueType.String =>
        val string = primitive.toString
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
      case ValueType.Boolean =>
        primitive.asInstanceOf[Boolean]
      case ValueType.Integer =>
        primitive.asInstanceOf[Int] match {
          case 0 => false
          case 1 => true
          case _ => throw new ClassCastException(
            toString + " cannot be converted to a Bool"
          )
        }
      case ValueType.Double =>
        // TODO implement Double -> Boolean
        throw new ClassCastException(
          toString + " cannot be converted to a Bool"
        )
      case ValueType.Nada =>
        // TODO implement Nada -> Boolean
        throw new ClassCastException(
          toString + " cannot be converted to a Bool"
        )
    }
  }

  def asInt: Int = {
    valueType match {
      case ValueType.String =>
        try {
          primitive.asInstanceOf[String].toInt
        } catch {
          case e: NumberFormatException =>
            try {
              // TODO avoid newing up a Value here
              new Value(asBool).asInt
            } catch {
              case e: ClassCastException =>
                throw new ClassCastException(
                  primitive.toString + " cannot be converted to a Number"
                )
            }
        }
      case ValueType.Boolean =>
        val boolean = primitive.asInstanceOf[Boolean]
        if (boolean) {
          1
        } else {
          0
        }
      case ValueType.Integer =>
        primitive.asInstanceOf[Int]
      case ValueType.Double =>
        asDouble.toInt
      case ValueType.Nada =>
        0
    }
  }

  def asDouble: Double = {
    valueType match {
      case ValueType.String =>
        try {
          primitive.asInstanceOf[String].toDouble
        } catch {
          case e: NumberFormatException =>
            try {
              // TODO avoid newing up a Value here
              new Value(asBool).asDouble
            } catch {
              case e: ClassCastException =>
                throw new ClassCastException(
                  toString + " cannot be converted to a Double"
                )
            }
        }
      case ValueType.Boolean =>
        asInt
      case ValueType.Integer =>
        asInt
      case ValueType.Double =>
        primitive.asInstanceOf[Double]
      case ValueType.Nada =>
        0
    }
  }


  /** A value is an expression which evaluates to itself.
    *
    * @return this
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

class Nada
  extends Value(None) {

  override def asBool: Boolean = false

  override def asInt: Int = 0

  override def asText: String = "nada"

}
