package com.quane.little.ide.view

/** An implementation of [[com.quane.little.ide.view.FunctionArgumentView]] for
  * testing purposes.
  *
  * @author Sean Connolly
  */
class MockFunctionArgumentView
  extends FunctionArgumentView
  with MockView {

  def setName(name: String) = Unit

  def setValue(value: String) = Unit

}
