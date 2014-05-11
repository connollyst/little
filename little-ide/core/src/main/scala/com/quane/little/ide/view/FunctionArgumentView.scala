package com.quane.little.ide.view

import com.quane.little.ide.presenter._

trait FunctionArgumentView extends View[FunctionArgumentViewPresenter] {

  def setName(name: String): Unit

  def createValueStatement(): ValuePresenter[_ <: ValueView]

  def createGetStatement(): GetStatementPresenter[_ <: GetStatementView]

  def createFunctionReference(): FunctionReferencePresenter[_ <: FunctionReferenceView]

}

trait FunctionArgumentViewPresenter
  extends ViewPresenter
  with PresenterAcceptsGet
  with PresenterAcceptsFunctionReference {

  def requestAddTextLiteral(): Unit

}
