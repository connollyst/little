package com.quane.little.ide.view

import com.quane.little.ide.presenter.ExpressionPresenter


object ExpressionView {
  val Style = "l-expression"
  val StyleButtonDelete = Style + "-btn-delete"
}

trait ExpressionView[+C <: ExpressionPresenter] extends Component {

  def presenter: C

}