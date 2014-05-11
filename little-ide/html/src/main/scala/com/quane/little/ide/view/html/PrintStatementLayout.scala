package com.quane.little.ide.view.html

import com.quane.little.ide.view.{ExpressionView, PrintStatementView}
import com.quane.little.ide.presenter.{FunctionReferencePresenter, ValuePresenter, GetStatementPresenter}
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
  addComponent(CloseButton(this))

  override def createValueStatement(): ValuePresenter[ValueLayout] = {
    removePrintValueView()
    val view = new ValueLayout
    printValue = Some(view)
    addComponent(view)
    new ValuePresenter(view)
  }

  override def createGetStatement(): GetStatementPresenter[GetStatementLayout] = {
    removePrintValueView()
    val view = new GetStatementLayout
    printValue = Some(view)
    addComponent(view)
    new GetStatementPresenter(view)
  }

  override def createFunctionReference(): FunctionReferencePresenter[FunctionReferenceLayout] = {
    removePrintValueView()
    val view = new FunctionReferenceLayout
    printValue = Some(view)
    addComponent(view)
    new FunctionReferencePresenter(view)
  }

  private def removePrintValueView(): Unit = {
    printValue match {
      case Some(removable) => removable.removeFromParent()
      case None => // do nothing
    }
    printValue = None
  }

}

