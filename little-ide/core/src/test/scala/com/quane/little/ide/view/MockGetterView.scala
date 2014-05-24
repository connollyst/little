package com.quane.little.ide.view

object MockGetterView {
  def apply(): MockGetterView = new MockGetterView
}

/** An implementation of [[com.quane.little.ide.view.GetterView]] for testing purposes.
  *
  * @author Sean Connolly
  */
class MockGetterView extends GetterView with MockView {

  def setName(name: String) = Unit

}
