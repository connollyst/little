package com.quane.little.ide.view.html

import com.quane.little.ide.view.{BlockView, ConditionalView}
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
  with RemovableComponent {

  private val thenBlockWrapper = new CssLayout with VaadinMixin
  private val elseBlockWrapper = new CssLayout with VaadinMixin

  setSpacing(false)
  setStyleName(ConditionalLayout.Style)
  addComponent(createThenHeader())
  addComponent(createThenBody())
  addComponent(createElseHeader())
  addComponent(createElseBody())
  addComponent(createFooter())

  private def createThenHeader(): Component = {
    new ConditionalHeader("if <TODO>")
  }

  private def createThenBody(): Component = {
    createBody(thenBlockWrapper)
  }

  private def createElseHeader(): Component = {
    val elseHeader = new Label
    elseHeader.setValue("else")
    elseHeader.setStyleName(ConditionalLayout.StyleElseHead)
    elseHeader
  }

  private def createElseBody(): Component = {
    createBody(elseBlockWrapper)
  }

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

  // TODO this expression layout belongs in the header
  override def setConditionStatement() = new BlockLayout

  override def setThenBlock(): BlockView =
    thenBlockWrapper.removeAll().add(new BlockLayout)

  override def setElseBlock(): BlockView =
    elseBlockWrapper.removeAll().add(new BlockLayout)

}

class ConditionalHeader(condition: String) extends HorizontalLayout {

  def this() {
    this("<condition>")
  }

  setStyleName(ConditionalLayout.StyleIfHead)
  addComponent(new Label(condition))

}