package com.quane.little.ide.view.html

import com.quane.little.ide.view.{View, ViewPresenter, CodeMenuView}
import com.quane.little.data.model.CodeCategory.CodeCategory
import com.quane.little.data.model.{CodeType, RecordId}
import com.vaadin.ui.MenuBar
import com.quane.little.ide.presenter.{PresenterAcceptsExpression, PresenterAcceptsStatement}
import com.quane.little.ide.presenter.command.{AddExpressionCommand, AddFunctionReferenceCommand, AddStatementCommand, IDECommandExecutor}
import scala.collection.mutable
import com.vaadin.ui.MenuBar.Command
import com.quane.little.data.model.CodeType.CodeType
import com.quane.little.ide.view.html.CodeMenuLayout._

object CodeMenuLayout {
  val Style = "l-code-menu"
}

/** An HTML layout view for the 'Add Code' dropdown menu.
  * This is one of the main tools the user uses to build their program.
  *
  * @author Sean Connolly
  */
class CodeMenuLayout[P <: ViewPresenter](view: View[P], var index: () => Int) extends MenuBar with CodeMenuView {

  setStyleName(Style)

  private val root = super.addItem("+", null)
  private val categories = mutable.HashMap[CodeCategory, MenuBar#MenuItem]()

  override def addCategory(codeCategory: CodeCategory) = {
    val item = root.addItem(codeCategory.toString, null)
    categories += codeCategory -> item
  }

  override def addMenuItem(codeType: CodeType, codeCategory: CodeCategory, id: RecordId, name: String) =
    addItem(codeType, codeCategory, id, name)

  override def addMenuItemDisabled(codeType: CodeType, codeCategory: CodeCategory, id: RecordId, name: String) =
    addItem(codeType, codeCategory, id, name).setEnabled(false)

  private def addItem(codeType: CodeType, codeCategory: CodeCategory, id: RecordId, name: String) =
    categories(codeCategory).addItem(name,
      new Command {
        override def menuSelected(selectedItem: MenuBar#MenuItem) = {
          codeType match {
            case CodeType.Statement => addStatementClicked(id)
            case CodeType.Expression => addExpressionClicked(id)
            case CodeType.Function => addFunctionClicked(id)
            case _ => throw new IllegalArgumentException(
              "Unsupported menu item: " + codeType + " (" + name + ")"
            )
          }
        }
      }
    )

  def addStatementClicked(id: RecordId) = {
    val p = view.presenter.asInstanceOf[PresenterAcceptsStatement]
    IDECommandExecutor.execute(new AddStatementCommand(p, id, index()))
  }

  def addExpressionClicked(id: RecordId) = {
    val p = view.presenter.asInstanceOf[PresenterAcceptsExpression]
    IDECommandExecutor.execute(new AddExpressionCommand(p, id, index()))
  }

  def addFunctionClicked(id: RecordId) = {
    val p = view.presenter.asInstanceOf[PresenterAcceptsExpression]
    IDECommandExecutor.execute(new AddFunctionReferenceCommand(p, id, index()))
  }

}
