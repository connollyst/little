package com.quane.little.ide.view

import com.quane.little.ide.presenter._

trait BlockView extends View[BlockViewListener] {

  def addConditional(): ConditionalPresenter[_]

  def addConditional(index: Int): ConditionalPresenter[_]

  def addGetStatement(): GetStatementPresenter[_]

  def addGetStatement(index: Int): GetStatementPresenter[_]

  def addSetStatement(): SetStatementPresenter[_]

  def addSetStatement(index: Int): SetStatementPresenter[_]

  def addPrintStatement(): PrintStatementPresenter[_]

  def addPrintStatement(index: Int): PrintStatementPresenter[_]

  def addFunctionReference(): FunctionReferencePresenter[_]

  def addFunctionReference(index: Int): FunctionReferencePresenter[_]

}

trait BlockViewListener extends ViewListener {

  def requestAddConditional(index: Int): Unit

  def requestAddGetStatement(index: Int): Unit

  def requestAddSetStatement(index: Int): Unit

  def requestAddPrintStatement(index: Int): Unit

  def requestAddFunctionReference(name: String, index: Int): Unit

}