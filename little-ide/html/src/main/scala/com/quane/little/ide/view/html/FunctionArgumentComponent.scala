package com.quane.little.ide.view.html

import com.quane.little.ide.view.{ExpressionView, FunctionArgumentView}
import com.vaadin.ui._
import com.quane.vaadin.scala.{DroppableTarget, VaadinMixin}
import com.vaadin.event.dd.{DragAndDropEvent, DropHandler}
import com.vaadin.event.dd.acceptcriteria.AcceptAll
import com.quane.little.ide.view.html.dnd.CodeTransferable
import com.quane.little.data.model.CodeCategory

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
  with RemovableComponent
  with VaadinMixin {

  private val nameLabel = new Label
  private val valueWrapper = new DroppableTarget(new CssLayout, new FunctionArgumentDropHandler(this))

  setSpacing(true)
  setDefaultComponentAlignment(Alignment.MIDDLE_CENTER)
  setStyleName(FunctionArgumentComponent.Style)

  add(nameLabel)
  add(new Label("="))
  add(new ExpressionMenu(this))
  add(valueWrapper)

  override def setName(name: String): Unit = nameLabel.setValue(name)

  override def createValueStatement(): ValueLayout = setValue(new ValueLayout)

  override def createGetStatement(): GetterLayout = setValue(new GetterLayout)

  override def createFunctionReference(): FunctionReferenceLayout = setValue(new FunctionReferenceLayout)

  private def setValue[V <: ExpressionView[_]](view: V with Component): V = {
    valueWrapper.component.removeAllComponents()
    valueWrapper.component.addComponent(view)
    view
  }

}

private class FunctionArgumentDropHandler(view: FunctionArgumentComponent) extends DropHandler {

  override def getAcceptCriterion = AcceptAll.get()

  override def drop(event: DragAndDropEvent) =
    event.getTransferable match {
      case transferable: CodeTransferable if transferable.category == CodeCategory.Expression =>
        view.presenter.requestAddExpression(transferable.codeId, 0)
      case transferable: CodeTransferable if transferable.category == CodeCategory.Function =>
        view.presenter.requestAddFunctionReference(transferable.codeId, 0)
      case _ =>
        throw new IllegalAccessException("Drop not supported: " + event.getTransferable)
    }

}