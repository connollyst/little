package com.quane.little.ide.view.html

import com.quane.little.ide.presenter.ExpressionPresenter
import vaadin.scala.Component


object ExpressionView {
  val Style = "l-expression"
  val StyleButtonDelete = Style + "-btn-delete"
}

trait ExpressionView[+C <: ExpressionPresenter] extends Component {

  def presenter: C

}