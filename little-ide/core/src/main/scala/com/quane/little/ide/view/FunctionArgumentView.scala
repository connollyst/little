package com.quane.little.ide.view

import com.quane.little.ide.presenter._

trait FunctionArgumentView extends ExpressionView[FunctionArgumentViewPresenter] {

  def setName(name: String): Unit

  def createGetExpression(): GetterView

  def createMathExpression(): MathView

  def createLogicExpression(): LogicView

  def createValueExpression(): ValueView

  def createFunctionReference(): FunctionReferenceView

}

trait FunctionArgumentViewPresenter
  extends ExpressionViewPresenter
  with PresenterAcceptsExpression