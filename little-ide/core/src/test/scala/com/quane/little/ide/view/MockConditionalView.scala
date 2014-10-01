package com.quane.little.ide.view

import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar

object MockConditionalView extends MockitoSugar {

  def apply(): MockConditionalView = new MockConditionalView

  /** Utility function for mocking out a [[com.quane.little.ide.view.ConditionalView]]
    * which returns instances of the appropriate mock views when asked to.
    *
    * @return the mock conditional view
    */
  def mocked(): ConditionalView = {
    val view = mock[ConditionalView]
    when(view.createThenBlock()).thenReturn(new MockBlockView)
    when(view.createElseBlock()).thenReturn(new MockBlockView)
    when(view.createMathCondition()).thenReturn(new MockMathView)
    when(view.createGetterCondition()).thenReturn(new MockGetterView)
    when(view.createLogicCondition()).thenReturn(new MockLogicView)
    when(view.createConditionalCondition()).thenReturn(new MockConditionalView)
    when(view.createFunctionReferenceCondition()).thenReturn(new MockFunctionReferenceView)
    view
  }

}

/** An implementation of [[com.quane.little.ide.view.ConditionalView]] for
  * testing purposes.
  *
  * @author Sean Connolly
  */
class MockConditionalView
  extends ConditionalView
  with MockView {

  def createLogicCondition() = new MockLogicView

  def createMathCondition() = new MockMathView

  def createGetterCondition() = new MockGetterView

  def createConditionalCondition() = new MockConditionalView()

  def createFunctionReferenceCondition() = new MockFunctionReferenceView

  def createThenBlock() = new MockBlockView

  def createElseBlock() = new MockBlockView

}
