package com.quane.little.ide.view.html

import com.quane.little.ide.view.{ExpressionView, SetStatementView}
import com.quane.little.ide.presenter.{FunctionReferencePresenter, GetStatementPresenter, ValuePresenter}
import com.quane.little.language.exceptions.NotImplementedError
import com.vaadin.ui.{MenuBar, TextField, HorizontalLayout, Label}
import com.vaadin.event.FieldEvents.{TextChangeListener, TextChangeEvent}
import com.vaadin.ui.MenuBar.Command
import com.quane.little.ide.presenter.command.{AddGetterCommand, AddValueCommand, AddFunctionReferenceCommand, IDECommandExecutor}
import com.quane.little.data.service.FunctionService
import com.quane.vaadin.scala.VaadinMixin

object SetStatementLayout {
  val Style = "l-set"
}

/** An HTML layout view representing a variable assignment expression, a 'setter'.
  *
  * @author Sean Connolly
  */
class SetStatementLayout
  extends HorizontalLayout
  with SetStatementView
  with RemovableComponent
  with VaadinMixin {

  private val nameField = createNameTextField()
  private var valueComponent: Option[ExpressionView[_]] = None

  setStyleNames(ExpressionLayout.Style, SetStatementLayout.Style)
  setSpacing(true)

  addComponent(nameField)
  addComponent(new Label("="))
  addComponent(new ValueMenuBar(this))
  addComponent(CloseButton(this))

  override def setName(name: String): Unit = nameField.setValue(name)

  private def createNameTextField() = new TextField {
    setInputPrompt("variable name")
    addTextChangeListener(new TextChangeListener {
      def textChange(event: TextChangeEvent) = presenter.onNameChange(event.getText)
    })
  }

  override def createValueStatement(): ValuePresenter[ValueLayout] = {
    removeValueComponent()
    val view = new ValueLayout
    valueComponent = Some(view)
    addComponent(view)
    new ValuePresenter(view)
  }

  override def createGetStatement(): GetStatementPresenter[GetStatementLayout] = {
    removeValueComponent()
    val view = new GetStatementLayout
    valueComponent = Some(view)
    addComponent(view)
    new GetStatementPresenter(view)
  }

  override def createFunctionReference(): FunctionReferencePresenter[FunctionReferenceLayout] = {
    removeValueComponent()
    val view = new FunctionReferenceLayout
    valueComponent = Some(view)
    addComponent(view)
    new FunctionReferencePresenter(view)
  }

  private def removeValueComponent(): Unit = {
    valueComponent match {
      case e: Some[ExpressionView[_]] => throw new NotImplementedError("drink") //e.get.removeFromParent()
      case None => // do nothing
    }
    valueComponent = None
  }

}

private class ValueMenuBar(view: SetStatementLayout)
  extends MenuBar {

  val item = addItem("∆", null, null)
  item.addItem("text", null, new Command {
    def menuSelected(item: MenuBar#MenuItem) =
      IDECommandExecutor.execute(new AddValueCommand(view.presenter))
  })
  item.addItem("get", null, new Command {
    def menuSelected(item: MenuBar#MenuItem) =
      IDECommandExecutor.execute(new AddGetterCommand(view.presenter))
  })
  val functions = item.addItem("functions", null, null)
  FunctionService().findByUser("connollyst") foreach {
    function =>
      functions.addItem(function.definition.name, null, new Command {
        def menuSelected(item: MenuBar#MenuItem) =
          IDECommandExecutor.execute(new AddFunctionReferenceCommand(view.presenter, function.id))
      })
  }

}