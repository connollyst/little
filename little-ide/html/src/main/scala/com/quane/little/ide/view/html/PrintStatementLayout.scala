package com.quane.little.ide.view.html

import vaadin.scala.{Label, HorizontalLayout}
import com.quane.little.ide.view.PrintStatementView

class PrintStatementLayout
  extends HorizontalLayout
  with PrintStatementView {

  private val valueLabel = Label("PRINT: ???")

  add(valueLabel)

  def setValue(value: String): Unit = valueLabel.value = "PRINT: " + value

}
