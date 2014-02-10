package com.quane.little.ide.view

import scala.collection.mutable.ListBuffer

import com.quane.little.ide.presenter.FunctionReferencePresenter

import vaadin.scala.HorizontalLayout
import vaadin.scala.Label

object FunctionReferenceView {
  val Style = "l-function-ref"
}

class FunctionReferenceView(val presenter: FunctionReferencePresenter, name: String, args: FunctionArgumentView*)
  extends HorizontalLayout with ExpressionView[FunctionReferencePresenter] {

  presenter.name = name
  presenter.args ++= args.map {
    f: FunctionArgumentView => f.presenter
  }.to[ListBuffer]

  styleName = FunctionReferenceView.Style
  add(Label(name))
  args.foreach {
    arg: FunctionArgumentView => add(arg)
  }

}