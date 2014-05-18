package com.quane.little.ide.view.html

import com.quane.little.ide.view._
import com.vaadin.ui._
import com.vaadin.server.Sizeable
import com.quane.vaadin.scala.VaadinMixin

object ConditionalLayout {
  val Style = "l-if"
  val StyleIfHead = Style + "-head"
  val StyleElseHead = Style + "-else-head"
  val StyleBody = Style + "-body"
  val StyleBodyLeft = Style + "-body-left"
  val StyleFoot = Style + "-foot"
}

/** An HTML layout view representing a conditional expression.
  *
  * @author Sean Connolly
  */
class ConditionalLayout
  extends VerticalLayout
  with ConditionalView
  with RemovableComponent
  with VaadinMixin {

  private val thenHeader = new ConditionalHeader
  private val thenBlockWrapper = new CssLayout with VaadinMixin
  private val elseBlockWrapper = new CssLayout with VaadinMixin

  setSpacing(false)
  setStyleName(ConditionalLayout.Style)

  add(thenHeader)
  add(createThenBody())
  add(createElseHeader())
  add(createElseBody())
  add(createFooter())

  private def createThenBody(): Component = createBody(thenBlockWrapper)

  private def createElseHeader(): Component = {
    val elseHeader = new Label
    elseHeader.setValue("else")
    elseHeader.setStyleName(ConditionalLayout.StyleElseHead)
    elseHeader
  }

  private def createElseBody(): Component = createBody(elseBlockWrapper)

  private def createBody(blockWrapper: Component): Component = {
    val body = new HorizontalLayout
    val bodyLeft = new Label
    bodyLeft.setHeight(100, Sizeable.Unit.PERCENTAGE)
    bodyLeft.setStyleName(ConditionalLayout.StyleBodyLeft)
    body.addComponent(bodyLeft)
    body.addComponent(blockWrapper)
    body.setStyleName(ConditionalLayout.StyleBody)
    body.setSpacing(false)
    body
  }

  private def createFooter(): Component = {
    val footer = new Label()
    footer.setStyleName(ConditionalLayout.StyleFoot)
    footer
  }

  override def createMathCondition() = thenHeader.createMathCondition()

  override def createGetterCondition() = thenHeader.createGetterCondition()

  override def createLogicCondition() = thenHeader.createLogicalCondition()

  override def createConditionalCondition() = thenHeader.createConditionalCondition()

  override def createFunctionReferenceCondition() = thenHeader.createFunctionReferenceCondition()

  override def createThenBlock() = thenBlockWrapper.removeAll().add(new BlockLayout)

  override def createElseBlock() = elseBlockWrapper.removeAll().add(new BlockLayout)

}

class ConditionalHeader extends HorizontalLayout with VaadinMixin {

  private val conditionWrapper = new CssLayout with VaadinMixin

  setStyleName(ConditionalLayout.StyleIfHead)

  add(conditionWrapper)

  def createMathCondition() = setCondition(new MathLayout)

  def createGetterCondition() = setCondition(new GetterLayout)

  def createLogicalCondition() = setCondition(new LogicLayout)

  def createConditionalCondition() = setCondition(new ConditionalLayout)

  def createFunctionReferenceCondition() = setCondition(new FunctionReferenceLayout)

  def setCondition[V <: ExpressionView[_] with Component](view: V): V = {
    conditionWrapper.removeAll()
    conditionWrapper.add(view)
  }

}