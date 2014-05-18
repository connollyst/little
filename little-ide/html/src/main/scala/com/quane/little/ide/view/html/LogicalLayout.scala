package com.quane.little.ide.view.html

import com.vaadin.ui.{Alignment, HorizontalLayout}
import com.quane.little.ide.view.LogicalView
import com.quane.little.data.model.RecordId
import com.quane.little.ide.presenter.command.{AddExpressionCommand, AddFunctionReferenceCommand, IDECommandExecutor}
import com.quane.little.language.LogicalOperation
import com.quane.little.language.LogicalOperation.LogicalOperation
import com.quane.vaadin.scala.EnumerationComboBox

object LogicalLayout {
  val Style = "l-logical"
}

/** An HTML layout view representing a [[com.quane.little.language.Logical]]
  * expression.
  *
  * @author Sean Connolly
  */
class LogicalLayout
  extends HorizontalLayout
  with LogicalView
  with HasLeftAndRightExpressions {

  val operatorField = new EnumerationComboBox(LogicalOperation)

  setSpacing(true)
  setDefaultComponentAlignment(Alignment.MIDDLE_LEFT)
  setStyleNames(ExpressionLayout.Style, MathLayout.Style)

  add(leftValueWrapper)
  add(operatorField)
  add(rightValueWrapper)

  override def setOperation(operation: LogicalOperation) = operatorField.setValue(operation.toString)

  override def requestSetExpression(codeId: RecordId, index: Int) =
    IDECommandExecutor.execute(
      new AddExpressionCommand(presenter, codeId, index)
    )

  override def requestSetFunctionReference(codeId: RecordId, index: Int) =
    IDECommandExecutor.execute(
      new AddFunctionReferenceCommand(presenter, codeId, index)
    )

}
