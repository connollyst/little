package com.quane.little.ide.presenter

import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import com.quane.little.ide.view.SetStatementView
import org.mockito.Mockito._
import org.mockito.Matchers._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestSetStatementPresenter extends FunSuite with MockitoSugar {

  test("test name is set") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    presenter.name = "sean is cool"
    assert(presenter.name == "sean is cool")
  }

  test("test value is set") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    presenter.value = "sean is cool"
    assert(presenter.value == "sean is cool")
  }

  /* Test View */

  test("test listener registered") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    verify(view).addViewListener(presenter)
  }

  test("test name initialized in view") {
    val view = mock[SetStatementView]
    new SetStatementPresenter(view)
    verify(view).setName(anyString())
  }

  test("test value initialized in view") {
    val view = mock[SetStatementView]
    new SetStatementPresenter(view)
    verify(view).setValue(anyString())
  }

  test("test name propagates to view") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    presenter.name = "sean is cool"
    verify(view).setName("sean is cool")
  }

  test("test value propagates to view") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    presenter.value = "sean is cool"
    verify(view).setValue("sean is cool")
  }

  test("test name is set on listen event") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    presenter.nameChanged("sean is cool")
    assert(presenter.name == "sean is cool")
  }

  test("test value is set on listen event") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    presenter.valueChanged("sean is cool")
    assert(presenter.value == "sean is cool")
  }

  test("test name is not propagated to view on listen event") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    presenter.nameChanged("sean is cool")
    verify(view, never()).setName("sean is cool")
  }

  test("test value is not propagated to view on listen event") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    presenter.valueChanged("sean is cool")
    verify(view, never()).setValue("sean is cool")
  }

}
