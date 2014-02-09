package com.quane.little.ide.view

import com.quane.little.ide.controller.ExpressionController
import vaadin.scala.Component


object ExpressionView {
  val Style = "l-expression"
  val StyleButtonDelete = Style + "-btn-delete"
}

trait ExpressionView[+C <: ExpressionController] extends Component {

  def controller: C

}