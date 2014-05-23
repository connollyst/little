package com.quane.little.ide.view

object MockFunctionReferenceView {
  def apply(): MockFunctionReferenceView = new MockFunctionReferenceView
}

/** An implementation of [[com.quane.little.ide.view.FunctionReferenceView]] for
  * testing purposes.
  *
  * @author Sean Connolly
  */
class MockFunctionReferenceView
  extends FunctionReferenceView
  with MockView {

  override def setName(name: String) = Unit

  override def createArgument() = new MockFunctionArgumentView

  override def clearArguments() = Unit

}
