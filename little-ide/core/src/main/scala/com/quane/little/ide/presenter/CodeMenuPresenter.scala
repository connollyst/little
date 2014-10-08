package com.quane.little.ide.presenter

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import com.quane.little.data.model.CodeType.CodeType
import com.quane.little.data.model.{CodeCategory, CodeType, PrimitiveRecord}
import com.quane.little.data.service.{ExpressionService, FunctionService, StatementService}
import com.quane.little.ide.view.{CodeMenuView, CodeMenuViewPresenter}
import com.quane.little.language.FunctionDefinition
import com.quane.little.language.data.ValueType

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

  view.registerViewPresenter(this)

  CodeCategory.values foreach {
    category => view.addCategory(category)
  }
  expressionService.all foreach {
    expression => addItem(CodeType.Expression, expression)
  }
  statementService.all foreach {
    statement => addItem(CodeType.Statement, statement)
  }

  if (m <:< manifest[PresenterAcceptsCode]) {
    functionService.findByUser("connollyst") foreach {
      function =>
        val accepts = contextAccepts(function.definition)
        println(".." + accepts)
        if (accepts) {
          view.addMenuItem(CodeType.Function, function.category, function.id, function.definition.name)
        } else {
          view.addMenuItemDisabled(CodeType.Function, function.category, function.id, function.definition.name)
        }
    }
  } else {
    // TODO disable functions category?
  }

  // TODO expand to more than FunctionDefinition?
  private def contextAccepts(function: FunctionDefinition): Boolean = {
    val returnType = function.returnType
    val acceptedType = context.asInstanceOf[PresenterAcceptsCode].acceptedValueType
    print("accepts " + returnType + " in " + acceptedType + "?..")
    acceptedType match {
      case ValueType.Anything => true
      case ValueType.Something => returnType != ValueType.Nothing
      case _ => acceptedType == returnType
    }
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
