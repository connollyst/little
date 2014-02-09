package com.quane.little.web.view

import com.quane.little.web.controller.ExpressionController

trait ExpressionView[+C <: ExpressionController] {

  def controller: C

}