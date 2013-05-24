package com.quane.little.language.data

class Value(val primitive: Any) {

  private val primitiveType: ValueType = {
    primitive match {
      case b: Boolean => BooleanValueType
      case i: Int => IntValueType
      case s: String => StringValueType
    }
  }

  def asBool: Boolean = {
    primitiveType match {
      case BooleanValueType =>
        primitive.asInstanceOf[Boolean]
      case IntValueType =>
        primitive.asInstanceOf[Int] match {
          case 0 => false
          case 1 => true
          case _ => throw new ClassCastException(
            primitive.toString + " cannot be converted to a Bool"
          )
        }
      case StringValueType =>
        val string = primitive.toString
        if (string equalsIgnoreCase "true") {
          true
        } else if (string equalsIgnoreCase "false") {
          false
        } else {
          throw new ClassCastException(
            primitive.toString + " cannot be converted to a Bool"
          )
        }
    }
  }

  def asNumber: Int = {
    primitiveType match {
      case BooleanValueType =>
        val boolean = primitive.asInstanceOf[Boolean]
        if (boolean) {
          1
        } else {
          0
        }
      case IntValueType =>
        primitive.asInstanceOf[Int]
      case StringValueType =>
        try {
          primitive.asInstanceOf[String].toInt
        } catch {
          case e: ClassCastException =>
            throw new ClassCastException(
              primitive.toString + " cannot be converted to a Number"
            )
        }
    }
  }

  def asText: String = primitive.toString

}

sealed trait ValueType

object BooleanValueType extends ValueType

object IntValueType extends ValueType

object StringValueType extends ValueType