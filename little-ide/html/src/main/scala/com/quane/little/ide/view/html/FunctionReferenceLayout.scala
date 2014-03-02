package com.quane.little.ide.view.html

import vaadin.scala.{Label, HorizontalLayout}
import com.quane.little.ide.view.FunctionReferenceView
import com.quane.little.ide.presenter.FunctionArgumentPresenter

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
  with HtmlComponent
  with CloseableComponent {

  private val nameLabel = Label("???")

  styleName = FunctionReferenceLayout.Style
  spacing = true

  add(nameLabel)
  add(CloseButton(this))

  override def setName(name: String): Unit = nameLabel.value = Some(name)

  override def createArgument(): FunctionArgumentPresenter[_] =
    new FunctionArgumentPresenter(add(new FunctionArgumentComponent))

}