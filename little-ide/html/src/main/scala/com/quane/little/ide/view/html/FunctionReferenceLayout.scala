package com.quane.little.ide.view.html

import com.quane.little.ide.view.{FunctionArgumentView, FunctionReferenceView}
import com.vaadin.ui.{Alignment, Label, HorizontalLayout}
import com.quane.vaadin.scala.VaadinMixin
import scala.collection.mutable

object FunctionReferenceLayout {
  val Style = ExpressionLayout.Style + " l-function-ref"
}

/** An HTML layout view representing a function reference.
  *
  * @author Sean Connolly
  */
class FunctionReferenceLayout
  extends HorizontalLayout
  with FunctionReferenceView
  with RemovableComponent
  with VaadinMixin {

  private val nameLabel = new Label("???")
  private val args = mutable.ListBuffer[FunctionArgumentComponent]()

  setSpacing(true)
  setDefaultComponentAlignment(Alignment.MIDDLE_LEFT)
  setStyleNames(ExpressionLayout.Style, FunctionReferenceLayout.Style)

  add(nameLabel)
  add(Buttons.closeButton(this))

  override def setName(name: String) = nameLabel.setValue(name)

  override def createArgument(): FunctionArgumentView = {
    val view = new FunctionArgumentComponent
    args += view
    add(view)
  }

  override def clearArguments() =
    args foreach {
      arg => removeComponent(arg)
    }

}