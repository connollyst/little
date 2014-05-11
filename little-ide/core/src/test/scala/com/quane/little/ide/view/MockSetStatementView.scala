package com.quane.little.ide.view

import com.quane.little.ide.presenter.{GetterPresenter, FunctionReferencePresenter, ValuePresenter}

/** An implementation of [[com.quane.little.ide.view.SetStatementView]] for
  * testing purposes.
  *
  * @author Sean Connolly
  */
class MockSetStatementView
  extends SetStatementView
  with MockView {

  override def setName(name: String) = Unit

  override def createValueStatement() = new ValuePresenter(new MockValueView)

  override def createGetStatement() = new GetterPresenter(new MockGetStatementView)

  override def createFunctionReference() = new FunctionReferencePresenter(new MockFunctionReferenceView)

}
