package com.quane.little.ide.view.html

import vaadin.scala.{Label, HorizontalLayout}
import com.quane.little.ide.view.FunctionReferenceView
import com.quane.little.ide.presenter.FunctionArgumentPresenter


object FunctionReferenceComponent {
  val Style = "l-expression l-function-ref"
}

class FunctionReferenceComponent
  extends HorizontalLayout with FunctionReferenceView {

  private val nameLabel = new Label

  add(nameLabel)
  spacing = true
  styleName = FunctionReferenceComponent.Style

  override def setName(name: String): Unit = nameLabel.value = name

  override def createArgument: FunctionArgumentPresenter[_] = {
    val arg = new FunctionArgumentComponent
    add(arg)
    new FunctionArgumentPresenter(arg)
  }

}