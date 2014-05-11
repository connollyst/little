package com.quane.little.ide.view.html

import com.quane.little.ide.view.{ExpressionView, FunctionArgumentView}
import com.quane.little.ide.presenter.{FunctionReferencePresenter, GetStatementPresenter, ValuePresenter}
import com.quane.little.language.exceptions.NotImplementedError
import com.vaadin.ui.{Label, HorizontalLayout}

object FunctionArgumentComponent {
  private val Style = "l-function-ref-arg"
}

/** An HTML layout view representing a function definition.
  *
  * @author Sean Connolly
  */
class FunctionArgumentComponent
  extends HorizontalLayout
  with FunctionArgumentView
  with RemovableComponent {

  private val nameLabel = new Label()
  private var valueComponent: Option[ExpressionView[_]] = None

  setStyleName(FunctionArgumentComponent.Style)
  setSpacing(true)

  addComponent(nameLabel)
  addComponent(new Label("="))
  addComponent(new ExpressionMenu(this))
  addComponent(CloseButton(this))

  override def createValueStatement(): ValuePresenter[ValueLayout] = {
    removeValueComponent()
    val view = new ValueLayout
    valueComponent = Some(view)
    addComponent(view)
    new ValuePresenter(view)
  }

  override def createGetStatement(): GetStatementPresenter[GetStatementLayout] = {
    removeValueComponent()
    val view = new GetStatementLayout
    valueComponent = Some(view)
    addComponent(view)
    new GetStatementPresenter(view)
  }

  override def createFunctionReference(): FunctionReferencePresenter[FunctionReferenceLayout] = {
    removeValueComponent()
    val view = new FunctionReferenceLayout
    valueComponent = Some(view)
    addComponent(view)
    new FunctionReferencePresenter(view)
  }

  private def removeValueComponent(): Unit = {
    valueComponent match {
      case e: Some[ExpressionView[_]] => throw new NotImplementedError("booooozzzzeeee") // e.get.removeFromParent()
      case None => // do nothing
    }
    valueComponent = None
  }

  override def setName(name: String): Unit = nameLabel.setValue(name)

}