package com.quane.little.ide.view.html

import vaadin.scala.{Label, HorizontalLayout}
import com.quane.little.ide.view.GetStatementView

class GetStatementLayout
  extends HorizontalLayout
  with GetStatementView {

  private val nameLabel = Label("???")

  add(nameLabel)

  def setName(name: String): Unit = nameLabel.value = name

}
