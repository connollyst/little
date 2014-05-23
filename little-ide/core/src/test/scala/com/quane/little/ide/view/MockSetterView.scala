package com.quane.little.ide.view

object MockSetterView {
  def apply(): MockSetterView = {
    println("Creating new setter view..")
    new MockSetterView
  }
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

  override def createGetExpression() = new MockGetterView

  override def createMathExpression() = new MockMathView

  override def createLogicExpression() = new MockLogicView

  override def createValueExpression() = new MockValueView

  override def createFunctionReference() = new MockFunctionReferenceView

}
