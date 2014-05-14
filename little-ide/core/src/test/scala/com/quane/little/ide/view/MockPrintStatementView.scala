package com.quane.little.ide.view

/** An implementation of [[com.quane.little.ide.view.PrintStatementView]] for
  * testing purposes.
  *
  * @author Sean Connolly
  */
class MockPrintStatementView
  extends PrintStatementView
  with MockView {

  override def createValueStatement() = new MockValueView

  override def createGetStatement() = new MockGetStatementView

  override def createFunctionReference() = new MockFunctionReferenceView

}
