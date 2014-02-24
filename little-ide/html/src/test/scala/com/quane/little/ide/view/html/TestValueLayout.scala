package com.quane.little.ide.view.html

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import com.quane.little.ide.presenter.ValuePresenter
import vaadin.scala.AbstractTextField.TextChangeEvent

@RunWith(classOf[JUnitRunner])
class TestValueLayout extends FunSuite with MockitoSugar {

  test("test changing value propagated to presenter") {
    val view = new ValueLayout
    val presenter = mock[ValuePresenter[ValueLayout]]
    view.addViewListener(presenter)
    setText(view, "hello world")
    verify(presenter).onValueChange("hello world")
  }

  private def setText(view: ValueLayout, text: String): Unit = {
    val field = view.valueField
    field.textChangeListeners.foreach {
      listener => listener.apply(new TextChangeEvent(field, text, 0))
    }
  }

}
