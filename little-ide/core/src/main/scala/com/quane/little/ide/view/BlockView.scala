package com.quane.little.ide.view

import com.quane.little.ide.presenter._

trait BlockView extends View[BlockViewListener] {

  def createSetExpression(): SetPresenter[_]

  def createGetExpression(): GetPresenter[_]

  def createPrintStatement(): PrintPresenter[_]

  def createConditional(): ConditionalPresenter[_]

  def createFunctionReference(): FunctionReferencePresenter[_]

}

trait BlockViewListener extends ViewListener