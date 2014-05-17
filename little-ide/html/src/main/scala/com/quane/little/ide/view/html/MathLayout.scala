package com.quane.little.ide.view.html

import com.quane.little.ide.view.{ExpressionView, MathView}
import com.vaadin.ui.{Alignment, Label, CssLayout, HorizontalLayout}
import com.quane.little.language.math.BasicMathOperation
import com.quane.vaadin.scala.VaadinMixin

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
  with VaadinMixin {

  private val leftValueWrapper = new CssLayout
  private val rightValueWrapper = new CssLayout

  setSpacing(true)
  setDefaultComponentAlignment(Alignment.MIDDLE_LEFT)
  setStyleNames(ExpressionLayout.Style, MathLayout.Style)

  add(leftValueWrapper)
  add(new Label("?"))
  add(rightValueWrapper)

  override def setOperation(operation: BasicMathOperation): Unit = {}

  override def createLeftGetStatement() = setLeftValueComponent(new GetterLayout)

  override def createRightGetStatement() = setRightValueComponent(new GetterLayout)

  override def createLeftValueStatement() = setLeftValueComponent(new ValueLayout)

  override def createRightValueStatement() = setRightValueComponent(new ValueLayout)

  override def createLeftFunctionReference() = setLeftValueComponent(new FunctionReferenceLayout)

  override def createRightFunctionReference() = setRightValueComponent(new FunctionReferenceLayout)

  private def setLeftValueComponent[T <: ExpressionView[_] with RemovableComponent](view: T): T = {
    leftValueWrapper.removeAllComponents()
    leftValueWrapper.addComponent(view)
    view
  }

  private def setRightValueComponent[T <: ExpressionView[_] with RemovableComponent](view: T): T = {
    rightValueWrapper.removeAllComponents()
    rightValueWrapper.addComponent(view)
    view
  }

}
