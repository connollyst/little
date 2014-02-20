package com.quane.little.ide.view

import com.quane.little.ide.presenter._

trait BlockView extends View[BlockViewListener] {

  def addConditional(): ConditionalPresenter[_]

  def addGetStatement(): GetStatementPresenter[_]

  def addSetStatement(): SetStatementPresenter[_]

  def addPrintStatement(): PrintStatementPresenter[_]

  def addFunctionReference(): FunctionReferencePresenter[_]

}

trait BlockViewListener extends ViewListener {

  def requestAddConditional(): Unit

  def requestAddGetStatement(): Unit

  def requestAddSetStatement(): Unit

  def requestAddPrintStatement(): Unit

  def requestAddFunctionReference(name: String): Unit

}