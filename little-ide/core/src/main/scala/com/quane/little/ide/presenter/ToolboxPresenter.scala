package com.quane.little.ide.presenter

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import com.quane.little.data.model.CodeCategory._
import com.quane.little.data.model.{CodeCategory, FunctionId}
import com.quane.little.data.service.CodeService
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

  view.registerViewPresenter(this)

  CodeCategory.values foreach {
    category =>
      view.createToolboxTab(category)
  }
  codeService.allRecordsForUser("connollyst") foreach {
    code =>
      view.createToolboxItem(code.category, code.name, code.id)
  }

  override def requestNewFunctionDefinition(category: CodeCategory) =
    workspace.requestAddBlankFunctionDefinition(category)

  override def openFunctionDefinition(id: FunctionId) =
    workspace.requestAddFunctionDefinition(id)

}
