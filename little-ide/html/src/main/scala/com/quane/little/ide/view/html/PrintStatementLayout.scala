package com.quane.little.ide.view.html

import com.quane.little.ide.view.{ExpressionView, PrintStatementView}
import com.quane.little.ide.presenter.{FunctionReferencePresenter, ValuePresenter, GetStatementPresenter}
import com.vaadin.ui.{MenuBar, HorizontalLayout, Label}
import com.quane.little.ide.presenter.command.{AddFunctionReferenceCommand, AddGetterCommand, IDECommandExecutor, AddValueCommand}
import com.quane.little.ide.model.FunctionService
import com.quane.little.language.FunctionReference

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
  with RemovableComponent {

  private val printLabel = new Label("print")
  private var printValue: Option[ExpressionView[_] with RemovableComponent] = None

  setStyleName(ExpressionLayout.Style + " " + PrintStatementLayout.Style)
  setSpacing(true)
  addComponent(printLabel)
  addComponent(new PrintMenuBar(this))
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

private class PrintMenuBar(view: PrintStatementLayout)
  extends MenuBar {

  val item = addItem("∆", null, null)
  item.addItem("text", null, new MenuBar.Command {
    def menuSelected(item: MenuBar#MenuItem) =
      IDECommandExecutor.execute(new AddValueCommand(view.presenter))
  })
  item.addItem("get", null, new MenuBar.Command {
    def menuSelected(item: MenuBar#MenuItem) =
      IDECommandExecutor.execute(new AddGetterCommand(view.presenter))
  })
  val functionMenu = item.addItem("functions", null, null)
  println("Fetching functions for connollyst")
  val functions: List[FunctionReference] = FunctionService.findReferencesByUser("connollyst")
  functions foreach {
    function => println("Got function: " + function)
  }
  functionMenu.addItem("move", null, new MenuBar.Command {
    def menuSelected(item: MenuBar#MenuItem) =
      IDECommandExecutor.execute(new AddFunctionReferenceCommand(view.presenter, item.getText))
  })
  functionMenu.addItem("stop", null, new MenuBar.Command {
    def menuSelected(item: MenuBar#MenuItem) =
      IDECommandExecutor.execute(new AddFunctionReferenceCommand(view.presenter, item.getText))
  })
  functionMenu.addItem("turn", null, new MenuBar.Command {
    def menuSelected(item: MenuBar#MenuItem) =
      IDECommandExecutor.execute(new AddFunctionReferenceCommand(view.presenter, item.getText))
  })

}