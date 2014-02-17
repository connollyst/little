package com.quane.little.ide.view.html

import vaadin.scala.{HorizontalLayout, Label}
import com.quane.little.ide.view.FunctionArgumentView


object FunctionArgumentComponent {
  private val Style = "l-function-ref-arg"
}

class FunctionArgumentComponent
  extends HorizontalLayout with FunctionArgumentView {

  private val nameLabel = new Label()
  private val valueLabel = new Label()

  styleName = FunctionArgumentComponent.Style
  add(nameLabel)
  add(Label("="))
  add(valueLabel)

  override def setName(name: String): Unit = nameLabel.value = name

  override def setValue(value: String): Unit = valueLabel.value = value

}