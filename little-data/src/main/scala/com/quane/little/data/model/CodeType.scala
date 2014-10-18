package com.quane.little.data.model

/** Used to organize code in the UI.
  *
  * @author Sean Connolly
  */
object CodeType extends Enumeration {
  type CodeType = Value
  // TODO this can be inferred by the class hierarchy!
  val Primitive, Function = Value
}