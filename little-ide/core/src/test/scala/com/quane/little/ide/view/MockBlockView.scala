package com.quane.little.ide.view

import org.mockito.Mockito._
import org.mockito.Matchers._
import org.scalatest.mock.MockitoSugar
import com.quane.little.ide.view.MockView._

object MockBlockView extends MockitoSugar {

  def apply(): MockBlockView = new MockBlockView

  /** Utility function for mocking out a [[com.quane.little.ide.view.BlockView]]
    * which returns instances of the appropriate mock views when asked to.
    *
    * @return the mock block view
    */
  def mocked(): BlockView = {
    val view = mock[BlockView]
    when(view.addCodeMenu(anyInt)).then(answer(MockCodeMenuView.apply))
    when(view.addMathStep(anyInt)).then(answer(MockMathView.apply))
    when(view.addLogicStep(anyInt)).then(answer(MockLogicView.apply))
    when(view.addGetStep(anyInt)).then(answer(MockGetterView.apply))
    when(view.addSetStep(anyInt)).then(answer(MockSetterView.apply))
    when(view.addPrintStep(anyInt)).then(answer(MockPrinterView.apply))
    when(view.addConditionalStep(anyInt)).then(answer(MockConditionalView.apply))
    when(view.addFunctionStep(anyInt)).then(answer(MockFunctionReferenceView.apply))
    view
  }

}

/** An implementation of [[com.quane.little.ide.view.BlockView]] for testing
  * purposes.
  *
  * @author Sean Connolly
  */
class MockBlockView extends BlockView with MockView {

  override def addCodeMenu(index: Int) = new MockCodeMenuView

  override def addMathStep(index: Int) = new MockMathView

  override def addConditionalStep(index: Int) = new MockConditionalView

  override def addGetStep(index: Int) = new MockGetterView

  override def addSetStep(index: Int) = new MockSetterView

  override def addLogicStep(index: Int) = new MockLogicView

  override def addPrintStep(index: Int) = new MockPrinterView

  override def addFunctionStep(index: Int) = new MockFunctionReferenceView

}
