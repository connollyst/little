package com.quane.little.ide.view.html

import com.vaadin.ui.MenuBar
import com.quane.little.ide.presenter.command.{AddStatementCommand, AddExpressionCommand, AddFunctionReferenceCommand, IDECommandExecutor}
import com.quane.little.ide.view.{ViewPresenter, View}
import com.quane.little.ide.presenter.{PresenterAcceptsStatement, PresenterAcceptsExpression}
import com.quane.little.data.model.RecordId

/** A menu for selecting an [[com.quane.little.language.Expression]] to be added
  * to a view. The options available are defined by the presenter's manifest.
  *
  * @see [[com.quane.little.ide.presenter.PresenterAccepts]]
  *
  * @author Sean Connolly
  */
class ExpressionMenu[P <: ViewPresenter](view: View[P], index: => Int)(implicit m: Manifest[P])
  extends MenuBar {

  def addStatementClicked(id: RecordId) = {
    val p = view.presenter.asInstanceOf[PresenterAcceptsStatement]
    IDECommandExecutor.execute(new AddStatementCommand(p, id, index))
  }

  def addExpressionClicked(id: RecordId) = {
    val p = view.presenter.asInstanceOf[PresenterAcceptsExpression]
    IDECommandExecutor.execute(new AddExpressionCommand(p, id, index))
  }

  def addFunctionClicked(id: RecordId) = {
    val p = view.presenter.asInstanceOf[PresenterAcceptsExpression]
    IDECommandExecutor.execute(new AddFunctionReferenceCommand(p, id, index))
  }

}
