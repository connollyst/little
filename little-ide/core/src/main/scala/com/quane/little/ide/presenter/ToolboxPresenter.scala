package com.quane.little.ide.presenter

import com.quane.little.ide.view.{ToolboxView, ToolboxViewPresenter}
import com.quane.little.data.model.{CodeCategory, FunctionCategory}
import com.quane.little.data.service.{ExpressionService, StatementService, FunctionService}
import com.escalatesoft.subcut.inject.{BindingModule, Injectable}

/** Presenter for the toolbox view, from which the user grab code components.
  *
  * @author Sean Connolly
  */
class ToolboxPresenter[V <: ToolboxView](val view: V)(implicit val bindingModule: BindingModule)
  extends ToolboxViewPresenter
  with HasWorkspace
  with Injectable {

  private val expressionService = inject[ExpressionService]
  private val statementService = inject[StatementService]
  private val functionService = inject[FunctionService]

  view.registerViewPresenter(this)

  view.createToolboxTab(CodeCategory.Expression)
  view.createToolboxTab(CodeCategory.Statement)
  FunctionCategory.values foreach {
    category =>
      view.createToolboxTab(CodeCategory.Function, category)
  }
  expressionService.all foreach {
    expression =>
      view.createToolboxItem(CodeCategory.Expression, expression.name, expression.id)
  }
  statementService.all foreach {
    statement =>
      view.createToolboxItem(CodeCategory.Statement, statement.name, statement.id)
  }
  functionService.findByUser("connollyst") foreach {
    function =>
      view.createToolboxItem(CodeCategory.Function, function.category, function.definition.name, function.id)
  }

}
