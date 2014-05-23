package com.quane.little.ide.view

import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

object MockFunctionReferenceView extends MockitoSugar {

  def apply(): MockFunctionReferenceView = new MockFunctionReferenceView

  def mocked(): FunctionReferenceView = {
    val view = mock[FunctionReferenceView]
    when(view.createArgument()).then(MockView.answer(MockFunctionArgumentView.apply))
    view
  }

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
