package com.quane.little.ide.presenter

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import com.quane.little.data.model.{FunctionRecord, CodeCategory, CodeType, PrimitiveRecord}
import com.quane.little.data.service.{CodeService, FunctionService}
import com.quane.little.ide.view.{CodeMenuView, CodeMenuViewPresenter}

/** A presenter for views of the menu used to add code to a program.
  *
  * @author Sean Connolly
  */
class CodeMenuPresenter[C <: PresenterAccepts](val view: CodeMenuView, context: C)(implicit val bindingModule: BindingModule, m: Manifest[C])
  extends CodeMenuViewPresenter
  with Injectable {

  private val codeService = inject[CodeService]
  private val functionService = inject[FunctionService]

  view.registerViewPresenter(this)

  CodeCategory.values foreach {
    category => view.addCategory(category)
  }
  codeService.allRecords foreach {
    code => addItem(code)
  }
  functionService.findByUser("connollyst") foreach {
    function => addItem(function)
  }

  private def addItem(primitive: PrimitiveRecord): Unit =
    if (accepts(primitive)) {
      view.addMenuItem(CodeType.Expression, primitive.category, primitive.id, primitive.name)
    } else {
      view.addMenuItemDisabled(CodeType.Expression, primitive.category, primitive.id, primitive.name)
    }

  private def addItem(function: FunctionRecord): Unit =
    if (accepts(function)) {
      view.addMenuItem(CodeType.Function, function.category, function.id, function.definition.name)
    } else {
      view.addMenuItemDisabled(CodeType.Function, function.category, function.id, function.definition.name)
    }

  private def accepts(primitive: PrimitiveRecord): Boolean =
    PresenterAccepts.acceptsPrimitive(context, primitive)

  private def accepts(function: FunctionRecord): Boolean =
    PresenterAccepts.acceptsFunction(context, function)

}
