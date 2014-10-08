package com.quane.little.ide.presenter

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import com.quane.little.data.model.{CodeCategory, CodeType, RecordId}
import com.quane.little.data.service.{CodeService, FunctionService}
import com.quane.little.ide.view.{ToolboxView, ToolboxViewPresenter}

/** Presenter for the toolbox view, from which the user grab code components.
  *
  * @author Sean Connolly
  */
class ToolboxPresenter[V <: ToolboxView](val view: V)(implicit val bindingModule: BindingModule)
  extends ToolboxViewPresenter
  with HasWorkspace
  with Injectable {

  private val codeService = inject[CodeService]
  private val functionService = inject[FunctionService]

  view.registerViewPresenter(this)

  CodeCategory.values foreach {
    category =>
      view.createToolboxTab(category)
  }
  codeService.allRecords foreach {
    expression =>
      view.createToolboxItem(expression.category, expression.name, CodeType.Expression, expression.id)
  }
  functionService.findByUser("connollyst") foreach {
    function =>
      view.createToolboxItem(function.category, function.definition.name, CodeType.Function, function.id)
  }

  override def requestNewFunctionDefinition() =
    workspace.requestAddBlankFunctionDefinition()

  override def openFunctionDefinition(functionId: RecordId) =
    workspace.requestAddFunctionDefinition(functionId)

}
