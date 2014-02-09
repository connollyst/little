package com.quane.little.ide.controller

import com.quane.little.language.data.Value
import com.quane.little.language.{Print, Statement, Scope}

sealed trait StatementController extends ExpressionController {
  override def compile(scope: Scope): Statement
}

class PrintController extends StatementController {
  override def compile(scope: Scope): Statement = new Print(new Value("TODO"))
}