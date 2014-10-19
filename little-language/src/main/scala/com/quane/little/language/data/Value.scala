package com.quane.little.language.data

import com.google.common.base.Objects
import com.quane.little.language.data.ValueType._
import com.quane.little.language.{Code, Scope}

object Value {

  def apply(primitive: Any): Value =
    primitive match {
      case b: Boolean => new Bool(b)
      case i: Int => new NumberSimple(i)
      case d: Double => new NumberDecimal(d)
      case s: String => new Text(s)
      case None => new Nada
      case _ => throw new IllegalArgumentException(
        "Cannot resolve value type for " + primitive.getClass + ": " + primitive
      )
    }

}

sealed trait Value extends Code with Ordered[Value] {

  val primitive: Any

  def returnType: ValueType

  def asText: String

  def asBool: Boolean

  def asInt: Int

  def asDouble: Double


  /** A value evaluates to itself.
    *
    * @return the value itself
    */
  override def evaluate(scope: Scope): Value = this

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
    if (returnType == that.returnType) {
      return returnType match {
        case ValueType.Text => asText compareTo that.asText
        case ValueType.TrueFalse => asBool compareTo that.asBool
        case ValueType.Number => asInt compareTo that.asInt
        case ValueType.Double => asDouble compareTo that.asDouble
        case _ => throw new IllegalAccessException("Unrecognized value type: " + toString)
      }
    }
    // 2) If the primitive types are different, see if they can be cast (eg: Int & Double, Int & Boolean?)
    if ( // Compare int to double as int
      (returnType == ValueType.Number && that.returnType == ValueType.Double)
        || (returnType == ValueType.Double && that.returnType == ValueType.Number)
        // Compare int to boolean as int
        || (returnType == ValueType.Number && that.returnType == ValueType.TrueFalse)
        || (returnType == ValueType.TrueFalse && that.returnType == ValueType.Number)
        // Compare double to boolean as int
        || (returnType == ValueType.Double && that.returnType == ValueType.TrueFalse)
        || (returnType == ValueType.TrueFalse && that.returnType == ValueType.Double)
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
      .add("type", returnType)
      .toString
  }

}

class Bool(override val primitive: Boolean) extends Value {

  override def returnType: ValueType = ValueType.TrueFalse

  override def asBool: Boolean = primitive

  override def asInt: Int = {
    val boolean = primitive.asInstanceOf[Boolean]
    if (boolean) {
      1
    } else {
      0
    }
  }

  override def asDouble: Double = asInt

  override def asText: String = primitive.toString

}

class NumberSimple(override val primitive: Int)
  extends Value {


  override def returnType: ValueType = ValueType.Number

  override def asBool: Boolean =
    primitive match {
      case 1 => true
      case _ => false
    }

  override def asInt: Int = primitive

  override def asDouble: Double = primitive

  override def asText: String = primitive.toString

}

class NumberDecimal(override val primitive: Double) extends Value {


  override def returnType: ValueType = ValueType.Double

  override def asBool: Boolean =
    primitive match {
      case 1 => true
      case _ => false
    }

  override def asInt: Int = primitive.toInt

  override def asDouble: Double = primitive

  override def asText: String = primitive.toString

}

class Text(override val primitive: String) extends Value {

  override def returnType: ValueType = ValueType.Text

  override def asBool: Boolean = {
    if (primitive equalsIgnoreCase "true") {
      true
    } else if (primitive equalsIgnoreCase "false") {
      false
    } else if (primitive.isEmpty) {
      false
    } else {
      throw new ClassCastException(
        toString + " cannot be converted to a Bool"
      )
    }
  }

  override def asInt: Int = {
    try {
      primitive.toInt
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
      primitive.toDouble
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

  override def asText: String = primitive

}

class Nada extends Value {

  override val primitive = None

  override def returnType: ValueType = ValueType.Nothing

  override def asBool: Boolean = false

  override def asInt: Int = 0

  override def asDouble: Double = 0.0d

  override def asText: String = "nada"

}
