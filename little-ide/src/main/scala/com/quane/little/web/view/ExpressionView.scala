package com.quane.little.web.view

import com.quane.little.web.controller.ExpressionController
import vaadin.scala.Component

trait ExpressionView[+C <: ExpressionController] extends Component {

  def controller: C

}