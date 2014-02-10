package com.quane.little.ide.view

import com.quane.little.ide.presenter.{PrintPresenter, StatementPresenter}

sealed trait StatementView[+C <: StatementPresenter] extends ExpressionView[C]

class PrintView(override var value: Option[Any]) extends Label with StatementView[PrintPresenter] {
  def this(value: String) = {
    this(Some(value))
  }

  def presenter: PrintPresenter = new PrintPresenter
}