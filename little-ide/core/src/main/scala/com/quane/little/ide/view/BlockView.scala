package com.quane.little.ide.view

import com.quane.little.ide.presenter._

trait BlockView extends ExpressionView[BlockViewPresenter] {

  def addMath(): MathView

  def addMath(index: Int): MathView

  def addConditional(): ConditionalView

  def addConditional(index: Int): ConditionalView

  def addGetStatement(): GetterView

  def addGetStatement(index: Int): GetterView

  def addSetStatement(): SetterView

  def addSetStatement(index: Int): SetterView

  def addPrintStatement(): PrinterView

  def addPrintStatement(index: Int): PrinterView

  def addFunctionReference(): FunctionReferenceView

  def addFunctionReference(index: Int): FunctionReferenceView

}

trait BlockViewPresenter
  extends ExpressionViewPresenter
  with PresenterAcceptsExpression
  with PresenterAcceptsStatement
  with PresenterAcceptsFunctionReference