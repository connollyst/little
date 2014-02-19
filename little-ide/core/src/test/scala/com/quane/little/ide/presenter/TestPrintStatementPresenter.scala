package com.quane.little.ide.presenter

import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import com.quane.little.ide.view.PrintStatementView
import org.mockito.Mockito._
import org.mockito.Matchers._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestPrintStatementPresenter extends FunSuite with MockitoSugar {

  test("test value is set") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    presenter.value = "sean is cool"
    assert(presenter.value == "sean is cool")
  }

  /* Test View */

  test("test listener registered") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    verify(view).addViewListener(presenter)
  }

  test("test value initialized in view") {
    val view = mock[PrintStatementView]
    new PrintStatementPresenter(view)
    verify(view).setValue(anyString())
  }

  test("test value propagates to view") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    presenter.value = "sean is cool"
    verify(view).setValue("sean is cool")
  }

  test("test value is set on listen event") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    presenter.valueChanged("sean is cool")
    assert(presenter.value == "sean is cool")
  }

  test("test value is not propagated to view on listen event") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    presenter.valueChanged("sean is cool")
    verify(view, never()).setValue("sean is cool")
  }

}
