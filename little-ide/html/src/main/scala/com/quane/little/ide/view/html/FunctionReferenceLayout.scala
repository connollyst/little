package com.quane.little.ide.view.html

import com.quane.little.ide.view.FunctionReferenceView
import com.quane.little.ide.presenter.FunctionArgumentPresenter
import com.vaadin.ui.{Label, HorizontalLayout}

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
  with RemovableComponent {

  private val nameLabel = new Label("???")

  setStyleName(FunctionReferenceLayout.Style)
  setSpacing(true)

  addComponent(nameLabel)
  addComponent(CloseButton(this))

  override def setName(name: String): Unit = nameLabel.setValue(name)

  override def createArgument(): FunctionArgumentPresenter[_] = {
    val view = new FunctionArgumentComponent
    addComponent(view)
    new FunctionArgumentPresenter(view)
  }

}