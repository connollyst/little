package com.quane.little.ide.view

import com.quane.little.ide.presenter.{FunctionReferencePresenter, GetStatementPresenter, ValuePresenter, ExpressionPresenter}


trait FunctionArgumentView extends View[FunctionArgumentViewListener] {

  def setName(name: String): Unit

  def createValueStatement(): ValuePresenter[_]

  def createGetStatement(): GetStatementPresenter[_]

  def createFunctionReference(): FunctionReferencePresenter[_]

}

trait FunctionArgumentViewListener extends ViewListener {

  def onValueChange(p: ExpressionPresenter): Unit

}
