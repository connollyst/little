package com.quane.little.ide.view

import com.quane.little.ide.presenter._

trait GetStatementView
  extends ExpressionView[GetStatementViewPresenter] {

  def setName(name: String): Unit

}

trait SetStatementView
  extends ExpressionView[SetStatementViewPresenter] {

  def setName(name: String): Unit

  def createValueStatement(): ValuePresenter[_ <: ValueView]

  def createGetStatement(): GetStatementPresenter[_ <: GetStatementView]

  def createFunctionReference(): FunctionReferencePresenter[_ <: FunctionReferenceView]

}

trait PrintStatementView
  extends ExpressionView[PrintStatementViewPresenter] {

  def createValueStatement(): ValuePresenter[_ <: ValueView]

  def createGetStatement(): GetStatementPresenter[_ <: GetStatementView]

  def createFunctionReference(): FunctionReferencePresenter[_ <: FunctionReferenceView]

}

trait GetStatementViewPresenter
  extends ExpressionViewPresenter {

  def onNameChange(name: String): Unit

}

trait SetStatementViewPresenter
  extends ExpressionViewPresenter
  with PresenterAcceptsGet
  with PresenterAcceptsFunctionReference {

  def onNameChange(name: String): Unit

}

trait PrintStatementViewPresenter
  extends ExpressionViewPresenter
  with PresenterAcceptsGet
  with PresenterAcceptsFunctionReference