package com.quane.little.ide.view.html

import com.quane.little.data.model.Id
import com.quane.little.ide.presenter.command.{AddCodeCommand, IDECommandExecutor}
import com.quane.little.ide.view.MathView
import com.quane.little.language.math.BasicMathOperation
import com.quane.little.language.math.BasicMathOperation.BasicMathOperation
import com.quane.vaadin.scala.EnumerationComboBox
import com.vaadin.ui._

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
  setStyleNames(CodeLayout.Style, MathLayout.Style)

  add(leftValueWrapper)
  add(operatorField)
  add(rightValueWrapper)

  override def setOperation(operation: BasicMathOperation) = operatorField.setValue(operation.toString)

  override def requestSetCode(codeId: Id, index: Int) =
    IDECommandExecutor.execute(
      new AddCodeCommand(presenter, codeId, index)
    )

}