package com.quane.little.ide.view.html

import com.quane.little.ide.presenter.FunctionParameterPresenter
import com.quane.little.ide.view.html.FunctionParameterComponent._
import vaadin.scala.Label

object FunctionParameterComponent {
  private val Style = "l-function-def-param"
  private val DefaultName = "newParam"
}

class FunctionParameterComponent(val presenter: FunctionParameterPresenter, name: String)
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