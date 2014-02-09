package com.quane.little.ide.view

import com.quane.little.ide.controller.FunctionReferenceController
import vaadin.scala.Label
import vaadin.scala.HorizontalLayout

object FunctionReferenceView {
  val Style = "l-function-ref"
}

class FunctionReferenceView(val controller: FunctionReferenceController, name: String, params: FunctionArgumentView*)
  extends HorizontalLayout with ExpressionView[FunctionReferenceController] {

  styleName = FunctionReferenceView.Style

  add(Label(name))
  params.foreach {
    param: FunctionArgumentView => add(param)
  }

}