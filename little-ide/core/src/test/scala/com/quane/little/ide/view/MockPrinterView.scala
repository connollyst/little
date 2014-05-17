package com.quane.little.ide.view

/** An implementation of [[com.quane.little.ide.view.PrinterView]] for
  * testing purposes.
  *
  * @author Sean Connolly
  */
class MockPrinterView
  extends PrinterView
  with MockView {

  override def createValueStatement() = new MockValueView

  override def createGetStatement() = new MockGetterView

  override def createFunctionReference() = new MockFunctionReferenceView

}