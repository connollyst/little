package com.quane.little.ide.view

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar

object MockFunctionArgumentView extends MockitoSugar {
  def apply() = new MockFunctionArgumentView

  /** Utility function for mocking out a [[com.quane.little.ide.view.FunctionArgumentView]]
    * which returns instances of the appropriate mock views when asked to.
    *
    * @return the mock function argument view
    */
  def mocked: FunctionArgumentView = {
    val view = mock[FunctionArgumentView]
    when(view.createCodeMenuView()).thenReturn(new MockCodeMenuView)
    when(view.createMathView()).thenReturn(new MockMathView)
    when(view.createLogicView()).thenReturn(new MockLogicView)
    when(view.createGetterView()).thenReturn(new MockGetterView)
    when(view.createValueView()).thenReturn(new MockValueView)
    when(view.createFunctionReferenceView()).thenReturn(new MockFunctionReferenceView)
    view
  }
}

/** An implementation of [[com.quane.little.ide.view.FunctionArgumentView]] for
  * testing purposes.
  *
  * @author Sean Connolly
  */
class MockFunctionArgumentView
  extends FunctionArgumentView
  with MockView {

  var name: Option[String] = None

  override def setName(n: String) = name = Some(n)

  override def createCodeMenuView() = new MockCodeMenuView

  override def createGetterView() = new MockGetterView

  override def createMathView() = new MockMathView

  override def createLogicView() = new MockLogicView

  override def createValueView() = new MockValueView

  override def createFunctionReferenceView() = new MockFunctionReferenceView

}
