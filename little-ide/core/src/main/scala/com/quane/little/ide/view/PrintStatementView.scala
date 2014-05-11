package com.quane.little.ide.view

import com.quane.little.ide.presenter._

trait PrintStatementView
  extends StatementView[PrintStatementViewPresenter] {

  def createValueStatement(): ValuePresenter[_ <: ValueView]

  def createGetStatement(): GetterPresenter[_ <: GetStatementView]

  def createFunctionReference(): FunctionReferencePresenter[_ <: FunctionReferenceView]

}

trait PrintStatementViewPresenter
  extends StatementViewPresenter
  with PresenterAcceptsExpression
  with PresenterAcceptsFunctionReference