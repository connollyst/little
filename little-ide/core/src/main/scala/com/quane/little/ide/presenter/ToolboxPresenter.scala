package com.quane.little.ide.presenter

import com.quane.little.ide.view.{ToolboxView, ToolboxViewPresenter}
import com.quane.little.data.model.{CodeCategory, FunctionCategory}
import com.quane.little.data.service.{ExpressionService, StatementService, FunctionService}

/** Presenter for the toolbox view, from which the user grab code components.
  *
  * @author Sean Connolly
  */
class ToolboxPresenter[V <: ToolboxView](val view: V)
  extends ToolboxViewPresenter
  with HasWorkspace {

  view.registerViewPresenter(this)

  // Initialize the tabs
  view.createToolboxTab(CodeCategory.Expression)
  view.createToolboxTab(CodeCategory.Statement)
  FunctionCategory.values foreach {
    category =>
      view.createToolboxTab(CodeCategory.Function, category)
  }

  // Load all primitives
  ExpressionService().all foreach {
    expression =>
      view.createToolboxItem(CodeCategory.Expression, expression.name, expression.id)
  }
  StatementService().all foreach {
    statement =>
      view.createToolboxItem(CodeCategory.Statement, statement.name, statement.id)
  }

  // Load this user's code
  FunctionService().findByUser("connollyst") foreach {
    function =>
      view.createToolboxItem(CodeCategory.Function, function.category, function.definition.name, function.id)
  }

}
