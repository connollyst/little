package com.quane.little.ide.view

import org.mockito.Mockito._
import org.mockito.Matchers._
import org.scalatest.mock.MockitoSugar
import com.quane.little.ide.view.MockView._

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
    when(view.addMathStep()).then(answer(MockMathView.apply))
    when(view.addMathStep(anyInt)).then(answer(MockMathView.apply))
    when(view.addLogicStep()).then(answer(MockLogicView.apply))
    when(view.addLogicStep(anyInt)).then(answer(MockLogicView.apply))
    when(view.addGetStep()).then(answer(MockGetterView.apply))
    when(view.addGetStep(anyInt)).then(answer(MockGetterView.apply))
    when(view.addSetStep()).then(answer(MockSetterView.apply))
    when(view.addSetStep(anyInt)).then(answer(MockSetterView.apply))
    when(view.addPrintStep()).then(answer(MockPrinterView.apply))
    when(view.addPrintStep(anyInt)).then(answer(MockPrinterView.apply))
    when(view.addConditionalStep()).then(answer(MockConditionalView.apply))
    when(view.addConditionalStep(anyInt)).then(answer(MockConditionalView.apply))
    when(view.addFunctionStep()).then(answer(MockFunctionReferenceView.apply))
    when(view.addFunctionStep(anyInt)).then(answer(MockFunctionReferenceView.apply))
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

  override def addCodeMenu() = addCodeMenu(0)

  override def addCodeMenu(index: Int) = new MockCodeMenuView

  override def addMathStep() = addMathStep(0)

  override def addMathStep(index: Int) = new MockMathView

  override def addConditionalStep() = addConditionalStep(0)

  override def addConditionalStep(index: Int) = new MockConditionalView

  override def addGetStep() = addGetStep(0)

  override def addGetStep(index: Int) = new MockGetterView

  override def addSetStep() = addSetStep(0)

  override def addSetStep(index: Int) = new MockSetterView

  override def addLogicStep() = addLogicStep(0)

  override def addLogicStep(index: Int) = new MockLogicView

  override def addPrintStep() = addPrintStep(0)

  override def addPrintStep(index: Int) = new MockPrinterView

  override def addFunctionStep() = addFunctionStep(0)

  override def addFunctionStep(index: Int) = new MockFunctionReferenceView

}
