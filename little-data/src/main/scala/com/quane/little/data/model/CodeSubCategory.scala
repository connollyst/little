package com.quane.little.data.model

/** Used to organize code in the UI.
  *
  * @author Sean Connolly
  */
object CodeCategory extends Enumeration {
  type CodeCategory = Value
  val Expression, Statement, Function, EventListener = Value
}

/** User to organize functions in the UI.
  *
  * @author Sean Connolly
  */
object CodeSubcategory extends Enumeration {
  type CodeSubcategory = Value
  val Basic, Sensing, Motion, Math, Misc = Value
}