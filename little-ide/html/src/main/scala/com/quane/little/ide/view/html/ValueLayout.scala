package com.quane.little.ide.view.html

import com.quane.little.ide.view.ValueView
import com.vaadin.ui.{TextField, CssLayout}
import com.vaadin.event.FieldEvents.{TextChangeListener, TextChangeEvent}

/** An HTML layout view representing an explicit value, a constant.
  *
  * @author Sean Connolly
  */
class ValueLayout
  extends CssLayout
  with ValueView
  with RemovableComponent {

  val valueField = createValueField()

  addComponent(valueField)

  override def setValue(value: String): Unit = valueField.setValue(value)

  private def createValueField() = new TextField {
    setInputPrompt("value")
    addTextChangeListener(new TextChangeListener {
      def textChange(event: TextChangeEvent) = presenter.onValueChange(event.getText)
    })
  }

}
