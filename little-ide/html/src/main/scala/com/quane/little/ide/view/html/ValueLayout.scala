package com.quane.little.ide.view.html

import vaadin.scala.{CssLayout, TextField}
import com.quane.little.ide.view.ValueView
import vaadin.scala.AbstractTextField.TextChangeEvent

class ValueLayout
  extends CssLayout
  with ValueView
  with HtmlComponent {

  val valueField = new TextField {
    textChangeListeners += {
      e: TextChangeEvent =>
      viewListeners.foreach {
          listener => listener.onValueChange(e.text)
        }
    }
  }
  add(valueField)

  override def setValue(value: String): Unit = valueField.value = value

}
