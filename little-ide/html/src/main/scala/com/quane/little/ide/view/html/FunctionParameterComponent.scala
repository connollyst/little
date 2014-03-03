package com.quane.little.ide.view.html

import com.quane.little.ide.view.html.FunctionParameterComponent._
import vaadin.scala.TextField
import com.quane.little.ide.view.FunctionParameterView
import vaadin.scala.AbstractTextField.TextChangeEvent

object FunctionParameterComponent {
  private val Style = "l-function-def-param"
}

/** An HTML layout view representing a function reference.
  *
  * @author Sean Connolly
  */
class FunctionParameterComponent
  extends TextField
  with FunctionParameterView
  with RemovableComponent {

  styleName = Style

  prompt = "parameter name"

  textChangeListeners += {
    e: TextChangeEvent =>
      _viewPresenter.foreach {
        listener => listener.onNameChanged(e.text)
      }
  }

  override def setName(name: String): Unit = {
    value = name
  }

}