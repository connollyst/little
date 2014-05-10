package com.quane.little.ide.view.html

import com.quane.little.ide.view.FunctionReferenceView
import com.quane.little.ide.presenter.FunctionArgumentPresenter
import com.vaadin.ui.{Alignment, Label, HorizontalLayout}
import com.quane.vaadin.scala.VaadinMixin

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

  setSpacing(true)
  setDefaultComponentAlignment(Alignment.MIDDLE_LEFT)
  setStyleNames(ExpressionLayout.Style, FunctionReferenceLayout.Style)

  addComponent(nameLabel)
  addComponent(CloseButton(this))

  override def setName(name: String): Unit = nameLabel.setValue(name)

  override def createArgument(): FunctionArgumentPresenter[_] = {
    val view = new FunctionArgumentComponent
    addComponent(view)
    new FunctionArgumentPresenter(view)
  }

}