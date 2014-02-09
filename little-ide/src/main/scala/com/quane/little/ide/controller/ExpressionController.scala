package com.quane.little.ide.controller

import com.quane.little.language.{Expression, Scope}

trait ExpressionController {

  def compile(scope: Scope): Expression

}
