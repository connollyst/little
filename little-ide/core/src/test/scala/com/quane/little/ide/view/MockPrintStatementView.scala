package com.quane.little.ide.view

import com.quane.little.ide.presenter.{ValuePresenter, GetterPresenter, FunctionReferencePresenter}

/** An implementation of [[com.quane.little.ide.view.PrintStatementView]] for
  * testing purposes.
  *
  * @author Sean Connolly
  */
class MockPrintStatementView
  extends PrintStatementView
  with MockView {

  override def createValueStatement() = new ValuePresenter(new MockValueView)

  override def createGetStatement() = new GetterPresenter(new MockGetStatementView)

  override def createFunctionReference() = new FunctionReferencePresenter(new MockFunctionReferenceView)

}
