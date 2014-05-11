package com.quane.little.ide.view.html

import com.vaadin.ui.MenuBar
import com.quane.little.data.service.{FunctionService, PrimitiveService}
import com.vaadin.ui.MenuBar.Command
import com.quane.little.ide.presenter.command.{AddFunctionReferenceCommand, AddPrimitiveCommand, IDECommandExecutor}
import com.quane.little.ide.view.{ViewPresenter, View}
import com.quane.little.ide.presenter.{PresenterAccepts, PresenterAcceptsPrimitive, PresenterAcceptsFunctionReference}
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
  initPrimitiveMenuItems()
  initFunctionMenuItems()

  /** Initialize the menu items for primitive expressions.
    */
  private def initPrimitiveMenuItems(): Unit = {
    PrimitiveService().all foreach {
      primitive =>
        if (accepts(primitive)) {
          item.addItem(primitive.name, new Command {
            def menuSelected(selectedItem: MenuBar#MenuItem) = addPrimitiveClicked(primitive.id)
          })
        } else {
          item.addItem(primitive.name, null).setEnabled(false)
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

  private def accepts(primitive: PrimitiveRecord): Boolean =
    PresenterAccepts.acceptsPrimitive(view, primitive)

  def addPrimitiveClicked(id: RecordId) = {
    val p = view.presenter.asInstanceOf[PresenterAcceptsPrimitive]
    IDECommandExecutor.execute(new AddPrimitiveCommand(p, id, index))
  }

  def addFunctionClicked(id: RecordId) = {
    val p = view.presenter.asInstanceOf[PresenterAcceptsFunctionReference]
    IDECommandExecutor.execute(new AddFunctionReferenceCommand(p, id, index))
  }

}
