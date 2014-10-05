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
    when(view.createCodeMenu()).thenReturn(new MockCodeMenuView)
    when(view.createMathExpression()).thenReturn(new MockMathView)
    when(view.createLogicExpression()).thenReturn(new MockLogicView)
    when(view.createGetterExpression()).thenReturn(new MockGetterView)
    when(view.createValueExpression()).thenReturn(new MockValueView)
    when(view.createFunctionReference()).thenReturn(new MockFunctionReferenceView)
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

  override def createCodeMenu() = new MockCodeMenuView

  override def createGetterExpression() = new MockGetterView

  override def createMathExpression() = new MockMathView

  override def createLogicExpression() = new MockLogicView

  override def createValueExpression() = new MockValueView

  override def createFunctionReference() = new MockFunctionReferenceView

}
