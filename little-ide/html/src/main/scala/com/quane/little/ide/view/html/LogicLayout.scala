package com.quane.little.ide.view.html

import com.vaadin.ui.{Alignment, HorizontalLayout}
import com.quane.little.ide.view.LogicView
import com.quane.little.data.model.RecordId
import com.quane.little.ide.presenter.command.{AddExpressionCommand, AddFunctionReferenceCommand, IDECommandExecutor}
import com.quane.little.language.LogicOperation
import com.quane.little.language.LogicOperation.LogicOperation
import com.quane.vaadin.scala.EnumerationComboBox

object LogicLayout {
  val Style = "l-logical"
}

/** An HTML layout view representing a [[com.quane.little.language.Logic]]
  * expression.
  *
  * @author Sean Connolly
  */
class LogicLayout
  extends HorizontalLayout
  with LogicView
  with RemovableComponent
  with HasLeftAndRightExpressions {

  val operatorField = new EnumerationComboBox(LogicOperation)

  setSpacing(true)
  setDefaultComponentAlignment(Alignment.MIDDLE_LEFT)
  setStyleNames(ExpressionLayout.Style, MathLayout.Style)

  add(leftValueWrapper)
  add(operatorField)
  add(rightValueWrapper)

  override def setOperation(operation: LogicOperation) = operatorField.setValue(operation.toString)

  override def requestSetExpression(codeId: RecordId, index: Int) =
    IDECommandExecutor.execute(
      new AddExpressionCommand(presenter, codeId, index)
    )

  override def requestSetFunctionReference(codeId: RecordId, index: Int) =
    IDECommandExecutor.execute(
      new AddFunctionReferenceCommand(presenter, codeId, index)
    )

}
