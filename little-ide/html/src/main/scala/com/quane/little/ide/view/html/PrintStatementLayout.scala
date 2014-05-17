package com.quane.little.ide.view.html

import com.quane.little.ide.view.{ExpressionView, PrintStatementView}
import com.vaadin.ui.{Alignment, HorizontalLayout, Label}
import com.quane.vaadin.scala.VaadinMixin

object PrintStatementLayout {
  val Style = "l-print"
}

/** An HTML layout view representing a print statement.
  *
  * @author Sean Connolly
  */
class PrintStatementLayout
  extends HorizontalLayout
  with PrintStatementView
  with RemovableComponent
  with VaadinMixin {

  private val printLabel = new Label("print")
  private var printValue: Option[ExpressionView[_] with RemovableComponent] = None

  setSpacing(true)
  setDefaultComponentAlignment(Alignment.MIDDLE_LEFT)
  setStyleNames(ExpressionLayout.Style, PrintStatementLayout.Style)

  addComponent(printLabel)
  addComponent(new ExpressionMenu(this))
  addComponent(Buttons.closeButton(this))

  override def createValueStatement(): ValueLayout = {
    removePrintValueView()
    val view = new ValueLayout
    printValue = Some(view)
    add(view)
  }

  override def createGetStatement(): GetterLayout = {
    removePrintValueView()
    val view = new GetterLayout
    printValue = Some(view)
    add(view)
  }

  override def createFunctionReference(): FunctionReferenceLayout = {
    removePrintValueView()
    val view = new FunctionReferenceLayout
    printValue = Some(view)
    add(view)
  }

  private def removePrintValueView(): Unit = {
    printValue match {
      case Some(removable) => removable.removeFromParent()
      case None => // do nothing
    }
    printValue = None
  }

}

