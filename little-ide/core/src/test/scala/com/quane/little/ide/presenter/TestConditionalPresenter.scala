package com.quane.little.ide.presenter

import org.scalatest.WordSpec
import org.scalatest.mock.MockitoSugar
import com.quane.little.ide.view._
import org.mockito.Mockito._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.quane.little.ide.MockIDEBindingModule

/** Test cases for the [[com.quane.little.ide.presenter.ConditionalPresenter]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestConditionalPresenter extends WordSpec with MockitoSugar {

  implicit val bindingModule = MockIDEBindingModule

  "ConditionalPresenter" should {
    "test listener registered" in {
      val view = mockConditionalView
      val presenter = new ConditionalPresenter(view)
      verify(view).registerViewPresenter(presenter)
    }
  }

  /** Utility function for mocking out a [[com.quane.little.ide.view.ConditionalView]]
    * which returns instances of the appropriate [[com.quane.little.ide.view.MockView]]
    * when asked to.
    *
    * @return the mock conditional view
    */
  private def mockConditionalView: ConditionalView = {
    val view = mock[ConditionalView]
    when(view.createThenBlock()).thenReturn(new MockBlockView)
    when(view.createElseBlock()).thenReturn(new MockBlockView)
    when(view.createMathCondition()).thenReturn(new MockMathView)
    when(view.createGetterCondition()).thenReturn(new MockGetterView)
    when(view.createLogicCondition()).thenReturn(new MockLogicView)
    when(view.createConditionalCondition()).thenReturn(new MockConditionalView)
    when(view.createFunctionReferenceCondition()).thenReturn(new MockFunctionReferenceView)
    view
  }

}
