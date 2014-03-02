package com.quane.little.ide.view

import com.quane.little.ide.presenter.FunctionArgumentPresenter


trait FunctionReferenceView extends ExpressionView[FunctionReferenceViewPresenter] {

  def setName(name: String): Unit

  def createArgument(): FunctionArgumentPresenter[_]

}

trait FunctionReferenceViewPresenter extends ExpressionViewPresenter