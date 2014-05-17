package com.quane.little.ide.view.html

import com.quane.vaadin.scala.{VaadinMixin, DroppableTarget}
import com.vaadin.ui.{Component, CssLayout}
import com.quane.little.ide.view.ExpressionView
import com.quane.little.ide.view.html.HasLeftAndRightExpressions._
import com.vaadin.event.dd.{DragAndDropEvent, DropHandler}
import com.vaadin.event.dd.acceptcriteria.AcceptAll
import com.quane.little.ide.view.html.dnd.CodeTransferable
import com.quane.little.data.model.{RecordId, CodeCategory}

object HasLeftAndRightExpressions {
  val LeftIndex = 0
  val RightIndex = 1
}

trait HasLeftAndRightExpressions extends Component with VaadinMixin {

  val leftValueWrapper = new DroppableTarget(new CssLayout, new LeftAndRightDropHandler(this, LeftIndex))
  val rightValueWrapper = new DroppableTarget(new CssLayout, new LeftAndRightDropHandler(this, RightIndex))

  def createLeftGetStatement() = setLeftValueComponent(new GetterLayout)

  def createRightGetStatement() = setRightValueComponent(new GetterLayout)

  def createLeftValueStatement() = setLeftValueComponent(new ValueLayout)

  def createRightValueStatement() = setRightValueComponent(new ValueLayout)

  def createLeftFunctionReference() = setLeftValueComponent(new FunctionReferenceLayout)

  def createRightFunctionReference() = setRightValueComponent(new FunctionReferenceLayout)

  def setLeftValueComponent[T <: ExpressionView[_] with RemovableComponent](view: T): T = {
    leftValueWrapper.component.removeAllComponents()
    leftValueWrapper.component.addComponent(view)
    view
  }

  def setRightValueComponent[T <: ExpressionView[_] with RemovableComponent](view: T): T = {
    rightValueWrapper.component.removeAllComponents()
    rightValueWrapper.component.addComponent(view)
    view
  }

  def requestSetExpression(codeId: RecordId, index: Int)

  def requestSetFunctionReference(codeId: RecordId, index: Int)

}

private class LeftAndRightDropHandler(view: HasLeftAndRightExpressions, index: Int) extends DropHandler {

  override def getAcceptCriterion = AcceptAll.get()

  override def drop(event: DragAndDropEvent) =
    event.getTransferable match {
      case transferable: CodeTransferable if transferable.category == CodeCategory.Expression =>
        view.requestSetExpression(transferable.codeId, index)
      case transferable: CodeTransferable if transferable.category == CodeCategory.Function =>
        view.requestSetFunctionReference(transferable.codeId, index)
      case _ =>
        throw new IllegalAccessException("Drop not supported: " + event.getTransferable)
    }

}