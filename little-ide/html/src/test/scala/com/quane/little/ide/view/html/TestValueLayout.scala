package com.quane.little.ide.view.html

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import com.quane.little.ide.view.html.InteractionSimulator._
import com.quane.little.ide.view.ValueViewListener

@RunWith(classOf[JUnitRunner])
class TestValueLayout extends FunSuite with MockitoSugar {

  test("should propagate changing value to presenter") {
    val view = new ValueLayout
    val presenter = mock[ValueViewListener]
    view.addViewListener(presenter)
    setText(view.valueField, "hello world")
    verify(presenter).onValueChange("hello world")
  }

  test("should change textbox text on setValue") {
    val view = new ValueLayout
    view.setValue("hello world")
    assert(view.valueField.value.get == "hello world")
  }


}
