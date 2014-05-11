package com.quane.little.ide.view.html

import com.vaadin.ui.MenuBar
import com.quane.little.data.service.{FunctionService, StatementService}
import com.vaadin.ui.MenuBar.Command
import com.quane.little.ide.presenter.command.{AddStatementCommand, AddExpressionCommand, AddFunctionReferenceCommand, IDECommandExecutor}
import com.quane.little.ide.view.{ViewPresenter, View}
import com.quane.little.ide.presenter.{PresenterAcceptsStatement, PresenterAcceptsExpression, PresenterAccepts, PresenterAcceptsFunctionReference}
import com.quane.little.data.model.{RecordId, PrimitiveRecord}

/** A menu for selecting an [[com.quane.little.language.Expression]] to be added
  * to a view. The options available are defined by the presenter's manifest.
  *
  * @see [[com.quane.little.ide.presenter.PresenterAccepts]]
  *
  * @author Sean Connolly
  */
class ExpressionMenu[P <: ViewPresenter](view: View[P], index: => Int)(implicit m: Manifest[P])
  extends MenuBar {

  def this(view: View[P])(implicit m: Manifest[P]) = this(view, {
    0
  })

  private val item = addItem("∆", null)
  initExpressionMenuItems()
  initStatementMenuItems()
  initFunctionMenuItems()

  /** Initialize the menu items for primitive expressions.
    */
  private def initExpressionMenuItems(): Unit = {
    StatementService().all foreach {
      expression =>
        if (accepts(expression)) {
          item.addItem(expression.name, new Command {
            def menuSelected(selectedItem: MenuBar#MenuItem) = addExpressionClicked(expression.id)
          })
        } else {
          item.addItem(expression.name, null).setEnabled(false)
        }
    }
  }

  /** Initialize the menu items for primitive expressions.
    */
  private def initStatementMenuItems(): Unit = {
    StatementService().all foreach {
      statement =>
        if (accepts(statement)) {
          item.addItem(statement.name, new Command {
            def menuSelected(selectedItem: MenuBar#MenuItem) = addStatementClicked(statement.id)
          })
        } else {
          item.addItem(statement.name, null).setEnabled(false)
        }
    }
  }

  /** Initialize the menu items for function expressions.
    */
  private def initFunctionMenuItems(): Unit = {
    val functions = item.addItem("functions", null)
    if (m <:< manifest[PresenterAcceptsFunctionReference]) {
      FunctionService().findByUser("connollyst") foreach {
        function =>
          functions.addItem(function.definition.name, new Command {
            def menuSelected(item: MenuBar#MenuItem) = addFunctionClicked(function.id)
          })
      }
    } else {
      functions.setEnabled(false)
    }
  }

  def addExpressionClicked(id: RecordId) = {
    val p = view.presenter.asInstanceOf[PresenterAcceptsExpression]
    IDECommandExecutor.execute(new AddExpressionCommand(p, id, index))
  }

  def addStatementClicked(id: RecordId) = {
    val p = view.presenter.asInstanceOf[PresenterAcceptsStatement]
    IDECommandExecutor.execute(new AddStatementCommand(p, id, index))
  }

  def addFunctionClicked(id: RecordId) = {
    val p = view.presenter.asInstanceOf[PresenterAcceptsFunctionReference]
    IDECommandExecutor.execute(new AddFunctionReferenceCommand(p, id, index))
  }

  private def accepts(primitive: PrimitiveRecord): Boolean =
    PresenterAccepts.acceptsPrimitive(view, primitive)

}
