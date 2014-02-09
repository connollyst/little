package com.quane.little.ide.view

import com.quane.little.web.view.ExpressionView
import vaadin.scala.Label
import vaadin.scala.HorizontalLayout
import com.quane.little.web.controller.FunctionReferenceController

object FunctionReference {
  val Style = "l-function-ref"
}

class FunctionReference(val controller: FunctionReferenceController, name: String, params: FunctionArgument*)
  extends HorizontalLayout with ExpressionView[FunctionReferenceController] {

  styleName = Expression.Style

  add(Label(name))
  params.foreach {
    param: FunctionArgument => add(param)
  }

}