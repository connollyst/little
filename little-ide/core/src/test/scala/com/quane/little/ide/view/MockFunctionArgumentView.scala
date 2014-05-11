package com.quane.little.ide.view

import com.quane.little.ide.presenter.{ValuePresenter, GetterPresenter, FunctionReferencePresenter}

/** An implementation of [[com.quane.little.ide.view.FunctionArgumentView]] for
  * testing purposes.
  *
  * @author Sean Connolly
  */
class MockFunctionArgumentView
  extends FunctionArgumentView
  with MockView {

  override def setName(name: String) = Unit

  override def createValueStatement() = new ValuePresenter(new MockValueView)

  override def createGetStatement() = new GetterPresenter(new MockGetStatementView)

  override def createFunctionReference() = new FunctionReferencePresenter(new MockFunctionReferenceView)

}
