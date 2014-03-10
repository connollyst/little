package com.quane.little.ide.view.html

import com.quane.little.ide.view.{ExpressionView, FunctionArgumentView}
import com.quane.little.ide.presenter.{FunctionReferencePresenter, GetStatementPresenter, ValuePresenter}
import com.quane.little.language.exceptions.NotImplementedError
import com.vaadin.ui.{MenuBar, Label, HorizontalLayout}
import com.vaadin.ui.MenuBar.Command

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
  with RemovableComponent {

  private val nameLabel = new Label()
  private var valueComponent: Option[ExpressionView[_]] = None

  setStyleName(FunctionArgumentComponent.Style)
  setSpacing(true)

  addComponent(nameLabel)
  addComponent(new Label("="))
  addComponent(new FunctionArgumentMenuBar(this))
  addComponent(CloseButton(this))

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
      case e: Some[ExpressionView[_]] => throw new NotImplementedError("booooozzzzeeee") // e.get.removeFromParent()
      case None => // do nothing
    }
    valueComponent = None
  }

  override def setName(name: String): Unit = nameLabel.setValue(name)

}

private class FunctionArgumentMenuBar(view: FunctionArgumentComponent)
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