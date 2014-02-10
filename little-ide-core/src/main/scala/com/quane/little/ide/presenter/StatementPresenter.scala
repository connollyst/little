package com.quane.little.ide.presenter

import com.quane.little.language.data.Value
import com.quane.little.language.{Print, Statement, Scope}

sealed trait StatementPresenter extends ExpressionPresenter {
  override def compile(scope: Scope): Statement
}

class PrintPresenter extends StatementPresenter {
  override def compile(scope: Scope): Statement = new Print(new Value("TODO"))
}