package com.quane.little.ide.view

import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import com.quane.little.ide.view.MockView._

object MockFunctionDefinitionView extends MockitoSugar {

  def apply() = new MockFunctionDefinitionView

  def mocked(): FunctionDefinitionView = {
    val view = mock[FunctionDefinitionView]
    when(view.createBlock()).then(answer(MockBlockView.apply))
    when(view.createFunctionParameter()).then(answer(MockFunctionParameterView.apply))
    view
  }

}

/** An implementation of [[com.quane.little.ide.view.FunctionDefinitionView]]
  * for testing purposes.
  *
  * @author Sean Connolly
  */
class MockFunctionDefinitionView
  extends FunctionDefinitionView
  with MockView {

  override def setName(name: String) = Unit

  override def createFunctionParameter() = new MockFunctionParameterView

  override def createBlock() = new MockBlockView

}
