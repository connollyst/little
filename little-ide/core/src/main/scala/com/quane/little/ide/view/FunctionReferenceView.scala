package com.quane.little.ide.view

trait FunctionReferenceView
  extends ExpressionView[FunctionReferenceViewPresenter] {

  def setName(name: String): Unit

  def createArgument(): FunctionArgumentView

  def clearArguments(): Unit

}

trait FunctionReferenceViewPresenter
  extends ExpressionViewPresenter