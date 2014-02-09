package com.quane.little.web.controller

import com.quane.little.language.Expression
import com.quane.little.language.Scope

trait Controller[T <: Expression] {

  def compile(scope: Scope): T
  
}