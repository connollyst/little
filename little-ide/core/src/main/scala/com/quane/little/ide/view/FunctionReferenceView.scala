package com.quane.little.ide.view

trait FunctionReferenceView extends CodeView[FunctionReferenceViewPresenter] {

  def setName(name: String): Unit

  def createArgument(): FunctionArgumentView

  def clearArguments(): Unit

}

trait FunctionReferenceViewPresenter extends CodeViewPresenter