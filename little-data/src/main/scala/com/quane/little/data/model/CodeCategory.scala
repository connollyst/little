package com.quane.little.data.model

/** Used to organize functions in the UI.
  *
  * @author Sean Connolly
  */
object CodeCategory extends Enumeration {
  type CodeCategory = Value
  val Basic, Sensing, Motion, Math, Misc = Value
}