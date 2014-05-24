package com.quane.little.ide.view

object MockFunctionParameterView {
  def apply() = new MockFunctionParameterView
}

/** An implementation of [[com.quane.little.ide.view.FunctionParameterView]] for
  * testing purposes.
  *
  * @author Sean Connolly
  */
class MockFunctionParameterView
  extends FunctionParameterView
  with MockView {

  def setName(name: String) = Unit

}
