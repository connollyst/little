package com.quane.little.ide.presenter


sealed trait StatementPresenter extends ExpressionPresenter {
  override def compile(scope: Scope): Statement
}

class PrintPresenter extends StatementPresenter {
  override def compile(scope: Scope): Statement = new Print(new Value("TODO"))
}