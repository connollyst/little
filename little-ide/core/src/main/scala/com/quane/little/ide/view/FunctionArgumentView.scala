package com.quane.little.ide.view

import com.quane.little.ide.presenter._

trait FunctionArgumentView extends CodeView[FunctionArgumentViewPresenter] {

  def setName(name: String): Unit

  def createCodeMenuView(): CodeMenuView

  def createGetterView(): GetterView

  def createMathView(): MathView

  def createLogicView(): LogicView

  def createValueView(): ValueView

  def createFunctionReferenceView(): FunctionReferenceView

}

trait FunctionArgumentViewPresenter extends CodeViewPresenter with PresenterAcceptsCode