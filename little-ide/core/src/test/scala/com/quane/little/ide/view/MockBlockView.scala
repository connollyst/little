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

  override def addMathStep(index: Int): MathView = new MockMathView

  override def addConditional() = new MockConditionalView

  override def addConditionalStep(index: Int) = new MockConditionalView

  override def addGetStatement() = new MockGetterView

  override def addGetStep(index: Int) = new MockGetterView

  override def addSetStatement() = new MockSetterView

  override def addSetStep(index: Int) = new MockSetterView

  override def addLogicalOperation() = new MockLogicView

  override def addLogicStep(index: Int) = new MockLogicView

  override def addPrintStatement() = new MockPrinterView

  override def addPrintStep(index: Int) = new MockPrinterView

  override def addFunctionReference() = new MockFunctionReferenceView

  override def addFunctionStep(index: Int) = new MockFunctionReferenceView

}
