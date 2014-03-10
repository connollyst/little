package com.quane.little.ide.view.html

import com.quane.little.ide.view.{ExpressionView, SetStatementView}
import com.quane.little.ide.presenter.{FunctionReferencePresenter, GetStatementPresenter, ValuePresenter}
import com.quane.little.language.exceptions.NotImplementedError
import com.vaadin.ui.{MenuBar, TextField, HorizontalLayout, Label}
import com.vaadin.event.FieldEvents.{TextChangeListener, TextChangeEvent}
import com.vaadin.ui.MenuBar.Command

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
  with RemovableComponent {

  private val nameField = createNameTextField()
  private var valueComponent: Option[ExpressionView[_]] = None

  setStyleName(ExpressionLayout.Style + " " + SetStatementLayout.Style)
  setSpacing(true)

  addComponent(nameField)
  addComponent(new Label("="))
  addComponent(new ValueMenuBar(this))
  addComponent(CloseButton(this))

  override def setName(name: String): Unit = nameField.setValue(name)

  private def createNameTextField() = new TextField {
    setInputPrompt("variable name")
    addTextChangeListener(new TextChangeListener {
      def textChange(event: TextChangeEvent) = {
        _viewPresenter.foreach {
          listener => listener.onNameChange(event.getText)
        }
      }
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

  private[html] def requestAddTextLiteral() = {
    // TODO skip if already a value statement
    val view = createValueStatement()
    _viewPresenter.foreach {
      listener => listener.onValueChange(view)
    }
  }

  private[html] def requestAddGetStatement() = {
    // TODO skip if already a get statement
    val view = createGetStatement()
    _viewPresenter.foreach {
      listener => listener.onValueChange(view)
    }
  }

  private[html] def requestAddFunctionReference(name: String) = {
    // TODO skip if already this function reference
    val view = createFunctionReference()
    _viewPresenter.foreach {
      // TODO we need to look up the function definition
      listener => listener.onValueChange(view)
    }
  }

}

private class ValueMenuBar(view: SetStatementLayout)
  extends MenuBar {

  val item = addItem("∆", null, null)
  item.addItem("text", null, new Command {
    def menuSelected(item: MenuBar#MenuItem) =
      view.requestAddTextLiteral()
  })
  item.addItem("get", null, new Command {
    def menuSelected(item: MenuBar#MenuItem) =
      view.requestAddGetStatement()
  })
  val functions = item.addItem("functions", null, null)
  functions.addItem("move", null, new Command {
    def menuSelected(item: MenuBar#MenuItem) =
      view.requestAddFunctionReference(item.getText)
  })
  functions.addItem("stop", null, new Command {
    def menuSelected(item: MenuBar#MenuItem) =
      view.requestAddFunctionReference(item.getText)
  })
  functions.addItem("turn", null, new Command {
    def menuSelected(item: MenuBar#MenuItem) =
      view.requestAddFunctionReference(item.getText)
  })

}