package com.quane.little.ide.view

import com.escalatesoft.subcut.inject.BindingModule

/** An implementation of [[com.quane.little.ide.view.BlockView]] for testing
  * purposes.
  *
  * @author Sean Connolly
  */
class MockBlockView(implicit val bindingModule: BindingModule)
  extends BlockView
  with MockView {

  override def addMath(): MathView = new MockMathView

  override def addMath(index: Int): MathView = new MockMathView

  override def addConditional() = new MockConditionalView

  override def addConditional(index: Int) = new MockConditionalView

  override def addGetStatement() = new MockGetterView

  override def addGetStatement(index: Int) = new MockGetterView

  override def addSetStatement() = new MockSetterView

  override def addSetStatement(index: Int) = new MockSetterView

  override def addLogicalOperation() = new MockLogicalView

  override def addLogicalOperation(index: Int) = new MockLogicalView

  override def addPrintStatement() = new MockPrinterView

  override def addPrintStatement(index: Int) = new MockPrinterView

  override def addFunctionReference() = new MockFunctionReferenceView

  override def addFunctionReference(index: Int) = new MockFunctionReferenceView

}
