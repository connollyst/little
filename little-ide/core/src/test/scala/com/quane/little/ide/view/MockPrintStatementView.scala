package com.quane.little.ide.view

import com.quane.little.ide.presenter.{ValuePresenter, GetStatementPresenter, FunctionReferencePresenter}

/** An implementation of [[com.quane.little.ide.view.PrintStatementView]] for
  * testing purposes.
  *
  * @author Sean Connolly
  */
class MockPrintStatementView
  extends PrintStatementView
  with MockView {

  def createValueStatement(): ValuePresenter[_] = new ValuePresenter(new MockValueView)

  def createGetStatement(): GetStatementPresenter[_] = new GetStatementPresenter(new MockGetStatementView)

  def createFunctionReference(): FunctionReferencePresenter[_] = new FunctionReferencePresenter(new MockFunctionReferenceView)

}
