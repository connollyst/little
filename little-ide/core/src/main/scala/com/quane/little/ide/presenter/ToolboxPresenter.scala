package com.quane.little.ide.presenter

import com.quane.little.ide.view.{ToolboxView, ToolboxViewPresenter}
import com.quane.little.data.model.{CodeCategory, FunctionCategory}
import com.quane.little.data.service.{PrimitiveService, FunctionService}

/** Presenter for the toolbox view, from which the user grab code components.
  *
  * @author Sean Connolly
  */
class ToolboxPresenter[V <: ToolboxView](val view: V)
  extends ToolboxViewPresenter
  with HasWorkspace {

  view.registerViewPresenter(this)

  // Initialize the tabs
  view.createToolboxTab(CodeCategory.Primitive)
  FunctionCategory.values foreach {
    category =>
      view.createToolboxTab(CodeCategory.Function, category)
  }

  // Load all primitives
  PrimitiveService().all foreach {
    primitive =>
      view.createToolboxItem(CodeCategory.Primitive, primitive.id.oid, primitive.id)
  }

  // Load this user's code
  FunctionService().findByUser("connollyst") foreach {
    function =>
      view.createToolboxItem(CodeCategory.Function, function.category, function.definition.name, function.id)
  }

}
