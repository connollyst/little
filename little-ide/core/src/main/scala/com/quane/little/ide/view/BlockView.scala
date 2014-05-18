package com.quane.little.ide.view

import com.quane.little.ide.presenter._

trait BlockView extends ExpressionView[BlockViewPresenter] {

  def addMathStep(): MathView

  def addMathStep(index: Int): MathView

  def addLogicStep(): LogicView

  def addLogicStep(index: Int): LogicView

  def addGetStep(): GetterView

  def addGetStep(index: Int): GetterView

  def addSetStep(): SetterView

  def addSetStep(index: Int): SetterView

  def addPrintStep(): PrinterView

  def addPrintStep(index: Int): PrinterView

  def addConditionalStep(): ConditionalView

  def addConditionalStep(index: Int): ConditionalView

  def addFunctionStep(): FunctionReferenceView

  def addFunctionStep(index: Int): FunctionReferenceView

}

trait BlockViewPresenter
  extends ExpressionViewPresenter
  with PresenterAcceptsExpression
  with PresenterAcceptsStatement
  with PresenterAcceptsFunctionReference