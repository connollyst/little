package com.quane.little.ide.view.html

import com.quane.little.ide.view.html.FunctionParameterComponent._
import com.quane.little.ide.view.FunctionParameterView
import com.vaadin.ui.TextField
import com.vaadin.event.FieldEvents.{TextChangeListener, TextChangeEvent}

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

  setStyleName(Style)
  setInputPrompt("parameter name")

  addTextChangeListener(new TextChangeListener {
    def textChange(event: TextChangeEvent): Unit = {
      _viewPresenter.foreach {
        listener => listener.onNameChanged(event.getText)
      }
    }
  })

  override def setName(name: String): Unit = setValue(name)

}