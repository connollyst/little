package com.quane.little.ide.view.html

import com.quane.little.ide.view.html.FunctionParameterComponent._
import com.quane.little.ide.view.FunctionParameterView
import com.quane.little.language.data.ValueType
import com.quane.little.language.data.ValueType.ValueType
import com.quane.vaadin.scala.VaadinMixin
import com.vaadin.data.Property.{ValueChangeEvent, ValueChangeListener}
import com.vaadin.ui.{ComboBox, HorizontalLayout, TextField}
import com.vaadin.event.FieldEvents.{TextChangeListener, TextChangeEvent}

object FunctionParameterComponent {
  private val Style = "l-function-def-param"
}

/** An HTML layout view representing a function reference.
  *
  * @author Sean Connolly
  */
class FunctionParameterComponent
  extends HorizontalLayout
  with FunctionParameterView
  with RemovableComponent
  with VaadinMixin {

  setStyleName(Style)

  val nameTextbox = add(new TextField())
  nameTextbox.setInputPrompt("parameter name")
  nameTextbox.addTextChangeListener(new TextChangeListener {
    def textChange(event: TextChangeEvent): Unit = presenter.onNameChanged(event.getText)
  })
  val typeMenu = add(new ComboBox())
  ValueType.values foreach {
    value => typeMenu.addItem(value)
  }
  typeMenu.setImmediate(true)
  typeMenu.addValueChangeListener(new ValueChangeListener {
    def valueChange(event: ValueChangeEvent): Unit =
      event.getProperty.getValue match {
        case v: ValueType => presenter.onValueTypeChanged(v)
        case _ => throw new IllegalArgumentException(
          "Expected value type of " + classOf[ValueType]
            + " but found " + event.getProperty.getValue
        )
      }
  })

  override def setName(name: String): Unit = nameTextbox.setValue(name)

  override def setValueType(valueType: ValueType): Unit = typeMenu.select(valueType)

}