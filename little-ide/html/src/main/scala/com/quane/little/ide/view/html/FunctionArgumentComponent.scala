package com.quane.little.ide.view.html

import vaadin.scala.{MenuBar, HorizontalLayout, Label}
import com.quane.little.ide.view.{ExpressionView, FunctionArgumentView}
import com.quane.little.ide.presenter.{FunctionReferencePresenter, GetStatementPresenter, ValuePresenter}

object FunctionArgumentComponent {
  private val Style = "l-function-ref-arg"
}

class FunctionArgumentComponent
  extends HorizontalLayout
  with FunctionArgumentView
  with HtmlComponent
  with CloseableComponent {

  private val nameLabel = new Label()
  private var valueComponent: Option[ExpressionView[_]] = None

  styleName = FunctionArgumentComponent.Style
  spacing = true

  add(nameLabel)
  add(Label("="))
  add(new FunctionArgumentMenuBar(this))
  add(CloseButton(this))

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
      case e: Some[ExpressionView[_]] => e.get.removeFromParent()
      case None => // do nothing
    }
    valueComponent = None
  }

  override def setName(name: String): Unit = nameLabel.value = name

}

private class FunctionArgumentMenuBar(view: FunctionArgumentComponent)
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