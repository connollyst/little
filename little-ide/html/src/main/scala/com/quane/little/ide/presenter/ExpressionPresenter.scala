package com.quane.little.ide.presenter

import com.quane.little.language.{Scope, Expression}

trait ExpressionPresenter {

  def compile(scope: Scope): Expression

}
