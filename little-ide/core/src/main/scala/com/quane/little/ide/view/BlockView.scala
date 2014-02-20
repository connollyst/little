package com.quane.little.ide.view

import com.quane.little.ide.presenter._

trait BlockView extends View[BlockViewListener] {

  def addSetStatement(): SetStatementPresenter[_]

  def addGetStatement(): GetStatementPresenter[_]

  def addPrintStatement(): PrintStatementPresenter[_]

  def addConditionalStatement(): ConditionalPresenter[_]

  def addFunctionReference(): FunctionReferencePresenter[_]

}

trait BlockViewListener extends ViewListener {

  def requestAddSetStatement(): Unit

  def requestAddPrintStatement(): Unit

}