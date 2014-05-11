package com.quane.little.ide.view

import com.quane.little.ide.presenter._

trait FunctionArgumentView extends View[FunctionArgumentViewPresenter] {

  def setName(name: String): Unit

  def createValueStatement(): ValuePresenter[_ <: ValueView]

  def createGetStatement(): GetterPresenter[_ <: GetStatementView]

  def createFunctionReference(): FunctionReferencePresenter[_ <: FunctionReferenceView]

}

trait FunctionArgumentViewPresenter
  extends ExpressionViewPresenter
  with PresenterAcceptsExpression
  with PresenterAcceptsFunctionReference {

  def requestAddTextLiteral(): Unit

}
