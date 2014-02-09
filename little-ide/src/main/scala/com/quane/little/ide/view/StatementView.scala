package com.quane.little.ide.view

import com.quane.little.ide.controller.{PrintController, StatementController}
import vaadin.scala.Label

sealed trait StatementView[+C <: StatementController] extends ExpressionView[C]

class PrintView(override var value: Option[Any]) extends Label with StatementView[PrintController] {
  def this(value: String) = {
    this(Some(value))
  }

  def controller: PrintController = new PrintController
}