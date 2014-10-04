package com.quane.little.ide.view

import com.quane.little.language.data.ValueType.ValueType

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

  override def setName(name: String) = Unit

  override def setValueType(valueType: ValueType) = Unit

}
