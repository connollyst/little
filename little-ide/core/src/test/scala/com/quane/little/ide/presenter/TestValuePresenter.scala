package com.quane.little.ide.presenter

import com.quane.little.ide.view.ValueView
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import org.mockito.Matchers._

@RunWith(classOf[JUnitRunner])
class TestValuePresenter extends FunSuite with MockitoSugar {

  test("test value initialized in view") {
    val view = mock[ValueView]
    new ValuePresenter(view)
    verify(view).setValue(anyString())
  }

  test("test value is set on view event") {
    val view = mock[ValueView]
    val presenter = new ValuePresenter(view)
    presenter.onValueChange("sean is cool")
    assert(presenter.value == "sean is cool")
  }

  test("test value propagates to view") {
    val view = mock[ValueView]
    val presenter = new ValuePresenter(view)
    presenter.value = "sean is cool"
    verify(view).setValue("sean is cool")
  }

  test("test value is not propagated to view on view event") {
    val view = mock[ValueView]
    val presenter = new ValuePresenter(view)
    presenter.onValueChange("sean is cool")
    verify(view, never()).setValue("sean is cool")
  }

}
