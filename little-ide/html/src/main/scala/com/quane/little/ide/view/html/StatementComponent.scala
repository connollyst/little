package com.quane.little.ide.view.html

import com.quane.little.ide.presenter.{PrintPresenter, StatementPresenter}
import vaadin.scala.Label


sealed trait StatementComponent[+C <: StatementPresenter] extends ExpressionView[C]


class PrintComponent(override var value: Option[Any])
  extends Label with StatementComponent[PrintPresenter] {
  def this(value: String) = {
    this(Some(value))
  }

  def presenter: PrintPresenter = new PrintPresenter

}