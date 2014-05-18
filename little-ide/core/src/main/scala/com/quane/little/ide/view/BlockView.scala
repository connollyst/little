package com.quane.little.ide.view

import com.quane.little.ide.presenter._

trait BlockView extends ExpressionView[BlockViewPresenter] {

  def addMath(): MathView

  def addMathStep(index: Int): MathView

  def addConditional(): ConditionalView

  def addConditionalStep(index: Int): ConditionalView

  def addLogicalOperation(): LogicView

  def addLogicStep(index: Int): LogicView

  def addGetStatement(): GetterView

  def addGetStep(index: Int): GetterView

  def addSetStatement(): SetterView

  def addSetStep(index: Int): SetterView

  def addPrintStatement(): PrinterView

  def addPrintStep(index: Int): PrinterView

  def addFunctionReference(): FunctionReferenceView

  def addFunctionStep(index: Int): FunctionReferenceView

}

trait BlockViewPresenter
  extends ExpressionViewPresenter
  with PresenterAcceptsExpression
  with PresenterAcceptsStatement
  with PresenterAcceptsFunctionReference