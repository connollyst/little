package com.quane.little.ide.view.html

import vaadin.scala.{Label, HorizontalLayout}
import com.quane.little.ide.view.SetStatementView

class SetStatementLayout
  extends HorizontalLayout
  with SetStatementView {

  private val nameLabel = new Label
  private val valueLabel = new Label

  add(nameLabel)
  add(Label("="))
  add(valueLabel)

  override def setName(name: String): Unit = nameLabel.value = name

  override def setValue(value: String): Unit = valueLabel.value = value

}