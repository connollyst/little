package com.quane.little.ide.view.html

import com.quane.little.ide.presenter.FunctionReferencePresenter
import vaadin.scala.{Label, HorizontalLayout}
import scala.collection.mutable.ListBuffer


object FunctionReferenceComponent {
  val Style = "l-expression l-function-ref"
}

class FunctionReferenceComponent(val presenter: FunctionReferencePresenter, name: String, args: FunctionArgumentComponent*)
  extends HorizontalLayout with ExpressionView[FunctionReferencePresenter] {

  // Initialize presenter
  presenter.name = name
  presenter.args ++= args.map {
    f: FunctionArgumentComponent => f.presenter
  }.to[ListBuffer]

  // Initialize UI
  spacing = true
  styleName = FunctionReferenceComponent.Style
  add(Label(name))
  args.foreach {
    arg: FunctionArgumentComponent => add(arg)
  }

}