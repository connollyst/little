package com.quane.little.ide.view.html

import vaadin.scala.{CssLayout, TextField}
import com.quane.little.ide.view.ValueView

class ValueLayout
  extends CssLayout
  with ValueView
  with HtmlComponent {

  private val _valueField = new TextField

  add(_valueField)

  override def setValue(value: String): Unit = _valueField.value = value

}
