package com.quane.little.ide.presenter


trait ExpressionPresenter {

  def compile(scope: Scope): Expression

}
