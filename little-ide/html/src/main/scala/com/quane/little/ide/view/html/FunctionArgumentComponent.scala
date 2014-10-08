package com.quane.little.ide.view.html

import com.quane.little.ide.view.html.dnd.CodeDropHandler
import com.quane.little.ide.view.{CodeMenuView, CodeView, FunctionArgumentView, FunctionArgumentViewPresenter}
import com.quane.vaadin.scala.{DroppableTarget, VaadinMixin}
import com.vaadin.ui._

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
    new CodeDropHandler[FunctionArgumentViewPresenter, FunctionArgumentView](this)
  )
  private val menuWrapper = new CssLayout() with VaadinMixin

  setSpacing(true)
  setDefaultComponentAlignment(Alignment.MIDDLE_CENTER)
  setStyleName(FunctionArgumentComponent.Style)

  add(nameLabel)
  add(new Label("="))
  add(valueWrapper)
  add(menuWrapper)

  override def setName(name: String): Unit = nameLabel.setValue(name)

  override def createCodeMenuView(): CodeMenuView = {
    menuWrapper.removeAllComponents()
    menuWrapper.add(new CodeMenuLayout(this))
  }

  override def createGetterView(): GetterLayout = setValue(new GetterLayout)

  override def createMathView(): MathLayout = setValue(new MathLayout)

  override def createLogicView(): LogicLayout = setValue(new LogicLayout)

  override def createValueView(): ValueLayout = setValue(new ValueLayout)

  override def createFunctionReferenceView(): FunctionReferenceLayout = setValue(new FunctionReferenceLayout)

  private def setValue[V <: CodeView[_]](view: V with Component): V = {
    valueWrapper.component.removeAllComponents()
    valueWrapper.component.addComponent(view)
    view
  }

}