package com.quane.little.ide.presenter

import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import com.quane.little.ide.view.IDEView
import org.mockito.Mockito._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestIDEPresenter extends FunSuite with MockitoSugar {

  test("test listener registered") {
    val view = mock[IDEView]
    val presenter = new IDEPresenter(view)
    verify(view).addViewListener(presenter)
  }

  test("test toolbox created") {
    val view = mock[IDEView]
    new IDEPresenter(view)
    verify(view).createToolbox()
  }

  test("test workspace created") {
    val view = mock[IDEView]
    new IDEPresenter(view)
    verify(view).createWorkspace()
  }

}
