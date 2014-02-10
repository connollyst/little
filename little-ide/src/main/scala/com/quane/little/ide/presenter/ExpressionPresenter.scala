package com.quane.little.ide.presenter

import com.quane.little.language.{Expression, Scope}

trait ExpressionPresenter {

  def compile(scope: Scope): Expression

}
