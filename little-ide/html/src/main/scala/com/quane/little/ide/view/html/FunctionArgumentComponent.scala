package com.quane.little.ide.view.html

import com.quane.little.ide.presenter.FunctionArgumentPresenter
import vaadin.scala.Label


object FunctionArgumentComponent {
  private val Style = "l-function-ref-arg"
  private val DefaultName = "newArg"
}

class FunctionArgumentComponent(val presenter: FunctionArgumentPresenter, name: String)
  extends Label {

  def this(name: String) = {
    this(new FunctionArgumentPresenter(name), name)
  }

  def this() = {
    this(FunctionArgumentComponent.DefaultName)
  }

  value = name
  styleName = FunctionArgumentComponent.Style

}