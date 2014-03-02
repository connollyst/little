package com.quane.little.ide.view.html

import vaadin.scala.{CssLayout, TextField}
import com.quane.little.ide.view.ValueView
import vaadin.scala.AbstractTextField.TextChangeEvent

class ValueLayout
  extends CssLayout
  with ValueView
  with HtmlComponent
  with CloseableComponent {

  val valueField = createValueField()

  add(valueField)
  add(CloseButton(this))

  override def setValue(value: String): Unit = valueField.value = value

  private def createValueField() = new TextField {
    prompt = "value"
    textChangeListeners += {
      e: TextChangeEvent =>
        viewListeners.foreach {
          listener => listener.onValueChange(e.text)
        }
    }
  }
  
}
