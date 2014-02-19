package com.quane.little.ide.presenter

import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import com.quane.little.ide.view.BlockView
import org.mockito.Mockito._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestBlockPresenter extends FunSuite with MockitoSugar {

  test("test listener registered") {
    val view = mock[BlockView]
    val presenter = new BlockPresenter(view)
    verify(view).addViewListener(presenter)
  }

}
