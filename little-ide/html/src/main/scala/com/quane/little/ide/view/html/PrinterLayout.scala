package com.quane.little.ide.view.html

import com.quane.little.ide.view.{CodeMenuView, CodeView, PrinterView}
import com.quane.vaadin.scala.VaadinMixin
import com.vaadin.ui._

object PrinterLayout {
  val Style = "l-print"
}

/** An HTML layout view representing a print statement.
  *
  * @author Sean Connolly
  */
class PrinterLayout
  extends HorizontalLayout
  with PrinterView
  with RemovableComponent
  with VaadinMixin {

  private val printLabel = new Label("print")
  private var printValue: Option[CodeView[_] with RemovableComponent] = None
  private val menuWrapper = new CssLayout() with VaadinMixin

  setSpacing(true)
  setDefaultComponentAlignment(Alignment.MIDDLE_LEFT)
  setStyleNames(ExpressionLayout.Style, PrinterLayout.Style)

  addComponent(printLabel)
  addComponent(menuWrapper)
  addComponent(Buttons.closeButton(this))

  override def createCodeMenu(): CodeMenuView = {
    menuWrapper.removeAllComponents()
    menuWrapper.add(new CodeMenuLayout(this))
  }

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

  /**
   * Removes the component from this container.<br/>
   * If the component is the print value view, it is removed from this view also.
   *
   * @param c the component to be removed.
   */
  override def removeComponent(c: Component): Unit = {
    printValue match {
      case Some(removable) => if (c == removable) printValue = None // TODO notify presenter
      case None => // do nothing
    }
    super.removeComponent(c)
  }

}