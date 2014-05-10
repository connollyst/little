package com.quane.little.ide.presenter

import com.quane.little.ide.view.{ToolboxView, ToolboxViewPresenter}
import com.quane.little.data.model.{CodeCategory, FunctionCategory}
import com.quane.little.data.service.{ListenerService, FunctionService}

/** Presenter for the toolbox view, from which the user grab code components.
  *
  * @author Sean Connolly
  */
class ToolboxPresenter[V <: ToolboxView](val view: V)
  extends ToolboxViewPresenter {

  view.registerViewPresenter(this)

  initFunctions()
  initEventListeners()

  private def initFunctions() = {
    FunctionCategory.values foreach {
      category =>
        view.createToolboxTab(CodeCategory.Function, category)
    }
    FunctionService().findByUser("connollyst") foreach {
      function =>
        view.createToolboxItem(CodeCategory.Function, function.category, function.definition.name, function.id)
    }
  }

  private def initEventListeners() = {
    view.createToolboxTab(CodeCategory.EventListener)
    view.setSelectedTab(CodeCategory.EventListener)
    ListenerService().init()
    ListenerService().findByUser("connollyst") foreach {
      listener =>
        view.createToolboxItem(CodeCategory.EventListener, listener.listener.event.toString, listener.id)
    }
  }

}
