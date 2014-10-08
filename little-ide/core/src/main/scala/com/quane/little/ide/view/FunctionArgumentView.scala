package com.quane.little.ide.view

import com.quane.little.ide.presenter._

trait FunctionArgumentView extends CodeView[FunctionArgumentViewPresenter] {

  def setName(name: String): Unit

  def createCodeMenu(): CodeMenuView

  def createGetterExpression(): GetterView

  def createMathExpression(): MathView

  def createLogicExpression(): LogicView

  def createValueExpression(): ValueView

  def createFunctionReference(): FunctionReferenceView

}

trait FunctionArgumentViewPresenter extends CodeViewPresenter with PresenterAcceptsCode