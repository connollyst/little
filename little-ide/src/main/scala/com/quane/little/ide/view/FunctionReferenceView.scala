package com.quane.little.ide.view

import scala.collection.mutable.ListBuffer

import com.quane.little.ide.controller.FunctionReferenceController

import vaadin.scala.HorizontalLayout
import vaadin.scala.Label

object FunctionReferenceView {
  val Style = "l-function-ref"
}

class FunctionReferenceView(val controller: FunctionReferenceController, name: String, args: FunctionArgumentView*)
  extends HorizontalLayout with ExpressionView[FunctionReferenceController] {

  controller.name = name
  controller.args = args.map {
    f: FunctionArgumentView => f.controller
  }.to[ListBuffer]

  styleName = FunctionReferenceView.Style
  add(Label(name))
  args.foreach {
    arg: FunctionArgumentView => add(arg)
  }

}