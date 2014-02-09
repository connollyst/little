package com.quane.little.web.view

import vaadin.scala.Label
import scala.collection.mutable.ListBuffer
import vaadin.scala.HorizontalLayout
import com.quane.little.web.controller.FunctionReferenceController

object FunctionReference {
  val Style = "l-function-ref"
}

class FunctionReference(controller: FunctionReferenceController, name: String, params: FunctionArgument*)
  extends HorizontalLayout {

  styleName = Expression.Style

  add(Label(name))
  params.foreach {
    param: FunctionArgument => add(param)
  }

}