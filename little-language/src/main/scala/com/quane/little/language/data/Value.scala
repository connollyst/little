package com.quane.little.language.data

import com.google.common.base.Objects
import com.quane.little.language.data.ValueType._
import com.quane.little.language.{Code, Scope}

import scala.math.BigDecimal
import scala.math.BigDecimal.RoundingMode

object Value {

  val TRUE = new TrueFalseValue(true)

  val FALSE = new TrueFalseValue(false)

  val ONE = new NumberValue(1)

  val ZERO = new NumberValue(0)

  def apply(primitive: String) = new TextValue(primitive)

  def apply(primitive: Boolean) = new TrueFalseValue(primitive)

  def apply(primitive: BigDecimal) = new NumberValue(primitive)

  def apply(primitive: Int) = new NumberValue(BigDecimal(primitive))

  def apply(primitive: Long) = new NumberValue(BigDecimal(primitive))

  def apply(primitive: Float) = new NumberValue(BigDecimal(primitive))

  def apply(primitive: Double) = new NumberValue(BigDecimal(primitive))

}

sealed trait Value extends Code with Ordered[Value] {

  val primitive: Any

  def returnType: ValueType

  def asText: TextValue

  def asBool: TrueFalseValue

  def asNumber: NumberValue

  /** A value evaluates to itself.
    *
    * @return the value itself
    */
  override def evaluate(scope: Scope): Value = this

  override def equals(other: Any): Boolean =
    other match {
      case that: Value => this.asText.toString == that.asText.toString
      case that: String => this.asText.primitive == that
      case that: Boolean => this.asBool.primitive == that
      case that: BigDecimal => this.asNumber.primitive == that
      case that: Int => this.asNumber.asInt == that
      case that: Long => this.asNumber.asDouble == that.toDouble
      case that: Float => this.asNumber.asDouble == that.toDouble
      case that: Double => this.asNumber.asDouble == that
      case _ => false
    }

  def compare(that: Value): Int = {
    // 1) If the values are the same primitive type, compare the primitives
    if (returnType == that.returnType) {
      return returnType match {
        case ValueType.Text => asText.primitive compare that.asText.primitive
        case ValueType.TrueFalse => asBool.primitive compare that.asBool.primitive
        case ValueType.Number => asNumber.primitive compare that.asNumber.primitive
        case _ => throw new IllegalAccessException("Unrecognized value type: " + toString)
      }
    }
    // 2) If the primitive types are different, see if they can be cast (eg: Int & Double, Int & Boolean?)
    if ( // Compare int to boolean (as int)
      (returnType == ValueType.Number && that.returnType == ValueType.TrueFalse)
        || (returnType == ValueType.TrueFalse && that.returnType == ValueType.Number)
    ) {
      return asNumber compareTo that.asNumber
      // TODO what other conversions are possible? boolean to text 'true'/'false'?
    }
    // 3) If the primitive types are different and no cast makes sense, convert to text and compare
    asText compareTo that.asText
  }

  def asString: String = primitive.toString

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("value", asString)
      .add("type", returnType)
      .toString

}

/** A value representing a boolean `true` or `false`.
  *
  * @param primitive the boolean value
  */
class TrueFalseValue(override val primitive: Boolean) extends Value {

  override def returnType: ValueType = ValueType.TrueFalse

  override def asBool = this

  override def asNumber =
    if (primitive) {
      Value.ONE
    } else {
      Value.ZERO
    }

  override def asText = Value(primitive.toString)

  def &&(that: TrueFalseValue) = this.primitive && that.primitive

  def ||(that: TrueFalseValue) = this.primitive || that.primitive

}

/** A value representing a number.
  *
  * @param primitive the number value
  */
class NumberValue(override val primitive: BigDecimal) extends Value {

  override def returnType: ValueType = ValueType.Number

  override def asBool =
    if (primitive == Value.ONE.primitive) {
      Value.TRUE
    } else {
      Value.FALSE
    }

  override def asNumber = this

  override def asText = new TextValue(primitive.toString())

  def asInt: Int = primitive.setScale(0, RoundingMode.HALF_UP).toInt

  def asDouble: Double = primitive.toDouble

  def <(that: NumberValue): Boolean = primitive < that.primitive

  def <(that: Int): Boolean = asInt < that

  def <(that: Double): Boolean = asDouble < that

  def >(that: NumberValue): Boolean = primitive > that.primitive

  def >(that: Int): Boolean = asInt > that

  def >(that: Double): Boolean = asDouble > that

  def ==(that: NumberValue): Boolean = primitive == that.primitive

  def ==(that: Int): Boolean = asInt == that

  def ==(that: Double): Boolean = asDouble == that

  def !=(that: NumberValue): Boolean = primitive != that.primitive

  def !=(that: Int): Boolean = asInt != that

  def !=(that: Double): Boolean = asDouble != that

  def +(that: NumberValue): NumberValue = new NumberValue(primitive + that.primitive)

  def +(that: Int): NumberValue = new NumberValue(asInt + that)

  def +(that: Double): NumberValue = new NumberValue(asDouble + that)

  def *(that: NumberValue): NumberValue = new NumberValue(primitive * that.primitive)

  def *(that: Int): NumberValue = new NumberValue(asInt * that)

  def *(that: Double): NumberValue = new NumberValue(asDouble * that)

  def -(that: NumberValue): NumberValue = new NumberValue(primitive - that.primitive)

  def -(that: Int): NumberValue = new NumberValue(asInt - that)

  def -(that: Double): NumberValue = new NumberValue(asDouble - that)

  def /(that: NumberValue): NumberValue = new NumberValue(primitive / that.primitive)

  def /(that: Int): NumberValue = new NumberValue(asInt / that)

  def /(that: Double): NumberValue = new NumberValue(asDouble / that)

  def %(that: NumberValue): NumberValue = new NumberValue(primitive % that.primitive)

  def %(that: Int): NumberValue = new NumberValue(asInt % that)

  def %(that: Double): NumberValue = new NumberValue(asDouble % that)

}

class TextValue(override val primitive: String) extends Value {

  override def returnType: ValueType = ValueType.Text

  override def asBool =
    if (primitive.equalsIgnoreCase("true") || primitive.equalsIgnoreCase("yes")) {
      Value.TRUE
    } else if (primitive.equalsIgnoreCase("false") || primitive.equalsIgnoreCase("no")) {
      Value.FALSE
    } else if (primitive.isEmpty) {
      Value.FALSE
    } else {
      throw new ClassCastException("'" + asString + "' cannot be converted to true/false")
    }

  override def asNumber =
    try {
      new NumberValue(primitive.toInt)
    } catch {
      case e: NumberFormatException =>
        try {
          asBool.asNumber
        } catch {
          case e: ClassCastException =>
            throw new ClassCastException("'" + asString + "' cannot be converted to a number")
        }
    }

  override def asText = this

}

class Nada extends Value {

  override val primitive = None

  override def returnType: ValueType = ValueType.Nothing

  override def asBool = Value.FALSE

  override def asNumber = Value.ZERO

  override def asText = new TextValue("nada")

}
