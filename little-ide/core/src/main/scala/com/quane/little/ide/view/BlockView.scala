package com.quane.little.ide.view

import com.quane.little.ide.presenter._

trait BlockView extends View[BlockViewPresenter] {

  def addConditional(): ConditionalPresenter[_]

  def addConditional(index: Int): ConditionalPresenter[_]

  def addGetStatement(): GetterPresenter[_]

  def addGetStatement(index: Int): GetterPresenter[_]

  def addSetStatement(): SetStatementPresenter[_]

  def addSetStatement(index: Int): SetStatementPresenter[_]

  def addPrintStatement(): PrintStatementPresenter[_]

  def addPrintStatement(index: Int): PrintStatementPresenter[_]

  def addFunctionReference(): FunctionReferencePresenter[_]

  def addFunctionReference(index: Int): FunctionReferencePresenter[_]

}

trait BlockViewPresenter
  extends ExpressionViewPresenter
  with PresenterAcceptsExpression
  with PresenterAcceptsStatement
  with PresenterAcceptsFunctionReference