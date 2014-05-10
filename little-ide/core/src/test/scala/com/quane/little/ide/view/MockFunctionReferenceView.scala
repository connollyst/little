package com.quane.little.ide.view

import com.quane.little.ide.presenter.FunctionArgumentPresenter

/** An implementation of [[com.quane.little.ide.view.FunctionReferenceView]] for
  * testing purposes.
  *
  * @author Sean Connolly
  */
class MockFunctionReferenceView
  extends FunctionReferenceView
  with MockView {

  override def setName(name: String) = Unit

  override def createArgument() = new FunctionArgumentPresenter(new MockFunctionArgumentView)

  override def clearArguments() = Unit

}
