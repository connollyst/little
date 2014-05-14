package com.quane.little.ide.view

/** An implementation of [[com.quane.little.ide.view.SetStatementView]] for
  * testing purposes.
  *
  * @author Sean Connolly
  */
class MockSetStatementView
  extends SetStatementView
  with MockView {

  override def setName(name: String) = Unit

  override def createValueStatement() = new MockValueView

  override def createGetStatement() = new MockGetStatementView

  override def createFunctionReference() = new MockFunctionReferenceView

}
