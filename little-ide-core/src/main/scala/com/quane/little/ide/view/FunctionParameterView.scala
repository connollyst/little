package com.quane.little.ide.view

import com.quane.little.ide.view.FunctionParameterView._
import com.quane.little.ide.presenter.FunctionParameterPresenter
import vaadin.scala.Label

object FunctionParameterView {
  private val Style = "l-function-def-param"
  private val DefaultName = "newParam"
}

class FunctionParameterView(val presenter: FunctionParameterPresenter, name: String)
  extends Label {

  def this(name: String) = {
    this(new FunctionParameterPresenter(name), name)
  }

  def this() = {
    this(DefaultName)
  }

  // Initialize Presenter
  presenter.name = name

  // Initialize UI
  value = name
  styleName = Style

}