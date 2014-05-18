package com.quane.little.ide.view

/** An implementation of [[com.quane.little.ide.view.SetterView]] for
  * testing purposes.
  *
  * @author Sean Connolly
  */
class MockSetterView
  extends SetterView
  with MockView {

  override def setName(name: String) = Unit

  override def createValueExpression() = new MockValueView

  override def createGetExpression() = new MockGetterView

  override def createFunctionReference() = new MockFunctionReferenceView

}
