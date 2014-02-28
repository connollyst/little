package com.quane.little.ide.presenter

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.quane.little.ide.view.FunctionParameterView
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

@RunWith(classOf[JUnitRunner])
class TestFunctionParameterPresenter extends FunSuite with MockitoSugar {

  test("test name is set") {
    val presenter = new FunctionParameterPresenter(mock[FunctionParameterView])
    presenter.name = "sean is cool"
    assert(presenter.name == "sean is cool")
  }

  /* Test View */

  test("test listener registered") {
    val view = mock[FunctionParameterView]
    val presenter = new FunctionParameterPresenter(view)
    verify(view).addViewListener(presenter)
  }

  test("test name propagates to view") {
    val view = mock[FunctionParameterView]
    val presenter = new FunctionParameterPresenter(view)
    presenter.name = "sean is cool"
    verify(view).setName("sean is cool")
  }

  test("test name is set on view event") {
    val view = mock[FunctionParameterView]
    val presenter = new FunctionParameterPresenter(view)
    presenter.onNameChanged("sean is cool")
    assert(presenter.name == "sean is cool")
  }

  test("test name is not propagated to view on view event") {
    val view = mock[FunctionParameterView]
    val presenter = new FunctionParameterPresenter(view)
    presenter.onNameChanged("sean is cool")
    verify(view, never()).setName("sean is cool")
  }

}
