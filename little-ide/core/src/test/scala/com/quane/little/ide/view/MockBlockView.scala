package com.quane.little.ide.view

/** An implementation of [[com.quane.little.ide.view.BlockView]] for testing
  * purposes.
  *
  * @author Sean Connolly
  */
class MockBlockView
  extends BlockView
  with MockView {

  override def addMathStep(): MathView = new MockMathView

  override def addMathStep(index: Int): MathView = new MockMathView

  override def addConditionalStep() = new MockConditionalView

  override def addConditionalStep(index: Int) = new MockConditionalView

  override def addGetStep() = new MockGetterView

  override def addGetStep(index: Int) = new MockGetterView

  override def addSetStep() = new MockSetterView

  override def addSetStep(index: Int) = new MockSetterView

  override def addLogicStep() = new MockLogicView

  override def addLogicStep(index: Int) = new MockLogicView

  override def addPrintStep() = new MockPrinterView

  override def addPrintStep(index: Int) = new MockPrinterView

  override def addFunctionStep() = new MockFunctionReferenceView

  override def addFunctionStep(index: Int) = new MockFunctionReferenceView

}
