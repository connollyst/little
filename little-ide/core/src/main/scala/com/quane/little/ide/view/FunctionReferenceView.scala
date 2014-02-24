package com.quane.little.ide.view

import com.quane.little.ide.presenter.FunctionArgumentPresenter


trait FunctionReferenceView extends ExpressionView[FunctionReferenceViewListener] {

  def setName(name: String): Unit

  def createArgument(): FunctionArgumentPresenter[_]

}

trait FunctionReferenceViewListener extends ExpressionViewListener