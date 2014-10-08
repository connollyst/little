package com.quane.little.ide.view.html

import com.quane.little.data.model.CodeCategory.CodeCategory
import com.quane.little.data.model.CodeType.CodeType
import com.quane.little.data.model.{CodeType, RecordId}
import com.quane.little.ide.presenter.PresenterAcceptsCode
import com.quane.little.ide.presenter.command.{AddCodeCommand, IDECommandExecutor}
import com.quane.little.ide.view.html.CodeMenuLayout._
import com.quane.little.ide.view.{CodeMenuView, View, ViewPresenter}
import com.vaadin.ui.MenuBar
import com.vaadin.ui.MenuBar.Command

import scala.collection.mutable

object CodeMenuLayout {
  val Style = "l-code-menu"
}

/** An HTML layout view for the 'Add Code' dropdown menu.
  * This is one of the main tools the user uses to build their program.
  *
  * @author Sean Connolly
  */
class CodeMenuLayout[P <: ViewPresenter](view: View[P], val index: () => Int) extends MenuBar with CodeMenuView {

  def this(view: View[P]) = this(view, () => 0)

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
            case CodeType.Function =>
              val p = view.presenter.asInstanceOf[PresenterAcceptsCode]
              IDECommandExecutor.execute(new AddCodeCommand(p, id, index()))
            case _ => throw new IllegalArgumentException(
              "Unsupported menu item: " + codeType + " (" + name + ")"
            )
          }
        }
      }
    )

}
