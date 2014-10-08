package com.quane.little.ide.view

import org.mockito.Mockito._
import com.quane.little.ide.view.MockView._
import org.scalatest.mock.MockitoSugar

object MockPrinterView extends MockitoSugar {

  def apply(): MockPrinterView = new MockPrinterView

  /** Utility function for mocking out a [[com.quane.little.ide.view.BlockView]]
    * which returns instances of the appropriate mock views when asked to.
    *
    * @return the mock block view
    */
  def mocked(): PrinterView = {
    val view = mock[PrinterView]
    when(view.createCodeMenu()).then(answer(MockCodeMenuView.apply))
    when(view.createValueView()).then(answer(MockValueView.apply))
    when(view.createGetView()).then(answer(MockGetterView.apply))
    when(view.createFunctionReference()).then(answer(MockFunctionReferenceView.apply))
    view
  }
}

/** An implementation of [[com.quane.little.ide.view.PrinterView]] for
  * testing purposes.
  *
  * @author Sean Connolly
  */
class MockPrinterView extends PrinterView with MockView {

  override def createCodeMenu() = new MockCodeMenuView

  override def createValueView() = new MockValueView

  override def createGetView() = new MockGetterView

  override def createFunctionReference() = new MockFunctionReferenceView

}
