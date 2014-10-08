package com.quane.little.ide.view

object MockSetterView {
  def apply() = new MockSetterView
}

/** An implementation of [[com.quane.little.ide.view.SetterView]] for
  * testing purposes.
  *
  * @author Sean Connolly
  */
class MockSetterView
  extends SetterView
  with MockView {

  var name: Option[String] = None

  override def setName(n: String) = name = Some(n)

  override def createGetterView() = new MockGetterView

  override def createMathView() = new MockMathView

  override def createLogicView() = new MockLogicView

  override def createValueView() = new MockValueView

  override def createFunctionReferenceView() = new MockFunctionReferenceView

}
