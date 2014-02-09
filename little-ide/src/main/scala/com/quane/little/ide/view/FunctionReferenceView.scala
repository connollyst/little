package com.quane.little.ide.view

import scala.collection.mutable.ListBuffer

import com.quane.little.ide.controller.FunctionReferenceController

import vaadin.scala.HorizontalLayout
import vaadin.scala.Label

object FunctionReferenceView {
  val Style = "l-function-ref"
}

class FunctionReferenceView(val controller: FunctionReferenceController, name: String, params: FunctionParameterView*)
  extends HorizontalLayout with ExpressionView[FunctionReferenceController] {

  controller.name = name
  controller.params = params.map { f: FunctionParameterView => f.controller }.to[ListBuffer]

  styleName = FunctionReferenceView.Style
  add(Label(name))
  params.foreach {
    param: FunctionParameterView => add(param)
  }

}