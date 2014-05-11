package com.quane.little.ide.view

import com.quane.little.ide.presenter._

trait SetStatementView
  extends StatementView[SetStatementViewPresenter] {

  def setName(name: String): Unit

  def createValueStatement(): ValuePresenter[_ <: ValueView]

  def createGetStatement(): GetterPresenter[_ <: GetStatementView]

  def createFunctionReference(): FunctionReferencePresenter[_ <: FunctionReferenceView]

}

trait SetStatementViewPresenter
  extends StatementViewPresenter
  with PresenterAcceptsExpression
  with PresenterAcceptsFunctionReference {

  def onNameChange(name: String): Unit

}