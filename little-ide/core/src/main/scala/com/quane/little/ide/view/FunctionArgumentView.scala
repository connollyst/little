package com.quane.little.ide.view

import com.quane.little.ide.presenter._

trait FunctionArgumentView extends View[FunctionArgumentViewPresenter] {

  def setName(name: String): Unit

  def createValueStatement(): ValueView

  def createGetStatement(): GetStatementView

  def createFunctionReference(): FunctionReferenceView

}

trait FunctionArgumentViewPresenter
  extends ExpressionViewPresenter
  with PresenterAcceptsExpression
  with PresenterAcceptsFunctionReference