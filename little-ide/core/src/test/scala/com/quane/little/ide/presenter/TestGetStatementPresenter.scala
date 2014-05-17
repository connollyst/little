package com.quane.little.ide.presenter

import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import com.quane.little.ide.view.GetterView
import org.mockito.Mockito._
import org.mockito.Matchers._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestGetStatementPresenter extends FunSuite with MockitoSugar {

  test("test name is set") {
    val view = mock[GetterView]
    val presenter = new GetterPresenter(view)
    presenter.name = "sean is cool"
    assert(presenter.name == "sean is cool")
  }

  /* Test View */

  test("test listener registered") {
    val view = mock[GetterView]
    val presenter = new GetterPresenter(view)
    verify(view).registerViewPresenter(presenter)
  }

  test("test name initialized in view") {
    val view = mock[GetterView]
    new GetterPresenter(view)
    verify(view).setName(anyString())
  }

  test("test name propagates to view") {
    val view = mock[GetterView]
    val presenter = new GetterPresenter(view)
    presenter.name = "sean is cool"
    verify(view).setName("sean is cool")
  }

  test("test name is set on view event") {
    val view = mock[GetterView]
    val presenter = new GetterPresenter(view)
    presenter.onNameChange("sean is cool")
    assert(presenter.name == "sean is cool")
  }

  test("test name is not propagated to view on view event") {
    val view = mock[GetterView]
    val presenter = new GetterPresenter(view)
    presenter.onNameChange("sean is cool")
    verify(view, never()).setName("sean is cool")
  }

}
