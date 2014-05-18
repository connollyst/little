package com.quane.little.ide.view.html

import com.quane.little.ide.view.{FunctionArgumentViewPresenter, ExpressionView, FunctionArgumentView}
import com.vaadin.ui._
import com.quane.vaadin.scala.{DroppableTarget, VaadinMixin}
import com.quane.little.ide.view.html.dnd.ExpressionDropHandler

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
  private val valueWrapper = new DroppableTarget(
    new CssLayout,
    new ExpressionDropHandler[FunctionArgumentViewPresenter, FunctionArgumentView](this)
  )

  setSpacing(true)
  setDefaultComponentAlignment(Alignment.MIDDLE_CENTER)
  setStyleName(FunctionArgumentComponent.Style)

  add(nameLabel)
  add(new Label("="))
  add(new ExpressionMenu(this))
  add(valueWrapper)

  override def setName(name: String): Unit = nameLabel.setValue(name)

  override def createGetExpression(): GetterLayout = setValue(new GetterLayout)

  override def createMathExpression(): MathLayout = setValue(new MathLayout)

  override def createLogicExpression(): LogicLayout = setValue(new LogicLayout)

  override def createValueExpression(): ValueLayout = setValue(new ValueLayout)

  override def createFunctionReference(): FunctionReferenceLayout = setValue(new FunctionReferenceLayout)

  private def setValue[V <: ExpressionView[_]](view: V with Component): V = {
    valueWrapper.component.removeAllComponents()
    valueWrapper.component.addComponent(view)
    view
  }

}