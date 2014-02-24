package com.quane.little.ide.view.html

import vaadin.scala.{AbstractTextField, CssLayout, TextField}
import com.quane.little.ide.view.ValueView

class ValueLayout
  extends CssLayout
  with ValueView
  with HtmlComponent {

  private val _valueField = new TextField {
    textChangeListeners += {
      e: AbstractTextField.TextChangeEvent =>
        viewListeners.foreach {
          listener => listener.onValueChange(e.text)
        }
    }
  }
  add(_valueField)

  override def setValue(value: String): Unit = _valueField.value = value

}
