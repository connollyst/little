package com.quane.little.ide.view

import com.quane.little.ide.presenter._

trait SetStatementView
  extends StatementView[SetStatementViewPresenter] {

  def setName(name: String): Unit

  def createValueStatement(): ValueView

  def createGetStatement(): GetterView

  def createFunctionReference(): FunctionReferenceView

}

trait SetStatementViewPresenter
  extends StatementViewPresenter
  with PresenterAcceptsExpression
  with PresenterAcceptsFunctionReference {

  def onNameChange(name: String): Unit

}