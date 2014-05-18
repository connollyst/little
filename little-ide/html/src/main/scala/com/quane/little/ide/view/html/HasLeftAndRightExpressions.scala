package com.quane.little.ide.view.html

import com.quane.vaadin.scala.{VaadinMixin, DroppableTarget}
import com.vaadin.ui.{Component, CssLayout}
import com.quane.little.ide.view.{ViewOfLeftAndRightExpressions, ExpressionView}
import com.quane.little.ide.view.html.HasLeftAndRightExpressions._
import com.vaadin.event.dd.{DragAndDropEvent, DropHandler}
import com.vaadin.event.dd.acceptcriteria.AcceptAll
import com.quane.little.ide.view.html.dnd.CodeTransferable
import com.quane.little.data.model.{RecordId, CodeType}

object HasLeftAndRightExpressions {
  val LeftIndex = 0
  val RightIndex = 1
}

trait HasLeftAndRightExpressions
  extends Component
  with ViewOfLeftAndRightExpressions
  with VaadinMixin {

  val leftValueWrapper = new DroppableTarget(new CssLayout, new LeftAndRightDropHandler(this, LeftIndex))
  val rightValueWrapper = new DroppableTarget(new CssLayout, new LeftAndRightDropHandler(this, RightIndex))

  override def createLeftMath() = setLeftValueComponent(new MathLayout)

  override def createRightMath() = setRightValueComponent(new MathLayout)

  override def createLeftGetter() = setLeftValueComponent(new GetterLayout)

  override def createRightGetter() = setRightValueComponent(new GetterLayout)

  override def createLeftLiteral() = setLeftValueComponent(new ValueLayout)

  override def createRightLiteral() = setRightValueComponent(new ValueLayout)

  override def createLeftLogic() = setLeftValueComponent(new LogicLayout)

  override def createRightLogic() = setRightValueComponent(new LogicLayout)

  override def createLeftFunctionReference() = setLeftValueComponent(new FunctionReferenceLayout)

  override def createRightFunctionReference() = setRightValueComponent(new FunctionReferenceLayout)

  def setLeftValueComponent[T <: ExpressionView[_] with Component](view: T): T = {
    leftValueWrapper.component.removeAllComponents()
    leftValueWrapper.component.addComponent(view)
    view
  }

  def setRightValueComponent[T <: ExpressionView[_] with Component](view: T): T = {
    rightValueWrapper.component.removeAllComponents()
    rightValueWrapper.component.addComponent(view)
    view
  }

  def requestSetExpression(codeId: RecordId, index: Int)

  def requestSetFunctionReference(codeId: RecordId, index: Int)

}

/** Handles drops on both the left and right value components. Leftness and
  * rightness is specified by `index` provided on creation.
  *
  * @param view the main view component
  * @param index the index indicating leftness or rightness
  * @see [[com.quane.little.ide.view.html.HasLeftAndRightExpressions.LeftIndex]]
  * @see [[com.quane.little.ide.view.html.HasLeftAndRightExpressions.RightIndex]]
  */
private class LeftAndRightDropHandler(view: HasLeftAndRightExpressions, index: Int) extends DropHandler {

  override def getAcceptCriterion = AcceptAll.get()

  override def drop(event: DragAndDropEvent) =
    event.getTransferable match {
      case transferable: CodeTransferable if transferable.category == CodeType.Expression =>
        view.requestSetExpression(transferable.codeId, index)
      case transferable: CodeTransferable if transferable.category == CodeType.Function =>
        view.requestSetFunctionReference(transferable.codeId, index)
      case _ =>
        throw new IllegalAccessException("Drop not supported: " + event.getTransferable)
    }

}