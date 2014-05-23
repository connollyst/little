package com.quane.little.ide.view

import org.mockito.Mockito._
import org.mockito.Matchers._
import org.scalatest.mock.MockitoSugar

object MockBlockView extends MockView with MockitoSugar {

  def apply(): MockBlockView = new MockBlockView

  /** Utility function for mocking out a [[com.quane.little.ide.view.BlockView]]
    * which returns instances of the appropriate [[com.quane.little.ide.view.MockView]]
    * when asked to.
    *
    * @return the mock block view
    */
  def mocked(): BlockView = {
    val view = mock[BlockView]
    when(view.addMathStep()).then(MockView.answer(MockMathView.apply))
    when(view.addMathStep(anyInt)).then(MockView.answer(MockMathView.apply))
    when(view.addLogicStep()).then(MockView.answer(MockLogicView.apply))
    when(view.addLogicStep(anyInt)).then(MockView.answer(MockLogicView.apply))
    when(view.addGetStep()).then(MockView.answer(MockGetterView.apply))
    when(view.addGetStep(anyInt)).then(MockView.answer(MockGetterView.apply))
    when(view.addSetStep()).then(MockView.answer(MockSetterView.apply))
    when(view.addSetStep(anyInt)).then(MockView.answer(MockSetterView.apply))
    when(view.addPrintStep()).then(MockView.answer(MockPrinterView.apply))
    when(view.addPrintStep(anyInt)).then(MockView.answer(MockPrinterView.apply))
    when(view.addConditionalStep()).then(MockView.answer(MockConditionalView.apply))
    when(view.addConditionalStep(anyInt)).then(MockView.answer(MockConditionalView.apply))
    when(view.addFunctionStep()).then(MockView.answer(MockFunctionReferenceView.apply))
    when(view.addFunctionStep(anyInt)).then(MockView.answer(MockFunctionReferenceView.apply))
    view
  }

}

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
