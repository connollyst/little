package com.quane.little.ide.view.html

import com.quane.little.ide.view.{ExpressionView, PrintStatementView}
import vaadin.scala.HorizontalLayout
import vaadin.scala.Label
import vaadin.scala.MenuBar
import com.quane.little.ide.presenter.{FunctionReferencePresenter, ValuePresenter, GetStatementPresenter}

object PrintStatementLayout {
  val Style = "l-print"
}

class PrintStatementLayout
  extends HorizontalLayout
  with PrintStatementView
  with HtmlComponent {

  private val printLabel = Label("print")
  private var printValue: Option[ExpressionView[_]] = None

  styleName = ExpressionLayout.Style + " " + PrintStatementLayout.Style
  spacing = true
  add(printLabel)
  add(new PrintMenuBar(this))

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
    removePrintValue()
    val view = new ValueLayout
    printValue = Some(view)
    add(view)
    new ValuePresenter(view)
  }

  override def createGetStatement(): GetStatementPresenter[GetStatementLayout] = {
    removePrintValue()
    val view = new GetStatementLayout
    printValue = Some(view)
    add(view)
    new GetStatementPresenter(view)
  }

  override def createFunctionReference(): FunctionReferencePresenter[FunctionReferenceComponent] = {
    removePrintValue()
    val view = new FunctionReferenceComponent
    printValue = Some(view)
    add(view)
    new FunctionReferencePresenter(view)
  }

  private def removePrintValue(): Unit = {
    printValue match {
      case e: Some[ExpressionView[_]] => e.get.removeFromParent()
      case None => // do nothing
    }
    printValue = None
  }

}

private class PrintMenuBar(view: PrintStatementLayout)
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