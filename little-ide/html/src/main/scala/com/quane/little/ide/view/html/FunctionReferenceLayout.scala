package com.quane.little.ide.view.html

import vaadin.scala.{Label, HorizontalLayout}
import com.quane.little.ide.view.FunctionReferenceView
import com.quane.little.ide.presenter.FunctionArgumentPresenter

/** A HTML layout view representing a [[com.quane.little.language.FunctionReference]].
  *
  * @author Sean Connolly
  */
class FunctionReferenceLayout
extends HorizontalLayout
  with FunctionReferenceView
  with HtmlComponent {

  private val nameLabel = Label("???")

  styleName = FunctionReferenceLayout.Style
  spacing = true

  add(nameLabel)
  add(createCloseButton())

  override def setName(name: String): Unit = nameLabel.value = Some(name)

  override def createArgument(): FunctionArgumentPresenter[_] =
    new FunctionArgumentPresenter(add(new FunctionArgumentComponent))

  private def createCloseButton(): CloseButton = {
    new CloseButton {
      clickListeners += {
        ClickEvent => println(
          "Closing " + getClass.getSimpleName + ": '"
            + nameLabel.value.getOrElse("") + "'"
        )
      }
    }
  }

}

object FunctionReferenceLayout {
  val Style = ExpressionLayout.Style + " l-function-ref"
}