package com.quane.little.ide.view.html

import com.quane.little.ide.view.MathView
import com.vaadin.ui._
import com.quane.vaadin.scala.EnumerationComboBox
import com.quane.little.data.model.RecordId
import com.quane.little.ide.presenter.command.{AddFunctionReferenceCommand, AddExpressionCommand, IDECommandExecutor}
import com.quane.little.language.math.BasicMathOperation
import com.quane.little.language.math.BasicMathOperation.BasicMathOperation

object MathLayout {
  val Style = "l-math"
}

/** An HTML layout view representing a [[com.quane.little.language.math.Math]]
  * expression.
  *
  * @author Sean Connolly
  */
class MathLayout
  extends HorizontalLayout
  with MathView
  with RemovableComponent
  with HasLeftAndRightExpressions {

  val operatorField = new EnumerationComboBox(BasicMathOperation)

  setSpacing(true)
  setDefaultComponentAlignment(Alignment.MIDDLE_LEFT)
  setStyleNames(ExpressionLayout.Style, MathLayout.Style)

  add(leftValueWrapper)
  add(operatorField)
  add(rightValueWrapper)

  override def setOperation(operation: BasicMathOperation) = operatorField.setValue(operation.toString)

  override def requestSetExpression(codeId: RecordId, index: Int) =
    IDECommandExecutor.execute(
      new AddExpressionCommand(presenter, codeId, index)
    )

  override def requestSetFunctionReference(codeId: RecordId, index: Int) =
    IDECommandExecutor.execute(
      new AddFunctionReferenceCommand(presenter, codeId, index)
    )

}