package com.quane.little.ide.view.html

import com.quane.little.ide.view.{ExpressionView, FunctionArgumentView}
import com.quane.little.ide.presenter.{FunctionReferencePresenter, GetStatementPresenter, ValuePresenter}
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
  private var valueComponent: Option[ExpressionView[_] with RemovableComponent] = None

  setStyleName(FunctionArgumentComponent.Style)
  setSpacing(true)

  addComponent(nameLabel)
  addComponent(new Label("="))
  addComponent(new ExpressionMenu(this))

  private def value[T <: ExpressionView[_] with RemovableComponent]: T =
    valueComponent match {
      case Some(v) => v match {
        case t: T => t
        case _ =>
          throw new IllegalAccessException("Didn't expect value component to be a" + v.getClass)
      }
      case None =>
        throw new IllegalAccessException("Expected value component")
    }

  private def value_=(view: ExpressionView[_] with RemovableComponent) = {
    removeValueComponent()
    valueComponent = Some(view)
    addComponent(view)
  }

  override def createValueStatement(): ValuePresenter[ValueLayout] = {
    value = new ValueLayout
    new ValuePresenter(value)
  }

  override def createGetStatement(): GetStatementPresenter[GetStatementLayout] = {
    value = new GetStatementLayout
    new GetStatementPresenter(value)
  }

  override def createFunctionReference(): FunctionReferencePresenter[FunctionReferenceLayout] = {
    value = new FunctionReferenceLayout
    new FunctionReferencePresenter(value)
  }

  private def removeValueComponent(): Unit = {
    valueComponent match {
      case Some(e) => e.removeFromParent()
      case None => // do nothing
    }
    valueComponent = None
  }

  override def setName(name: String): Unit = nameLabel.setValue(name)

}