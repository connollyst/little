package com.quane.little.ide.view.html

import vaadin.scala.{MenuBar, TextField, Label, HorizontalLayout}
import com.quane.little.ide.view.{ExpressionView, SetStatementView}
import com.quane.little.ide.presenter.{FunctionReferencePresenter, GetStatementPresenter, ValuePresenter}
import vaadin.scala.AbstractTextField.TextChangeEvent


object SetStatementLayout {
  val Style = "l-set"
}

class SetStatementLayout
  extends HorizontalLayout
  with SetStatementView
  with HtmlComponent
  with CloseableComponent {

  private val nameField = createNameTextField()
  private var valueComponent: Option[ExpressionView[_]] = None

  styleName = ExpressionLayout.Style + " " + SetStatementLayout.Style
  spacing = true

  add(nameField)
  add(Label("="))
  add(new ValueMenuBar(this))
  add(CloseButton(this))

  override def setName(name: String): Unit = nameField.value = name

  private def createNameTextField() = new TextField {
    prompt = "variable name"
    textChangeListeners += {
      e: TextChangeEvent =>
        viewListeners.foreach {
          listener => listener.onNameChange(e.text)
        }
    }
  }

  override def createValueStatement(): ValuePresenter[ValueLayout] = {
    removeValueComponent()
    val view = new ValueLayout
    valueComponent = Some(view)
    add(view)
    new ValuePresenter(view)
  }

  override def createGetStatement(): GetStatementPresenter[GetStatementLayout] = {
    removeValueComponent()
    val view = new GetStatementLayout
    valueComponent = Some(view)
    add(view)
    new GetStatementPresenter(view)
  }

  override def createFunctionReference(): FunctionReferencePresenter[FunctionReferenceLayout] = {
    removeValueComponent()
    val view = new FunctionReferenceLayout
    valueComponent = Some(view)
    add(view)
    new FunctionReferencePresenter(view)
  }

  private def removeValueComponent(): Unit = {
    valueComponent match {
      case e: Some[ExpressionView[_]] =>
        e.get.removeFromParent()
      case None => // do nothing
    }
    valueComponent = None
  }

  private[html] def requestAddTextLiteral() = {
    // TODO skip if already a value statement
    val view = createValueStatement()
    viewListeners.foreach {
      listener => listener.onValueChange(view)
    }
  }

  private[html] def requestAddGetStatement() = {
    // TODO skip if already a get statement
    val view = createGetStatement()
    viewListeners.foreach {
      listener => listener.onValueChange(view)
    }
  }

  private[html] def requestAddFunctionReference(name: String) = {
    // TODO skip if already this function reference
    val view = createFunctionReference()
    viewListeners.foreach {
      // TODO we need to look up the function definition
      listener => listener.onValueChange(view)
    }
  }

}

private class ValueMenuBar(view: SetStatementLayout)
  extends MenuBar {

  val item = addItem("∆")
  item.addItem("text", {
    item => view.requestAddTextLiteral()
  })
  item.addItem("get", {
    item => view.requestAddGetStatement()
  })
  val functions = item.addItem("functions")
  functions.addItem("move", {
    item => view.requestAddFunctionReference(item.text)
  })
  functions.addItem("stop", {
    item => view.requestAddFunctionReference(item.text)
  })
  functions.addItem("turn", {
    item => view.requestAddFunctionReference(item.text)
  })

}