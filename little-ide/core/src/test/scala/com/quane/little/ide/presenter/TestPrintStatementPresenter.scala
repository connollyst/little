package com.quane.little.ide.presenter

import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import com.quane.little.ide.view.{ValueView, MockPrintStatementView, PrintStatementView}
import org.mockito.Mockito._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.quane.little.language.data.Value

@RunWith(classOf[JUnitRunner])
class TestPrintStatementPresenter extends FunSuite with MockitoSugar {

  test("test value is set") {
    val view = new MockPrintStatementView
    val presenter = new PrintStatementPresenter(view)
    presenter.expression = new Value("sean is cool")
    val valuePresenter = presenter.expression.asInstanceOf[ValuePresenter[_ <: ValueView]]
    assert(valuePresenter.value == "sean is cool")
  }

  /* Test View */

  test("test listener registered") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    verify(view).addViewListener(presenter)
  }

  test("test value propagates to view") {
    //    val view = mock[PrintStatementView]
    //    val presenter = new PrintStatementPresenter(view)
    //    presenter.expression = new Value("sean is cool")
    //    verify(view).setValue("sean is cool")
    fail("TODO implement test")
  }

}
