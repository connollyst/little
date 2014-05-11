package com.quane.little.ide.view.html

import com.vaadin.ui.MenuBar
import com.quane.little.data.service.{FunctionService, PrimitiveService}
import com.vaadin.ui.MenuBar.Command
import com.quane.little.ide.presenter.command.{AddFunctionReferenceCommand, AddPrimitiveCommand, IDECommandExecutor}
import com.quane.little.ide.view.{ViewPresenter, View}
import com.quane.little.ide.presenter.{PresenterAcceptsPrimitive, PresenterAcceptsFunctionReference}

/** A menu for selecting an [[com.quane.little.language.Expression]] to be added
  * to a view. The options available are defined by the presenter's manifest.
  *
  * @see [[com.quane.little.ide.presenter.PresenterAccepts]]
  *
  * @author Sean Connolly
  */
private class ExpressionMenu[P <: ViewPresenter](view: View[P])(implicit m: Manifest[P])
  extends MenuBar {

  val item = addItem("∆", null, null)
  initPrimitiveMenuItems()
  initFunctionMenuItems()

  /** Initialize the menu items for primitive expressions.
    */
  private def initPrimitiveMenuItems(): Unit = {
    if (m <:< manifest[PresenterAcceptsPrimitive]) {
      PrimitiveService().all foreach {
        primitive =>
          item.addItem(primitive.name, null, new Command {
            override def menuSelected(selectedItem: MenuBar#MenuItem) = {
              val p = view.presenter.asInstanceOf[PresenterAcceptsPrimitive]
              IDECommandExecutor.execute(new AddPrimitiveCommand(p, primitive.id))
            }
          })
      }
    } else {
      // TODO we should be checking each type of primitive and disabling
      PrimitiveService().all foreach {
        primitive =>
          item.addItem(primitive.name, null, null).setEnabled(false)
      }
    }
  }

  /** Initialize the menu items for function expressions.
    */
  private def initFunctionMenuItems(): Unit = {
    val functions = item.addItem("functions", null, null)
    if (m <:< manifest[PresenterAcceptsFunctionReference]) {
      FunctionService().findByUser("connollyst") foreach {
        function =>
          functions.addItem(function.definition.name, null, new Command {
            def menuSelected(item: MenuBar#MenuItem) = {
              val p = view.presenter.asInstanceOf[PresenterAcceptsFunctionReference]
              IDECommandExecutor.execute(new AddFunctionReferenceCommand(p, function.id))
            }
          })
      }
    } else {
      functions.setEnabled(false)
    }
  }

}
