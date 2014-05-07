package com.quane.little.ide.presenter

import com.quane.little.ide.view.{ToolboxView, ToolboxViewPresenter}
import com.quane.little.data.model.CodeCategory
import com.quane.little.data.service.FunctionService

/** Presenter for the toolbox view, from which the user grab code components.
  *
  * @author Sean Connolly
  */
class ToolboxPresenter[V <: ToolboxView](val view: V)
  extends ToolboxViewPresenter {

  view.registerViewPresenter(this)

  CodeCategory.values foreach {
    category =>
      view.createToolboxTab(category)
  }

  FunctionService().findByUser("connollyst") foreach {
    function =>
      view.createToolboxItem(function.category, function.definition.name, function.id)
  }

}
