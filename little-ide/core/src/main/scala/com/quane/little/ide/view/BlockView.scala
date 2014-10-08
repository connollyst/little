package com.quane.little.ide.view

import com.quane.little.ide.presenter._

trait BlockView extends ExpressionView[BlockViewPresenter] {

  def addMathStep(index: Int): MathView

  def addLogicStep(index: Int): LogicView

  def addGetStep(index: Int): GetterView

  def addSetStep(index: Int): SetterView

  def addPrintStep(index: Int): PrinterView

  def addConditionalStep(index: Int): ConditionalView

  def addFunctionStep(index: Int): FunctionReferenceView

  def addCodeMenu(index: Int): CodeMenuView

}

trait BlockViewPresenter extends ExpressionViewPresenter with PresenterAcceptsCode