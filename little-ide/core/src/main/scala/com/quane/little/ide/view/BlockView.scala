package com.quane.little.ide.view

import com.quane.little.ide.presenter._

trait BlockView extends View[BlockViewListener] {

  def addSetStatement(): SetPresenter[_]

  def addGetStatement(): GetPresenter[_]

  def addPrintStatement(): PrintPresenter[_]

  def addConditionalStatement(): ConditionalPresenter[_]

  def addFunctionReference(): FunctionReferencePresenter[_]

}

trait BlockViewListener extends ViewListener