package com.quane.little.ide.presenter

import com.quane.little.ide.view.{CodeMenuView, CodeMenuViewPresenter}
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}
import com.quane.little.data.service.{FunctionService, StatementService, ExpressionService}
import com.quane.little.data.model.{CodeCategory, CodeType, PrimitiveRecord}
import com.quane.little.data.model.CodeType.CodeType

/** A presenter for views of the menu used to add code to a program.
  *
  * @author Sean Connolly
  */
class CodeMenuPresenter[C <: PresenterAccepts](val view: CodeMenuView, context: C)(implicit val bindingModule: BindingModule, m: Manifest[C])
  extends CodeMenuViewPresenter
  with Injectable {

  private val expressionService = inject[ExpressionService]
  private val statementService = inject[StatementService]
  private val functionService = inject[FunctionService]

  CodeCategory.values foreach {
    category => view.addCategory(category)
  }
  expressionService.all foreach {
    expression => addItem(CodeType.Expression, expression)
  }
  statementService.all foreach {
    statement => addItem(CodeType.Statement, statement)
  }

  if (m <:< manifest[PresenterAcceptsExpression]) {
    functionService.findByUser("connollyst") foreach {
      function => view.addMenuItem(CodeType.Function, function.category, function.id, function.definition.name)
    }
  } else {
    // disable functions category?
  }

  private def addItem(codeType: CodeType, primitive: PrimitiveRecord): Unit =
    if (accepts(primitive)) {
      view.addMenuItem(codeType, primitive.category, primitive.id, primitive.name)
    } else {
      view.addMenuItemDisabled(codeType, primitive.category, primitive.id, primitive.name)
    }

  private def accepts(primitive: PrimitiveRecord): Boolean =
  // TODO I think we mean the context view.. no?
    PresenterAccepts.accepts(view, primitive)

}
