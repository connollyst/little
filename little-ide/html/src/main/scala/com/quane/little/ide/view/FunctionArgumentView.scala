package com.quane.little.ide.view

import com.quane.little.ide.presenter.FunctionArgumentPresenter
import vaadin.scala.Label


object FunctionArgumentView {
  private val Style = "l-function-ref-arg"
  private val DefaultName = "newArg"
}

class FunctionArgumentView(val presenter: FunctionArgumentPresenter, name: String)
  extends Label {

  def this(name: String) = {
    this(new FunctionArgumentPresenter(name), name)
  }

  def this() = {
    this(FunctionArgumentView.DefaultName)
  }

  value = name
  styleName = FunctionArgumentView.Style

}