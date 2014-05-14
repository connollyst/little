package com.quane.little.ide.view

import com.quane.little.ide.presenter._

trait PrintStatementView
  extends StatementView[PrintStatementViewPresenter] {

  def createValueStatement(): ValueView

  def createGetStatement(): GetStatementView

  def createFunctionReference(): FunctionReferenceView

}

trait PrintStatementViewPresenter
  extends StatementViewPresenter
  with PresenterAcceptsExpression
  with PresenterAcceptsFunctionReference