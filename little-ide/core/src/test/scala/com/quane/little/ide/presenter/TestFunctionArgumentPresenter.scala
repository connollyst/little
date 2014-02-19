package com.quane.little.ide.presenter

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.quane.little.ide.view.FunctionArgumentView
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

@RunWith(classOf[JUnitRunner])
class TestFunctionArgumentPresenter extends FunSuite with MockitoSugar {

  test("test compiled name (default)") {
    val view = mock[FunctionArgumentView]
    val presenter = new FunctionArgumentPresenter(view)
    presenter.name = "sean is cool"
    verify(view).setName("sean is cool")
  }

}
