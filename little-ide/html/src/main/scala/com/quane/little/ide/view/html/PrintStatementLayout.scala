package com.quane.little.ide.view.html

import com.quane.little.ide.view.{ExpressionView, PrintStatementView}
import com.quane.little.ide.presenter.{FunctionReferencePresenter, ValuePresenter, GetStatementPresenter}
import com.quane.little.language.exceptions.NotImplementedError
import com.vaadin.ui.{MenuBar, HorizontalLayout, Label}
import com.quane.little.ide.presenter.command.{IDECommandExecutor, AddValueCommand}

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
  private var printValue: Option[ExpressionView[_]] = None

  setStyleName(ExpressionLayout.Style + " " + PrintStatementLayout.Style)
  setSpacing(true)
  addComponent(printLabel)
  addComponent(new PrintMenuBar(this))
  addComponent(CloseButton(this))

  override def createValueStatement(): ValuePresenter[ValueLayout] = {
    removePrintValue()
    val view = new ValueLayout
    printValue = Some(view)
    addComponent(view)
    new ValuePresenter(view)
  }

  override def createGetStatement(): GetStatementPresenter[GetStatementLayout] = {
    removePrintValue()
    val view = new GetStatementLayout
    printValue = Some(view)
    addComponent(view)
    new GetStatementPresenter(view)
  }

  override def createFunctionReference(): FunctionReferencePresenter[FunctionReferenceLayout] = {
    removePrintValue()
    val view = new FunctionReferenceLayout
    printValue = Some(view)
    addComponent(view)
    new FunctionReferencePresenter(view)
  }

  private def removePrintValue(): Unit = {
    printValue match {
      case e: Some[ExpressionView[_]] => throw new NotImplementedError("boooooozzzzeee") //e.get.removeFromParent()
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
    def menuSelected(item: MenuBar#MenuItem) = view.presenter.requestAddGetStatement()
  })
  val functions = item.addItem("functions", null, null)
  functions.addItem("move", null, new MenuBar.Command {
    def menuSelected(item: MenuBar#MenuItem) = view.presenter.requestAddFunctionReference(item.getText)
  })
  functions.addItem("stop", null, new MenuBar.Command {
    def menuSelected(item: MenuBar#MenuItem) = view.presenter.requestAddFunctionReference(item.getText)
  })
  functions.addItem("turn", null, new MenuBar.Command {
    def menuSelected(item: MenuBar#MenuItem) = view.presenter.requestAddFunctionReference(item.getText)
  })

}