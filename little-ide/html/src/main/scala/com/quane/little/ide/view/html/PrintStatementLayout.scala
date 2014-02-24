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
    println("Requesting add text")
    viewListeners.foreach {
      listener => listener.setExpression(createValueStatement())
    }
  }

  private[html] def requestAddGetStatement() = {
    println("Requesting add get")
    viewListeners.foreach {
      listener => listener.setExpression(createGetStatement())
    }
  }

  private[html] def requestAddFunctionReference(name: String) = {
    println("Requesting add '" + name + "'")
    viewListeners.foreach {
      // TODO we need to look up the function definition
      listener => listener.setExpression(createFunctionReference())
    }
  }

  override def createValueStatement(): ValuePresenter[ValueLayout] = {
    println("Creating value statement")
    removePrintValue()
    val view = new ValueLayout
    printValue = Some(view)
    add(view)
    new ValuePresenter(view)
  }

  override def createGetStatement(): GetStatementPresenter[GetStatementLayout] = {
    println("Creating get statement")
    removePrintValue()
    val view = new GetStatementLayout
    printValue = Some(view)
    add(view)
    new GetStatementPresenter(view)
  }

  override def createFunctionReference(): FunctionReferencePresenter[FunctionReferenceComponent] = {
    println("Creating function reference")
    removePrintValue()
    val view = new FunctionReferenceComponent
    printValue = Some(view)
    add(view)
    new FunctionReferencePresenter(view)
  }

  private def removePrintValue(): Unit = {
    printValue match {
      case e: Some[ExpressionView[_]] =>
        println("Remove old print value")
        e.get.removeFromParent()
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