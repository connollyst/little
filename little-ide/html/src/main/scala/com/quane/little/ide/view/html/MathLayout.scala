package com.quane.little.ide.view.html

import com.quane.little.ide.view.{ExpressionView, MathView}
import com.vaadin.ui.{Alignment, Label, CssLayout, HorizontalLayout}
import com.quane.vaadin.scala.{DroppableTarget, VaadinMixin}
import com.vaadin.event.dd.{DragAndDropEvent, DropHandler}
import com.vaadin.event.dd.acceptcriteria.AcceptAll
import com.quane.little.ide.view.html.dnd.CodeTransferable
import com.quane.little.data.model.CodeCategory
import com.quane.little.ide.presenter.command._
import com.quane.little.ide.view.html.MathLayout._
import com.quane.little.language.math.BasicMathOperation.BasicMathOperation

object MathLayout {
  val Style = "l-math"
  val LeftIndex = 0
  val RightIndex = 1
}

/** An HTML layout view representing a [[com.quane.little.language.math.Math]]
  * expression.
  *
  * @author Sean Connolly
  */
class MathLayout
  extends HorizontalLayout
  with MathView
  with VaadinMixin {

  private val leftValueWrapper = new DroppableTarget(new CssLayout, new MathDropHandler(this, LeftIndex))
  private val operatorLabel = new Label("?")
  private val rightValueWrapper = new DroppableTarget(new CssLayout, new MathDropHandler(this, RightIndex))

  setSpacing(true)
  setDefaultComponentAlignment(Alignment.MIDDLE_LEFT)
  setStyleNames(ExpressionLayout.Style, MathLayout.Style)

  add(leftValueWrapper)
  add(operatorLabel)
  add(rightValueWrapper)

  override def setOperation(operation: BasicMathOperation) = operatorLabel.setValue(operation.toString)

  override def createLeftGetStatement() = setLeftValueComponent(new GetterLayout)

  override def createRightGetStatement() = setRightValueComponent(new GetterLayout)

  override def createLeftValueStatement() = setLeftValueComponent(new ValueLayout)

  override def createRightValueStatement() = setRightValueComponent(new ValueLayout)

  override def createLeftFunctionReference() = setLeftValueComponent(new FunctionReferenceLayout)

  override def createRightFunctionReference() = setRightValueComponent(new FunctionReferenceLayout)

  private def setLeftValueComponent[T <: ExpressionView[_] with RemovableComponent](view: T): T = {
    leftValueWrapper.component.removeAllComponents()
    leftValueWrapper.component.addComponent(view)
    view
  }

  private def setRightValueComponent[T <: ExpressionView[_] with RemovableComponent](view: T): T = {
    rightValueWrapper.component.removeAllComponents()
    rightValueWrapper.component.addComponent(view)
    view
  }

}

private class MathDropHandler(view: MathLayout, index: Int) extends DropHandler {

  override def getAcceptCriterion = AcceptAll.get()

  override def drop(event: DragAndDropEvent) =
    event.getTransferable match {
      case transferable: CodeTransferable if transferable.category == CodeCategory.Expression =>
        IDECommandExecutor.execute(
          new AddExpressionCommand(view.presenter, transferable.codeId, index)
        )
      case transferable: CodeTransferable if transferable.category == CodeCategory.Function =>
        IDECommandExecutor.execute(
          new AddFunctionReferenceCommand(view.presenter, transferable.codeId, index)
        )
      case _ =>
        throw new IllegalAccessException("Drop not supported: " + event.getTransferable)
    }

}