package com.quane.little.ide.presenter

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.quane.little.ide.view.FunctionArgumentView
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

@RunWith(classOf[JUnitRunner])
class TestFunctionArgumentPresenter extends FunSuite with MockitoSugar {

  test("test name is set") {
    val presenter = new FunctionArgumentPresenter(mock[FunctionArgumentView])
    presenter.name = "sean is cool"
    assert(presenter.name == "sean is cool")
  }

  test("test value is set") {
    val presenter = new FunctionArgumentPresenter(mock[FunctionArgumentView])
    presenter.value = "sean is cool"
    assert(presenter.value == "sean is cool")
  }

  /* Test View */

  test("test listener registered") {
    val view = mock[FunctionArgumentView]
    val presenter = new FunctionArgumentPresenter(view)
    verify(view).addViewListener(presenter)
  }

  test("test name propagates to view") {
    val view = mock[FunctionArgumentView]
    val presenter = new FunctionArgumentPresenter(view)
    presenter.name = "sean is cool"
    verify(view).setName("sean is cool")
  }

  test("test value propagates to view") {
    val view = mock[FunctionArgumentView]
    val presenter = new FunctionArgumentPresenter(view)
    presenter.value = "sean is cool"
    verify(view).setValue("sean is cool")
  }

}
