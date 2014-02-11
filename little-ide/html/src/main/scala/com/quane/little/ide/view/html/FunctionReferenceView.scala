package com.quane.little.ide.view.html

import com.quane.little.ide.presenter.FunctionReferencePresenter
import vaadin.scala.{Label, HorizontalLayout}
import scala.collection.mutable.ListBuffer


object FunctionReferenceView {
  val Style = "l-expression l-function-ref"
}

class FunctionReferenceView(val presenter: FunctionReferencePresenter, name: String, args: FunctionArgumentView*)
  extends HorizontalLayout with ExpressionView[FunctionReferencePresenter] {

  // Initialize presenter
  presenter.name = name
  presenter.args ++= args.map {
    f: FunctionArgumentView => f.presenter
  }.to[ListBuffer]

  // Initialize UI
  spacing = true
  styleName = FunctionReferenceView.Style
  add(Label(name))
  args.foreach {
    arg: FunctionArgumentView => add(arg)
  }

}