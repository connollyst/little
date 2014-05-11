package com.quane.little.ide.view.html

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import com.quane.little.ide.view.html.InteractionSimulator._
import com.quane.little.ide.view.ValueViewPresenter
import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class TestValueLayout extends FlatSpec with ShouldMatchers with MockitoSugar {

  "ValueLayout" should "propagate changing value to presenter" in {
    val view = new ValueLayout
    val presenter = mock[ValueViewPresenter]
    view.registerViewPresenter(presenter)
    setText(view.valueField, "hello world")
    verify(presenter).onValueChange("hello world")
  }
  it should "change textbox text on setValue" in {
    val view = new ValueLayout
    view.setValue("hello world")
    assert(view.valueField.getValue == "hello world")
  }

}
